package com.raveendra.finalproject_binar.presentation.account.payment_history.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raveendra.finalproject_binar.databinding.ItemListCoursePaidBinding
import com.raveendra.finalproject_binar.domain.UserTransactionDomain

class PaymentHistoryViewHolder(
    private val binding: ItemListCoursePaidBinding,
    private val onClickListener: (UserTransactionDomain) -> Unit
) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: UserTransactionDomain) {
        with(item){
            binding.ivImg.load(item.course?.image)
            binding.tvCategory.text = item.course?.courseName.toString()
            binding.tvTitle.text = item.course?.intendedFor
            binding.tvRating.text = item.course?.rating.toString()
            binding.tvAuthor.text = item.course?.adminId.toString()
            binding.tvLevel.text = item.course?.courseLevel
            binding.tvType.text = item.paymentStatus

        }
    }
}