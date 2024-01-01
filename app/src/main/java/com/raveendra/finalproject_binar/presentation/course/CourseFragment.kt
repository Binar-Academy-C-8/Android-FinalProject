package com.raveendra.finalproject_binar.presentation.course

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.data.local.sharedpref.PreferenceManager
import com.raveendra.finalproject_binar.databinding.FragmentCourseBinding
import com.raveendra.finalproject_binar.domain.CategoryDomain
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.presentation.course.adapter.CourseAdapter
import com.raveendra.finalproject_binar.presentation.detailcourse.DetailCourseActivity
import com.raveendra.finalproject_binar.presentation.popup.NonLoginDialogFragment
import com.raveendra.finalproject_binar.presentation.search.SearchActivity
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseFragment : BaseFragment<FragmentCourseBinding>() {

    private val viewModel: CourseViewModel by viewModel()

    private val preferences: PreferenceManager by inject()

    private var filterCourseBottomSheet: FilterCourseBottomSheet? = null

    var courseType: String? = null
    private var selectedCategory: List<Int?>? = listOf()
    var level: String? = ""

    private val adapterCourse: CourseAdapter by lazy {
        CourseAdapter { course: CourseDomain ->
            if (preferences.isLoggedIn()) course.id?.let { id ->
                DetailCourseActivity.navigate(
                    requireContext(),
                    id,
                    false
                )
            }
            else showNonLoginDialog()
        }
    }


    private val swipeRefreshListener = SwipeRefreshList {
        viewModel.getCourse(
            courseType = courseType,
            category = selectedCategory,
            difficulty = level ?: ""
        )
    }

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCourseBinding
        get() = FragmentCourseBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViews()
        observeData()
        setClickChips()
        setupSwipeRefreshLayout()
    }

    private fun observeData() {
        viewModel.course.observe(viewLifecycleOwner) {
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
                        requireContext(),
                        it.exception?.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            )
        }
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = { result ->
                result.payload?.let { data -> setupFilter(data) }
            })
        }
    }

    private fun setupRecyclerView() {
        binding.rvList.adapter = adapterCourse
        viewModel.getCourse()
        viewModel.getCategories()
    }

    private fun setClickChips() {
        binding.chip1.setOnClickListener {
            courseType = null
            viewModel.getCourse(
                courseType = courseType,
                category = selectedCategory,
                difficulty = level ?: ""
            )

        }
        binding.chip2.setOnClickListener {
            courseType = "Premium"
            viewModel.getCourse(
                courseType = courseType,
                category = selectedCategory,
                difficulty = level ?: ""
            )

        }
        binding.chip3.setOnClickListener {
            courseType = "Free"
            viewModel.getCourse(
                courseType = courseType,
                category = selectedCategory,
                difficulty = level ?: ""
            )

        }
    }

    private fun setupViews() = with(binding) {
        viewModel.getCourse()
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

    private fun setupSwipeRefreshLayout() {
        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener)
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.primary_dark_blue_06)
        )
    }

    private fun setupFilter(data: List<CategoryDomain>) = with(binding) {
        filterCourseBottomSheet = FilterCourseBottomSheet(
            context = requireContext(),
            data,
        )
        filterCourseBottomSheet?.filterListenerClicked =
            { category: List<Int?>?, difficulty: String ->
                selectedCategory = category
                level = if (difficulty == getString(R.string.label_all_level)) "" else difficulty
                viewModel.getCourse(
                    courseType = courseType,
                    category = selectedCategory,
                    difficulty = level ?: ""
                )
            }
        tvFilter.setOnClickListener {
            filterCourseBottomSheet?.show(
                requireActivity().supportFragmentManager,
                CourseFragment::class.java.simpleName
            )
        }
    }

    private fun showNonLoginDialog() {
        NonLoginDialogFragment().show(childFragmentManager, null)
    }
}