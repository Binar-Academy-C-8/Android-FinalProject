package com.raveendra.finalproject_binar.presentation.course.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ItemListCoursePremiumBinding
import com.raveendra.finalproject_binar.utils.ViewHolderBinder
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.domain.CoursePaymentDomain

class CourseViewHolder(
    private val binding: ItemListCoursePremiumBinding,
    private val onClickListener: (CourseDomain) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CourseDomain> {
    override fun bind(item: CourseDomain) {
        binding.ivImg.load(item.image) {
            crossfade(true)
        }
        binding.tvTitle.text = item.courseName
        binding.tvType.text = item.courseType
        binding.tvCategory.text = item.courseName
        binding.tvAuthor.text = item.courseBy
        binding.tvRating.text = "4.8"
        binding.tvLevel.text = item.courseLevel
        binding.tvModule.text = "${item.durationPerCourseInMinutes} ${binding.root.context.getString(R.string.duration_course)}"
        binding.tvDuration.text = "${item.modulePerCourse} ${binding.root.context.getString(R.string.module_course)}"
        binding.tvPrice.text = "${binding.root.context.getString(R.string.text_rupiah)} ${item.coursePrice}"
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }

}
