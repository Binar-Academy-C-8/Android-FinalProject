package com.raveendra.finalproject_binar.utils

import android.view.View
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ItemSectionHeaderBinding
import com.raveendra.finalproject_binar.databinding.ItemSectionVideosBinding
import com.raveendra.finalproject_binar.domain.ChapterDomain
import com.raveendra.finalproject_binar.domain.ContentDomain
import com.raveendra.finalproject_binar.domain.DetailCourseDomain
import com.xwray.groupie.viewbinding.BindableItem

/**
 *hrahm,25/11/2023, 23:22
 **/
class HeaderItem(private val title: String) :
    BindableItem<ItemSectionHeaderBinding>() {
    override fun bind(viewBinding: ItemSectionHeaderBinding, position: Int) {
        viewBinding.tvSectionHeaderName.text = title

    }
    override fun getLayout(): Int = R.layout.item_section_header
    override fun initializeViewBinding(view: View): ItemSectionHeaderBinding =
        ItemSectionHeaderBinding.bind(view)
}

class DataItem(private val data: ContentDomain, private val onItemClick: (ContentDomain) -> Unit) :
    BindableItem<ItemSectionVideosBinding>() {
    override fun bind(viewBinding: ItemSectionVideosBinding, position: Int) {
        viewBinding.tvSectionData.text = data.contentTitle
        viewBinding.icPlay.setOnClickListener { onItemClick.invoke(data) }
    }
    override fun getLayout(): Int = R.layout.item_section_videos
    override fun initializeViewBinding(view: View): ItemSectionVideosBinding =
        ItemSectionVideosBinding.bind(view)
}