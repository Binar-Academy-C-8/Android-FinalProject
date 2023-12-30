package com.raveendra.finalproject_binar.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ItemSectionHeaderBinding
import com.raveendra.finalproject_binar.databinding.ItemSectionVideosBinding
import com.raveendra.finalproject_binar.domain.ChapterDomain
import com.raveendra.finalproject_binar.domain.ContentDomain
import com.xwray.groupie.viewbinding.BindableItem

/**
 *hrahm,25/11/2023, 23:22
 **/
class HeaderItem(
    private val data: ChapterDomain,
    private val context: Context,
    private val headerPosition: Int
) :
    BindableItem<ItemSectionHeaderBinding>() {
    override fun bind(viewBinding: ItemSectionHeaderBinding, position: Int) {
        val title = "$headerPosition ${data.chapterTitle}"
        viewBinding.tvSectionHeaderName.text = context.getString(R.string.label_var_chapter, title)
        viewBinding.tvDuration.text = context.getString(
            R.string.label_var_minute,
            data.durationPerChapterInMinutes.toString()
        )

    }

    override fun getLayout(): Int = R.layout.item_section_header
    override fun initializeViewBinding(view: View): ItemSectionHeaderBinding =
        ItemSectionHeaderBinding.bind(view)
}

class DataItem(
    private val context: Context,
    private val data: ContentDomain,
    private val isFromClass: Boolean,
    private val onItemClick: (ContentDomain) -> Unit
) :
    BindableItem<ItemSectionVideosBinding>() {
    override fun bind(viewBinding: ItemSectionVideosBinding, position: Int) {
        viewBinding.tvSectionData.text = data.contentTitle

        if (data.isLocked) {
            viewBinding.icPlay.setBackgroundResource(R.drawable.rounded_background_grey)
            viewBinding.icPlay.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_play_rv
                )
            )
            viewBinding.icPlay.setOnClickListener {
                if (isFromClass) {
                    Toast.makeText(
                        context,
                        "Silahkan tonton video sebelumnya, untuk mengakses video selanjutnya",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else Toast.makeText(
                    context,
                    "Silahkan tambahkan ke kelas berjalan untuk bisa mengakses semua video",
                    Toast.LENGTH_SHORT
                ).show()

            }
        } else {
            viewBinding.icPlay.setBackgroundResource(R.drawable.rounded_background_green)
            viewBinding.icPlay.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_play_rv
                )
            )
            viewBinding.icPlay.setOnClickListener {
                onItemClick.invoke(data)
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_section_videos
    override fun initializeViewBinding(view: View): ItemSectionVideosBinding =
        ItemSectionVideosBinding.bind(view)
}