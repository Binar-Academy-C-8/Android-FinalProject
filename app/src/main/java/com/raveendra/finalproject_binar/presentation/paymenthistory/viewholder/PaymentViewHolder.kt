package com.raveendra.finalproject_binar.presentation.paymenthistory.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.utils.ViewHolderBinder
import com.raveendra.finalproject_binar.databinding.ItemListCourseNotPaidBinding
import com.raveendra.finalproject_binar.databinding.ItemListCoursePaidBinding
import com.raveendra.finalproject_binar.model.Payment

class PaymentPaidViewHolder(
    private val binding: ItemListCoursePaidBinding,
    private val onClickListener: (Payment) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Payment> {
    override fun bind(item: Payment) {
        binding.ivImg.load(item.imgUrl) {
            crossfade(true)
        }
        binding.tvTitle.text = item.name
        binding.tvAuthor.text = item.author
        binding.tvRating.text = item.rating.toString()
        binding.tvLevel.text = item.level
        binding.tvModule.text = item.modul
        binding.tvDuration.text = item.duration
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}

class PaymentNotPaidViewHolder(
    private val binding: ItemListCourseNotPaidBinding,
    private val onClickListener: (Payment) -> Unit
): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Payment> {
    override fun bind(item: Payment) {
        binding.ivImg.load(item.imgUrl) {
            crossfade(true)
        }
        binding.tvTitle.text = item.name
        binding.tvAuthor.text = item.author
        binding.tvRating.text = item.rating.toString()
        binding.tvTitle.text = item.name
        binding.tvLevel.text = item.level
        binding.tvModule.text = item.modul
        binding.tvDuration.text = item.duration
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }

}