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
                    val section = success.payload?.data?.chapters?.map {chapters ->
                        val section = Section()
                        section.setHeader(HeaderItem(chapters?.chapterTitle.orEmpty()))

                        val dataSection = chapters?.contents?.map { data ->
                            data?.let { contents ->
                                DataItem(contents) { data ->
                                    viewModel.getContentUrl(data.contentUrl)
                                }
                            }
                        }
                        dataSection?.let { it1 -> section.addAll(it1) }
                        section
                    }
                    section?.let { data -> adapterGropie.addAll(data) }

//                    val sectionName = success.payload?.data?.chapters
//                    val section = Section()
//                    sectionName?.forEach { chapter ->
//                        section.setHeader(HeaderItem(chapter?.chapterTitle.toString()))
//                        val contentList = chapter?.contents?.map { contentDomain ->
//                            contentDomain?.let { content ->
//                                DataItem(content) { contentUrl ->
//                                    viewModel.getContentUrl(contentUrl.contentUrl)
//                                }
//                            }
//                        }
//                        contentList?.let { content -> section.addAll(content) }
//                    }
//                    adapterGropie.add(section)
                },
                doOnError = { error ->
                    error.message.toString()
                }
            )
        }
    }
}

