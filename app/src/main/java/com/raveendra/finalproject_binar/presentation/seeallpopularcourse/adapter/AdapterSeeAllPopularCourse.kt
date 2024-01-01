package com.raveendra.finalproject_binar.presentation.seeallpopularcourse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ItemSeeAllPopulerCourseBinding
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.utils.toIdrCurrency

class AdapterSeeAllPopularCourse(private val itemClick: (CourseDomain) -> Unit,private val buttonClick: (CourseDomain) -> Unit): RecyclerView.Adapter<SeeAllPopularCourseViewHolder>(){

    private val differ = AsyncListDiffer(this, object : DiffUtil. ItemCallback<CourseDomain>(){
        override fun areItemsTheSame(oldItem: CourseDomain, newItem: CourseDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CourseDomain, newItem: CourseDomain): Boolean {
            return oldItem.id == newItem.id
        }})

    fun setData(data: List<CourseDomain>) {
        differ.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeAllPopularCourseViewHolder {
        return SeeAllPopularCourseViewHolder(
            binding = ItemSeeAllPopulerCourseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            itemClick,
            buttonClick
        )
    }


    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: SeeAllPopularCourseViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

}

class SeeAllPopularCourseViewHolder(
    private val binding: ItemSeeAllPopulerCourseBinding,
    private val itemClick: (CourseDomain) -> Unit,
    private val buttonClick: (CourseDomain) -> Unit
) : RecyclerView.ViewHolder(binding.root){
    fun bind(item: CourseDomain) {
        binding.root.setOnClickListener {
            itemClick.invoke(item)
        }
        binding.clAddToCart.setOnClickListener {
            if (item.coursePrice == 0) return@setOnClickListener
            buttonClick.invoke(item)
        }
        binding.ivPopularCourse.load(item.image){
            error(R.color.primary_dark_blue_06)
            crossfade(true)
        }
        binding.tvNamePopularCourse.text = item.category
        binding.tvRating.text = item.ratingCourse.toString()
        binding.tvTitle.text = item.courseName
        binding.tvAuthor.text = item.courseBy
        binding.tvLevel.text = item.courseLevel
        binding.tvDuration.text = "${item.durationPerCourseInMinutes} ${binding.root.context.getString(R.string.duration_course)}"
        binding.tvModule.text = "${item.modulePerCourse} ${binding.root.context.getString(R.string.module_course)}"
        binding.tvBuy.text =  if (item.coursePrice == 0) binding.root.context.getString(R.string.label_free) else binding.root.context.getString(R.string.label_buy, item.coursePrice?.toIdrCurrency())
    }
}