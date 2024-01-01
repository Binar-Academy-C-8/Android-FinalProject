package com.raveendra.finalproject_binar.presentation.course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.raveendra.finalproject_binar.databinding.ItemCategoryFilterBinding
import com.raveendra.finalproject_binar.domain.CategoryDomain

class CategoryFilterAdapter(private val itemClick: (category : CategoryDomain) -> Unit): RecyclerView.Adapter<CategoryFilterViewHolder>(){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryFilterViewHolder {
        return CategoryFilterViewHolder(
            binding =  ItemCategoryFilterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            itemClick
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: CategoryFilterViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

}

class CategoryFilterViewHolder(
    private val binding: ItemCategoryFilterBinding,
    private val itemClick: (CategoryDomain) -> Unit
): RecyclerView.ViewHolder(binding.root){
    fun bind(item: CategoryDomain) {
        binding.cbFilter.isChecked = item.isSelected == true
        binding.tvCategoryFilter.text = item.categoryName
        binding.cbFilter.setOnCheckedChangeListener { _, isChecked ->
            val category = item
            category.isSelected = isChecked
            itemClick.invoke(category)
        }

    }
}