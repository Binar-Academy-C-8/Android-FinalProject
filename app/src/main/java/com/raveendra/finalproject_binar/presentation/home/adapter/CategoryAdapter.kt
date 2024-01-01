package com.raveendra.finalproject_binar.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ItemCategoryCourseBinding
import com.raveendra.finalproject_binar.domain.CategoryDomain

class CategoryAdapter(private val itemClick: (CategoryDomain) -> Unit): RecyclerView.Adapter<CourseViewHolder>(){
    private val differ = AsyncListDiffer(this, object : DiffUtil. ItemCallback<CategoryDomain>(){
        override fun areItemsTheSame(oldItem: CategoryDomain, newItem: CategoryDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryDomain, newItem: CategoryDomain): Boolean {
            return oldItem.id == newItem.id
        }})

    fun setData(data: List<CategoryDomain>) {
        differ.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder(
            binding =  ItemCategoryCourseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            itemClick
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

}

class CourseViewHolder(
    private val binding: ItemCategoryCourseBinding,
    private val itemClick: (CategoryDomain) -> Unit
): RecyclerView.ViewHolder(binding.root){
    fun bind(item: CategoryDomain) {
        with(item) {
            binding.ivCategoryCourse.load(item.image) {
                error(R.color.primary_dark_blue_06)
                crossfade(true)
                transformations(
                    CircleCropTransformation()
                )
            }
            binding.tvNameCategoryCourse.text = item.categoryName
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}