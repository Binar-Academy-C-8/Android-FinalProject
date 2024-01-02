package com.raveendra.finalproject_binar.presentation.home

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.data.local.sharedpref.PreferenceManager
import com.raveendra.finalproject_binar.databinding.FragmentHomeBinding
import com.raveendra.finalproject_binar.domain.CategoryDomain
import com.raveendra.finalproject_binar.presentation.`class`.class_adapter.ClassAdapter
import com.raveendra.finalproject_binar.presentation.detailcourse.DetailCourseActivity
import com.raveendra.finalproject_binar.presentation.home.adapter.AdapterPopularCourse
import com.raveendra.finalproject_binar.presentation.home.adapter.CategoryAdapter
import com.raveendra.finalproject_binar.presentation.popup.NonLoginDialogFragment
import com.raveendra.finalproject_binar.presentation.search.SearchActivity
import com.raveendra.finalproject_binar.presentation.seeallpopularcourse.SeeAllPopularCourseActivity
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModel()

    private val preferences: PreferenceManager by inject()

    var category: Int? = null

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            category = it.id
            viewModel.getCourses(listOf(category))
        }
    }

    private val adapterClass: ClassAdapter by lazy {
        ClassAdapter {
            DetailCourseActivity.navigate(requireContext(),it.courseUserId, true)
        }
    }



    private val popularCourseAdapter: AdapterPopularCourse by lazy {
        AdapterPopularCourse(
            itemClick = {
                if (preferences.isLoggedIn())  it.id?.let { id -> DetailCourseActivity.navigate(requireContext(), id,false) }
                else showNonLoginDialog()

            },
            buttonClick = {

            })
    }

    private val swipeRefreshListener = SwipeRefreshList {
        viewModel.getCategories()
        viewModel.getCourses(listOf(category))
        viewModel.getClass("inProgress")
    }


    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
        setOnClickListener()
        setupSwipeRefreshLayout()
    }


    private fun setupObservers() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                binding.shimmerView.stopShimmer()
                binding.shimmerView.isVisible = false
                binding.layoutStateCategory.root.isVisible = false
                binding.layoutStatePopularCourse.pbLoading.isVisible = false
                binding.layoutStateCategory.tvError.isVisible = false
                binding.layoutStateCategory.ivNotFound.isVisible = false
                binding.rvCategoryCourse.apply {
                    isVisible = true
                    adapter = categoryAdapter
                }
                it.payload?.let { data ->
                    val allData = data.toMutableList()
                    allData.add(
                        0, CategoryDomain(
                            categoryName = "Show All",
                            createdAt = "",
                            id = 0,
                            image = "https://raw.githubusercontent.com/Bahrulilmi30/rullfood/master/app/src/main/res/drawable/frame_2400__1_.png",
                            updatedAt = ""
                        )
                    )
                    categoryAdapter.setData(allData)
                }
                categoryAdapter.refreshList()
                binding.swipeRefreshLayout.isRefreshing = false
            }, doOnLoading = {
                binding.shimmerView.startShimmer()
                binding.shimmerView.isVisible = true
                binding.layoutStatePopularCourse.root.isVisible = false
                binding.layoutStatePopularCourse.pbLoading.isVisible = false
                binding.layoutStateCategory.tvError.isVisible = false
                binding.layoutStateCategory.ivNotFound.isVisible = false
                binding.rvCategoryCourse.isVisible = false
            }, doOnError = {
                binding.shimmerView.stopShimmer()
                binding.shimmerView.isVisible = false
                binding.layoutStateCategory.root.isVisible = true
                binding.layoutStatePopularCourse.pbLoading.isVisible = false
                binding.layoutStateCategory.tvError.isVisible = true
                binding.layoutStateCategory.ivNotFound.isVisible = false
                binding.layoutStateCategory.tvError.text = "Category Tidak Tersedia"
                binding.rvCategoryCourse.isVisible = false
                binding.swipeRefreshLayout.isRefreshing = false
            })
        }
        viewModel.course.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                binding.shimmerViewCourse.stopShimmer()
                binding.shimmerViewCourse.isVisible = false
                binding.layoutStatePopularCourse.root.isVisible = true
                binding.layoutStatePopularCourse.tvError.isVisible = false
                binding.layoutStatePopularCourse.pbLoading.isVisible = false
                binding.layoutStatePopularCourse.ivNotFound.isVisible = false
                binding.rvPopularCourse.apply {
                    isVisible = true
                    adapter = popularCourseAdapter
                    itemAnimator = null
                }
                binding.rvPopularCourse.smoothScrollToPosition(0)
                it.payload?.let { data -> popularCourseAdapter.setData(data) }
                popularCourseAdapter.refreshList()
                binding.swipeRefreshLayout.isRefreshing = false
            }, doOnLoading = {
                binding.shimmerViewCourse.startShimmer()
                binding.shimmerViewCourse.isVisible = true
                binding.layoutStatePopularCourse.root.isVisible = false
                binding.layoutStatePopularCourse.pbLoading.isVisible = false
                binding.layoutStatePopularCourse.ivNotFound.isVisible = false
                binding.rvPopularCourse.isVisible = false
            }, doOnError = {
                binding.shimmerViewCourse.stopShimmer()
                binding.shimmerViewCourse.isVisible = false
                binding.layoutStatePopularCourse.root.isVisible = true
                binding.layoutStatePopularCourse.pbLoading.isVisible = false
                binding.layoutStatePopularCourse.ivNotFound.isVisible = true
                /*binding.layoutStatePopularCourse.tvError.text = it.exception?.message.orEmpty()*/
                binding.rvPopularCourse.isVisible = false
                binding.swipeRefreshLayout.isRefreshing = false
            }, doOnEmpty = {
                binding.shimmerViewCourse.stopShimmer()
                binding.shimmerViewCourse.isVisible = false
                binding.layoutStatePopularCourse.root.isVisible = true
                binding.layoutStatePopularCourse.pbLoading.isVisible = false
                binding.layoutStatePopularCourse.ivNotFound.isVisible = true
                /*binding.layoutStatePopularCourse.tvError.text = "Course not found"*/
                binding.rvPopularCourse.isVisible = false
                binding.swipeRefreshLayout.isRefreshing = false
            })
        }
        viewModel.classResult.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.clClass.isVisible = true
                    binding.rvClass.apply {
                        adapter = adapterClass
                    }
                    it.payload?.let {
                        adapterClass.setData(it)
                    }
                    binding.swipeRefreshLayout.isRefreshing = false
                },
                doOnEmpty = {
                    binding.clClass.isVisible = false
                    binding.swipeRefreshLayout.isRefreshing = false
                },
                doOnError = {error ->
                    binding.clClass.isVisible = false
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            )
        }
    }

    private fun setupViews() = with(binding) {
        viewModel.getCategories()
        viewModel.getCourses()
        viewModel.getClass("inProgress")
        layoutSearchBar.etSearch.inputType = InputType.TYPE_NULL
        layoutSearchBar.etSearch.isFocusable = false
        layoutSearchBar.etSearch.isCursorVisible = false
        layoutSearchBar.etSearch.setOnClickListener {
            SearchActivity.navigate(requireContext())
        }
        layoutSearchBar.ibSearch.setOnClickListener {
            SearchActivity.navigate(requireContext())
        }
    }

    private fun setOnClickListener() {
        binding.tvViewAll.setOnClickListener {
            SeeAllPopularCourseActivity.navigate(requireContext())
        }
    }

    private fun setupSwipeRefreshLayout() {
        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener)
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.primary_dark_blue_06)
        )
    }

    private fun showNonLoginDialog(){
        NonLoginDialogFragment().show(childFragmentManager,null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getClass("inProgress")
    }
}