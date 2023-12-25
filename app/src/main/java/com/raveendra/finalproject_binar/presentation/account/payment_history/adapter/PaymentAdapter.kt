package com.raveendra.finalproject_binar.presentation.account.payment_history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.raveendra.finalproject_binar.utils.ViewHolderBinder
import com.raveendra.finalproject_binar.databinding.ItemListCoursePaidBinding
import com.raveendra.finalproject_binar.databinding.ItemListCoursePremiumBinding
import com.raveendra.finalproject_binar.domain.HistoryPaymentDomain
import com.raveendra.finalproject_binar.domain.UserTransactionDomain
import com.raveendra.finalproject_binar.presentation.account.payment_history.viewholder.PaymentHistoryViewHolder
import com.raveendra.finalproject_binar.presentation.course.viewholder.CourseViewHolder
import com.raveendra.finalproject_binar.presentation.home.adapter.PopularCourseViewHolder

class PaymentAdapter(
    private val onItemClick: (UserTransactionDomain) -> Unit
) : RecyclerView.Adapter<PaymentHistoryViewHolder>() {
    private val differ =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<UserTransactionDomain>() {
            override fun areItemsTheSame(
                oldItem: UserTransactionDomain,
                newItem: UserTransactionDomain
            ): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(
                oldItem: UserTransactionDomain,
                newItem: UserTransactionDomain
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentHistoryViewHolder {
        val binding = ItemListCoursePaidBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PaymentHistoryViewHolder(binding, onItemClick)
    }

    fun setData(data: List<UserTransactionDomain>) {
        differ.submitList(data)
    }


    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: PaymentHistoryViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
    }


}