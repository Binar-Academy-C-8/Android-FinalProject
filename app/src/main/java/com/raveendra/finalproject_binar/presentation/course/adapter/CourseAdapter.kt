package com.raveendra.finalproject_binar.presentation.course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ItemListCoursePremiumBinding
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.utils.ViewHolderBinder

class CourseAdapter(
    private val onItemClick: (CourseDomain) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<CourseDomain>() {
        override fun areItemsTheSame(oldItem: CourseDomain, newItem: CourseDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CourseDomain, newItem: CourseDomain): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CourseViewHolder(
            binding = ItemListCoursePremiumBinding.inflate(
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<CourseDomain>).bind(differ.currentList[position])
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }


}
class CourseViewHolder(
    private val binding: ItemListCoursePremiumBinding,
    private val onClickListener: (CourseDomain) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CourseDomain> {
    override fun bind(item: CourseDomain) {
        binding.ivImg.load(item.image) {
            crossfade(true)
        }
        binding.tvTitle.text = item.courseName
        binding.tvType.text = item.courseType
        binding.tvCategory.text = item.courseName
        binding.tvAuthor.text = item.courseBy
        binding.tvRating.text = "4.8"
        binding.tvLevel.text = item.courseLevel
        binding.tvModule.text = "${item.durationPerCourseInMinutes} ${binding.root.context.getString(
            R.string.duration_course)}"
        binding.tvDuration.text = "${item.modulePerCourse} ${binding.root.context.getString(R.string.module_course)}"
        binding.tvPrice.text = "${binding.root.context.getString(R.string.text_rupiah)} ${item.coursePrice}"
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}
