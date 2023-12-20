package com.raveendra.finalproject_binar.presentation.detailcourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.raveendra.finalproject_binar.databinding.FragmentListClassBinding
import com.raveendra.finalproject_binar.utils.DataItem
import com.raveendra.finalproject_binar.utils.HeaderItem
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ListClassFragment : BaseFragment<FragmentListClassBinding>() {

    private val viewModel: DetailViewModel by activityViewModel()
    private val adapterGropie: GroupieAdapter by lazy {
        GroupieAdapter()
    }
    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListClassBinding
        get() = FragmentListClassBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {
        binding.rvPage.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterGropie
        }

        viewModel.detailData.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { success ->
                    val sectionName = success.payload?.data?.chapters
                    val section = Section()
                    sectionName?.forEach { chapter ->
                        section.setHeader(HeaderItem(chapter.chapterTitle))
                        val contentList = chapter.contents.map { contentDomain ->
                            DataItem(contentDomain) { contentUrl ->
                                viewModel.getContentUrl(contentUrl.contentUrl)
                            }
                        }
                        section.addAll(contentList)
                    }
                    adapterGropie.add(section)
                },
                doOnError = { error ->
                    error.message.toString()
                }
            )
        }
        viewModel.getVideos(1)
    }
}

