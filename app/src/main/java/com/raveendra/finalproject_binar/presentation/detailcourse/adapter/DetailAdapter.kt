package com.raveendra.finalproject_binar.presentation.detailcourse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ActivityDetailCourseBinding
import com.raveendra.finalproject_binar.domain.CourseDomain

class DetailAdapter(private val buttonClick: (CourseDomain) -> Unit) :
    RecyclerView.Adapter<DetailCourseViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<CourseDomain>() {
        override fun areItemsTheSame(oldItem: CourseDomain, newItem: CourseDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CourseDomain, newItem: CourseDomain): Boolean {
            return oldItem.id == newItem.id
        }
    })

    fun setData(data: List<CourseDomain>) {
        differ.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCourseViewHolder {
        return DetailCourseViewHolder(
            binding = ActivityDetailCourseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: DetailCourseViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}

class DetailCourseViewHolder(
    private val binding: ActivityDetailCourseBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CourseDomain) {
        binding.tvCategory.text = item.category
        binding.tvCourseName.text = item.courseName
        binding.tvDurationCourse.text =
            "${item.durationPerCourseInMinutes} ${binding.root.context.getString(R.string.duration_course)}"
        binding.tvModuleCourse.text =
            "${item.modulePerCourse} ${binding.root.context.getString(R.string.module_course)}"

    }
}