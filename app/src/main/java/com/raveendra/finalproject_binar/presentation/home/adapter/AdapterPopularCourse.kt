package com.raveendra.finalproject_binar.presentation.home.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ItemPopularCourseBinding
import com.raveendra.finalproject_binar.model.PopularCourse
import com.c8.core.utils.toIdrCurrency


class AdapterPopularCourse(): RecyclerView.Adapter<PopularCourseViewHolder>(){

    private val differ = AsyncListDiffer(this, object : DiffUtil. ItemCallback<PopularCourse>(){
        override fun areItemsTheSame(oldItem: PopularCourse, newItem: PopularCourse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PopularCourse, newItem: PopularCourse): Boolean {
            return oldItem.id == newItem.id
        }})

    fun setData(data: List<PopularCourse>) {
        differ.submitList(data)
        notifyItemChanged(0, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularCourseViewHolder {
        return PopularCourseViewHolder(
            binding = ItemPopularCourseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: PopularCourseViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

}

class PopularCourseViewHolder(
    private val binding: ItemPopularCourseBinding,
) : RecyclerView.ViewHolder(binding.root){
    fun bind(item: PopularCourse) {
        binding.ivPopularCourse.load(item.imgPopularCourse)
        binding.tvNamePopularCourse.text = item.namePopularCourse
        binding.tvCourseRate.text = item.ratingCourse
        binding.tvTitleCourse.text = item.titleCourse
        binding.tvAuthorCourse.text = item.authorCourse
        binding.tvLevelCourse.text = item.levelCourse
        binding.tvDurationCourse.text = item.durationCourse
        binding.tvModuleCourse.text = item.moduleCourse
        binding.btnAddToCart.text =  binding.root.context.getString(R.string.label_buy, item.priceCourse.toIdrCurrency().toString())
    }
}