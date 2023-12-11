package com.raveendra.finalproject_binar.presentation.`class`.classviewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.utils.ViewHolderBinder
import com.raveendra.finalproject_binar.databinding.ItemCategoryCourseBinding
import com.raveendra.finalproject_binar.model.CourseCategory

class DashboardCategoryViewHolder(
    private val binding: ItemCategoryCourseBinding,
    private val onClickListener: (CourseCategory) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CourseCategory> {
    override fun bind(item: CourseCategory) {
        binding.ivCategoryCourse.load(item.imgCategoryCourse) {
            crossfade(true)
        }
        binding.tvNameCategoryCourse.text = item.nameCourse
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}