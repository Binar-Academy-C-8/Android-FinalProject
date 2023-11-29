package com.raveendra.finalproject_binar.presentation.detailcourse

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.raveendra.finalproject_binar.data.network.api.service.dummydatavideos.ItemVideos
import com.raveendra.finalproject_binar.databinding.FragmentListClassBinding
import com.raveendra.finalproject_binar.utils.DataItem
import com.raveendra.finalproject_binar.utils.HeaderItem
import com.raveendra.finalproject_binar.utils.ResultWrapper
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section

class ListClassFragment : BaseFragment<FragmentListClassBinding>() {
//    private val viewModel: DetailViewModel by viewModels {
//        val localDataSource: LocalDataSource = LocalDataSourceimpl()
//        val repository: RepositoryVideos = RepositoryVideosImpl(localDataSource)
//        GenericViewModelFactory.create(DetailViewModel(repository))
//    }
    private val viewModel:DetailViewModel by activityViewModel()
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
        viewModel.listVideos.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { succes ->
                    val sectionDataList = succes.payload
                    val sectionList: List<Section> = sectionDataList?.map { sectionedData ->
                        val sectionName = sectionedData.name
                        val itemVideoList = sectionedData.data

                        val section = Section()
                        section.setHeader(HeaderItem(sectionName) { data ->
                            Toast.makeText(requireContext(), "${data}", Toast.LENGTH_SHORT).show()
                        })

                        val listVideos = itemVideoList.map { itemVideo ->
                            DataItem(itemVideo) { data ->
                                Toast.makeText(requireContext(), "${data}", Toast.LENGTH_SHORT).show()
                                onItemClick(sectionName, itemVideo.titleVideos)
                            }
                        }


                        section.addAll(listVideos)
                        section
                    } ?: emptyList()
                    adapterGropie.addAll(sectionList)
                }
            )
        }
        viewModel.getvideos()

    }

    private fun onItemClick(sectionName: String, videoTitle: String) {
        val sectionDataList = viewModel.listVideos.value?.payload
        val matchingSection = sectionDataList?.find { it.name == sectionName }

        // Jika section ditemukan, cari video dengan judul yang sesuai
        matchingSection?.let { section ->
            val matchingVideo = section.data.find { it.titleVideos == videoTitle }

            // Jika video ditemukan, ambil URL video dan atur ke ViewModel
            matchingVideo?.let { video ->
                val videoUrl = video.urlVideos
                val itemVideos = ItemVideos("", videoUrl)
                val result = ResultWrapper.Success(itemVideos)
                viewModel.setUrlVideos(result)
            }
        }


        // Tidak ada perpindahan halaman di sini
    }

}