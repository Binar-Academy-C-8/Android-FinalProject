package com.raveendra.finalproject_binar.presentation.`class`.class_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.databinding.ItemListClassBinding
import com.raveendra.finalproject_binar.domain.ClassDomain
import com.raveendra.finalproject_binar.utils.ViewHolderBinder

class ClassAdapter(
    private val onItemClick: (ClassDomain) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ClassDomain>() {
        override fun areItemsTheSame(oldItem: ClassDomain, newItem: ClassDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ClassDomain, newItem: ClassDomain): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemListClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClassViewHolder(binding, onItemClick)
    }

    fun setData(data: List<ClassDomain>) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<ClassDomain>).bind(differ.currentList[position])
    }
}
class ClassViewHolder(
    private val binding: ItemListClassBinding,
    private val onClickListener: (ClassDomain) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<ClassDomain> {
    override fun bind(item: ClassDomain) {
        binding.ivImg.load(item.image) {
            crossfade(true)
        }
        binding.tvCategory.text = item.category
        binding.tvTitle.text = item.courseName
        binding.tvAuthor.text = item.courseBy
        binding.progressBar.progress = item.courseProgressInPercentage

        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}

