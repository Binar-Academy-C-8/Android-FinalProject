package com.raveendra.finalproject_binar.presentation.`class`.class_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.raveendra.finalproject_binar.utils.ViewHolderBinder
import com.raveendra.finalproject_binar.databinding.ItemCategoryCourseBinding
import com.raveendra.finalproject_binar.model.CourseCategory
import com.raveendra.finalproject_binar.presentation.`class`.classviewholder.DashboardCategoryViewHolder

class DashboardCategoryAdapter(
    private val onItemClick: (CourseCategory) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<CourseCategory>() {
        override fun areItemsTheSame(oldItem: CourseCategory, newItem: CourseCategory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CourseCategory, newItem: CourseCategory): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemCategoryCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DashboardCategoryViewHolder(binding, onItemClick)
    }

    fun setData(data: List<CourseCategory>) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<CourseCategory>).bind(differ.currentList[position])
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }


}