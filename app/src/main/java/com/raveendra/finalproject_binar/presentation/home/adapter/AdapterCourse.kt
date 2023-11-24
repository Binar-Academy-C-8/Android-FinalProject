package com.raveendra.finalproject_binar.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.databinding.ItemCategoryCourseBinding
import com.raveendra.finalproject_binar.model.Course

class AdapterCourse(): RecyclerView.Adapter<CourseViewHolder>(){
    private val differ = AsyncListDiffer(this, object : DiffUtil. ItemCallback<Course>(){
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }})

    fun setData(data: List<Course>) {
        differ.submitList(data)
        notifyItemChanged(0, data.size)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder(
            binding = ItemCategoryCourseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

}

class CourseViewHolder(
    private val binding: ItemCategoryCourseBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(item: Course) {
        binding.ivCategoryCourse.load(item.imgCategoryCourse)
        binding.tvNameCategoryCourse.text = item.nameCourse
    }
}