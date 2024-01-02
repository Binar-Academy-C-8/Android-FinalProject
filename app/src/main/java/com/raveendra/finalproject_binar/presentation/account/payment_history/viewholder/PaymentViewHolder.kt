package com.raveendra.finalproject_binar.presentation.account.payment_history.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ItemListCoursePaidBinding
import com.raveendra.finalproject_binar.domain.UserTransactionDomain
import com.raveendra.finalproject_binar.utils.toIdrCurrency

class PaymentHistoryViewHolder(
    private val binding: ItemListCoursePaidBinding,
    private val onClickListener: (UserTransactionDomain) -> Unit
) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: UserTransactionDomain) {
        binding.ivImg.load(item.course?.image)
        binding.tvCategory.text = item.course?.courseName.toString()
        binding.tvTitle.text = item.course?.intendedFor
        binding.tvRating.text = item.course?.rating.toString()
        binding.tvLevel.text = item.course?.courseLevel
        if (item.totalPrice != 0 ) binding.tvPrice.text = item.totalPrice?.toIdrCurrency()
        if (item.paymentStatus != "paid") {
            binding.llType.setBackgroundResource(R.drawable.bg_type_not_paid)
            binding.tvType.text = binding.root.context.getString(R.string.label_unpaid)
        }else{
            binding.llType.setBackgroundResource(R.drawable.bg_type_paid)
            binding.tvType.text = binding.root.context.getString(R.string.label_paid)
        }
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
    }
}