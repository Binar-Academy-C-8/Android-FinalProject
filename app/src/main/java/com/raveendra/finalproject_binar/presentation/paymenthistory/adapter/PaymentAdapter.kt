package com.raveendra.finalproject_binar.presentation.paymenthistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.c8.core.utils.ViewHolderBinder
import com.raveendra.finalproject_binar.databinding.ItemListCourseNotPaidBinding
import com.raveendra.finalproject_binar.databinding.ItemListCoursePaidBinding
import com.raveendra.finalproject_binar.model.Payment
import com.raveendra.finalproject_binar.presentation.paymenthistory.viewholder.PaymentNotPaidViewHolder
import com.raveendra.finalproject_binar.presentation.paymenthistory.viewholder.PaymentPaidViewHolder

class PaymentAdapter(
    var courseTypeAdapter: PaymentTypeAdadpter,
    private val onItemClick: (Payment) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Payment>() {
        override fun areItemsTheSame(oldItem: Payment, newItem: Payment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Payment, newItem: Payment): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PaymentTypeAdadpter.PAID.ordinal -> (
                    PaymentPaidViewHolder(
                        binding = ItemListCoursePaidBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        ),
                        onItemClick
                    )
                    )

            else -> {
                PaymentNotPaidViewHolder(
                    binding = ItemListCourseNotPaidBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClick
                )
            }
        }
    }

    fun setData(data: List<Payment>) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Payment>).bind(differ.currentList[position])
    }

    fun refreshList() {
        notifyItemRangeChanged(0, differ.currentList.size)
        }


}