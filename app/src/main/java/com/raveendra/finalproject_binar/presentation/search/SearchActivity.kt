package com.raveendra.finalproject_binar.presentation.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import coil.load
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.data.local.sharedpref.PreferenceManager
import com.raveendra.finalproject_binar.databinding.ActivitySearchBinding
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.presentation.detailcourse.DetailCourseActivity
import com.raveendra.finalproject_binar.presentation.popup.NonLoginDialogFragment
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity  : BaseViewModelActivity<SearchViewModel, ActivitySearchBinding>() {

    companion object {
        fun navigate(context: Context) = with(context) {
            startActivity(
                Intent(
                    this,
                    SearchActivity::class.java
                )
            )
        }
    }

    override val viewModel: SearchViewModel by viewModel()

    private val preferences: PreferenceManager by inject()

    override val bindingInflater: (LayoutInflater) -> ActivitySearchBinding
        get() = ActivitySearchBinding::inflate

    var search: String? = ""

    private val adapterCourse: SearchCourseAdapter by lazy {
        SearchCourseAdapter { course: CourseDomain ->
            if (preferences.isLoggedIn())  course.id?.let { id -> DetailCourseActivity.navigate(this, id,false) }
            else showNonLoginDialog()
        }
    }

    override fun setupViews(): Unit = with(binding) {

        layoutSearchBar.etSearch.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(layoutSearchBar.etSearch, InputMethodManager.SHOW_IMPLICIT)

        layoutSearchBar.ibSearch.setOnClickListener {
            search = layoutSearchBar.etSearch.text.toString()
            viewModel.getCourse(layoutSearchBar.etSearch.text.toString())
        }
        binding.layoutStateCourse.tvError.isVisible = true
        binding.layoutStateCourse.tvError.text = getString(R.string.label_search_class)
        binding.layoutStateCourse.ivNotFound.load(
            R.drawable.img_onboarding_popup
        )
        binding.rvList.adapter = adapterCourse

    }

    override fun setupObservers() {
        viewModel.course.observe(this) {
            it.proceedWhen(
                doOnSuccess = {

                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.rvList.isVisible = true
                    binding.layoutStateCourse.root.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.ivNotFound.isVisible = false
                    it.payload?.let {
                        adapterCourse.setData(it)
                    }
                    binding.swipeRefreshLayout.isRefreshing = false
                },
                doOnLoading = {
                    binding.shimmerView.startShimmer()
                    binding.shimmerView.isVisible = true
                    binding.rvList.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.root.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.ivNotFound.isVisible = false
                },
                doOnEmpty = {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.rvList.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.tvError.isVisible = true
                    binding.layoutStateCourse.tvError.text = getString(R.string.label_empty_search_class)
                    binding.layoutStateCourse.ivNotFound.load(
                        R.drawable.img_empty_class
                    )
                    binding.layoutStateCourse.ivNotFound.isVisible = true
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.swipeRefreshLayout.isRefreshing = false
                },
                doOnError = {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.rvList.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.ivNotFound.isVisible = true
                    it.exception?.message.toString()
                    Toast.makeText(
                        this,
                        it.exception?.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            )
        }
    }
    private fun showNonLoginDialog(){
        NonLoginDialogFragment().show(supportFragmentManager,null)
    }
}