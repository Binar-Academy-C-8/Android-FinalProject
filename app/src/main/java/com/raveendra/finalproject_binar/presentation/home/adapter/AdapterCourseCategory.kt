package com.raveendra.finalproject_binar.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.databinding.ItemCategoryCourseBinding
import com.raveendra.finalproject_binar.model.CourseCategory

class AdapterCourseCategory(): RecyclerView.Adapter<CourseViewHolder>(){
    private val differ = AsyncListDiffer(this, object : DiffUtil. ItemCallback<CourseCategory>(){
        override fun areItemsTheSame(oldItem: CourseCategory, newItem: CourseCategory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CourseCategory, newItem: CourseCategory): Boolean {
            return oldItem.id == newItem.id
        }})

    fun setData(data: List<CourseCategory>) {
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
    fun bind(item: CourseCategory) {
        binding.ivCategoryCourse.load(item.imgCategoryCourse) {
            crossfade(true)
        }
        binding.tvNameCategoryCourse.text = item.nameCourse
    }
}