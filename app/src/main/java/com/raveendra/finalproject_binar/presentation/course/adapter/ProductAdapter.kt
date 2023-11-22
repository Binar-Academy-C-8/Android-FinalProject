package com.raveendra.finalproject_binar.presentation.course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.core.ViewHolderBinder
import com.raveendra.finalproject_binar.databinding.ItemListCourseBinding
import com.raveendra.finalproject_binar.model.Product

class ProductAdapter (
    private val onItemClick: (Product) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemListCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onItemClick)
    }

    fun setData(data: List<Product>) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Product>).bind(differ.currentList[position])
    }

    class ProductViewHolder(
        private val binding: ItemListCourseBinding,
        private val onClickListener: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Product> {
        override fun bind(item: Product) {
            binding.ivImg.load(item.imgUrl) {
                crossfade(true)
            }
            binding.tvTitle.text = item.name
            binding.tvAuthor.text = item.author
            binding.tvRating.text = item.rating.toString()
            binding.tvTitle.text = item.name
            binding.tvLevel.text = item.level
            binding.tvModul.text = item.modul
            binding.tvDuration.text = item.duration
            binding.root.setOnClickListener {
                onClickListener.invoke(item)
            }
        }
    }


}