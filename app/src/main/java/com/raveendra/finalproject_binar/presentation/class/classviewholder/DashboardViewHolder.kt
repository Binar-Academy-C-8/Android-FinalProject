package com.raveendra.finalproject_binar.presentation.`class`.classviewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.utils.ViewHolderBinder
import com.raveendra.finalproject_binar.databinding.ItemListClassBinding
import com.raveendra.finalproject_binar.model.Course

class DashboardViewHolder(
    private val binding: ItemListClassBinding,
    private val onClickListener: (Course) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {
    override fun bind(item: Course) {
        binding.ivImg.load(item.imgUrl) {
            crossfade(true)
        }
        binding.tvCategory.text = item.modul
        binding.tvTitle.text = item.name
        binding.tvAuthor.text = item.author

        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}

