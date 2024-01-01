package com.raveendra.finalproject_binar.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ItemListCourseBinding
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.utils.ViewHolderBinder
import com.raveendra.finalproject_binar.utils.toIdrCurrency

class SearchCourseAdapter(
    private val onItemClick: (CourseDomain) -> Unit
) : RecyclerView.Adapter<SearchCourseViewHolder>() {
    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<CourseDomain>() {
        override fun areItemsTheSame(oldItem: CourseDomain, newItem: CourseDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CourseDomain, newItem: CourseDomain): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCourseViewHolder {
        return SearchCourseViewHolder(
            binding = ItemListCourseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )
    }

    fun setData(data: List<CourseDomain>) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: SearchCourseViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }


}
class SearchCourseViewHolder(
    private val binding: ItemListCourseBinding,
    private val onClickListener: (CourseDomain) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CourseDomain> {
    override fun bind(item: CourseDomain) {
        binding.ivImg.load(item.image) {
            crossfade(true)
        }
        binding.tvTitle.text = item.courseName
        binding.tvType.text = item.courseType
        binding.tvCategory.text = item.category
        binding.tvAuthor.text = binding.root.context.getString(R.string.label_var_author, item.courseBy)
        binding.tvRating.text = item.ratingCourse.toString()
        binding.tvLevel.text = item.courseLevel
        binding.tvModule.text = binding.root.context.getString(R.string.label_var_minute, item.durationPerCourseInMinutes.toString())
        binding.tvDuration.text = binding.root.context.getString(R.string.label_var_module, item.modulePerCourse.toString())
        if (item.coursePrice != 0) {
            binding.ivPremium.isVisible = true
            binding.tvPrice.text = item.coursePrice?.toIdrCurrency()
        }
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}
