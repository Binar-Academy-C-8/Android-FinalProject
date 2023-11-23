package com.raveendra.finalproject_binar.presentation.course.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.core.ViewHolderBinder
import com.raveendra.finalproject_binar.databinding.ItemListCourseFreeBinding
import com.raveendra.finalproject_binar.databinding.ItemListCoursePremiumBinding
import com.raveendra.finalproject_binar.model.Course

class CoursePremiumViewHolder(
    private val binding: ItemListCoursePremiumBinding,
    private val onClickListener: (Course) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {
    override fun bind(item: Course) {
        binding.ivImg.load(item.imgUrl) {
            crossfade(true)
        }
        binding.tvTitle.text = item.name
        binding.tvAuthor.text = item.author
        binding.tvRating.text = item.rating.toString()
        binding.tvTitle.text = item.name
        binding.tvLevel.text = item.level
        binding.tvModule.text = item.modul
        binding.tvDuration.text = item.duration
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}

class CourseFreeViewHolder(
    private val binding: ItemListCourseFreeBinding,
    private val onClickListener: (Course) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {
    override fun bind(item: Course) {
        binding.ivImg.load(item.imgUrl) {
            crossfade(true)
        }
        binding.tvTitle.text = item.name
        binding.tvAuthor.text = item.author
        binding.tvRating.text = item.rating.toString()
        binding.tvTitle.text = item.name
        binding.tvLevel.text = item.level
        binding.tvModule.text = item.modul
        binding.tvDuration.text = item.duration
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}