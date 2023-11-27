package com.raveendra.finalproject_binar.presentation.classdadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.raveendra.finalproject_binar.core.ViewHolderBinder
import com.raveendra.finalproject_binar.databinding.ItemListClassBinding
import com.raveendra.finalproject_binar.model.Course
import com.raveendra.finalproject_binar.presentation.classviewholder.DashboardViewHolder

class DashboardAdapter(
    private val onItemClick: (Course) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemListClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DashboardViewHolder(binding, onItemClick)
    }

    fun setData(data: List<Course>) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Course>).bind(differ.currentList[position])
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }


}