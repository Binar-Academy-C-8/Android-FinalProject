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
import com.raveendra.finalproject_binar.domain.CategoryDomain
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.utils.toIdrCurrency


class AdapterPopularCourse(private val itemClick: (CourseDomain) -> Unit): RecyclerView.Adapter<PopularCourseViewHolder>(){

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularCourseViewHolder {
        return PopularCourseViewHolder(
            binding = ItemPopularCourseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            itemClick
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: PopularCourseViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

}

class PopularCourseViewHolder(
    private val binding: ItemPopularCourseBinding,
    private val itemClick: (CourseDomain) -> Unit
) : RecyclerView.ViewHolder(binding.root){
    fun bind(item: CourseDomain) {
        binding.root.setOnClickListener {
            itemClick.invoke(item)
        }
        binding.ivPopularCourse.load(item.image){
            placeholder(R.color.primary_dark_blue_06)
            error(R.color.primary_dark_blue_06)
            crossfade(true)
        }
        binding.tvNamePopularCourse.text = item.courseName
        binding.tvTitleCourse.text = item.aboutCourse
        binding.tvAuthorCourse.text = item.courseBy
        binding.tvLevelCourse.text = item.courseLevel
        binding.tvDurationCourse.text = "${item.durationPerCourseInMinutes} ${binding.root.context.getString(R.string.duration_course)}"
        binding.tvModuleCourse.text = "${item.modulePerCourse} ${binding.root.context.getString(R.string.module_course)}"
        binding.btnAddToCart.text =  binding.root.context.getString(R.string.label_buy, item.coursePrice.toString())
    }
}