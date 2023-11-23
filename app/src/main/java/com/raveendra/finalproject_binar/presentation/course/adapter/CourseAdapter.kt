package com.raveendra.finalproject_binar.presentation.course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.core.ViewHolderBinder
import com.raveendra.finalproject_binar.databinding.ItemListCourseFreeBinding
import com.raveendra.finalproject_binar.databinding.ItemListCoursePremiumBinding
import com.raveendra.finalproject_binar.model.Course

class CourseAdapter(
    var courseTypeAdapter: CourseTypeAdapter,
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
        return when (viewType) {
            CourseTypeAdapter.PREMIUM.ordinal -> (
                    CoursePremiumViewHolder(
                        binding = ItemListCoursePremiumBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        ),
                        onItemClick
                    )
                    )

            else -> {
                CourseFreeViewHolder(
                    binding = ItemListCourseFreeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClick
                )
            }
        }
    }

    fun setData(data: List<Course>) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Course>).bind(differ.currentList[position])
    }

    class CoursePremiumViewHolder(
        private val binding: ItemListCoursePremiumBinding,
        private val onClickListener: (Course) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {
        override fun bind(item: Course) {
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

    class CourseFreeViewHolder(
        private val binding: ItemListCourseFreeBinding,
        private val onClickListener: (Course) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {
        override fun bind(item: Course) {
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