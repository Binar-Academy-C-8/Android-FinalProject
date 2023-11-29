package com.raveendra.finalproject_binar.utils

import android.view.View
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.data.network.api.service.dummydatavideos.ItemVideos
import com.raveendra.finalproject_binar.databinding.ItemSectionHeaderBinding
import com.raveendra.finalproject_binar.databinding.ItemSectionVideosBinding
import com.xwray.groupie.viewbinding.BindableItem

/**
 *hrahm,25/11/2023, 23:22
 **/
class HeaderItem(private val title: String?, private val onHeaderClick: (item: String) -> Unit) :
    BindableItem<ItemSectionHeaderBinding>() {
    override fun bind(viewBinding: ItemSectionHeaderBinding, position: Int) {
        viewBinding.tvSectionHeaderName.text = title
        viewBinding.root.setOnClickListener {
            if (title != null) {
                onHeaderClick.invoke(title)
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_section_header

    override fun initializeViewBinding(view: View): ItemSectionHeaderBinding =
        ItemSectionHeaderBinding.bind(view)
}

class DataItem(private val data: ItemVideos, private val onItemClick: (item: String) -> Unit) :
    BindableItem<ItemSectionVideosBinding>() {
    override fun bind(viewBinding: ItemSectionVideosBinding, position: Int) {
        viewBinding.tvSectionData.text = data.titleVideos
        viewBinding.icPlay.setOnClickListener { onItemClick.invoke(data.urlVideos) }
    }

    override fun getLayout(): Int = R.layout.item_section_videos

    override fun initializeViewBinding(view: View): ItemSectionVideosBinding =
        ItemSectionVideosBinding.bind(view)
}