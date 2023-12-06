package com.raveendra.finalproject_binar.presentation.notifications.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.c8.core.utils.ViewHolderBinder
import com.raveendra.finalproject_binar.databinding.ItemNotificationBinding
import com.raveendra.finalproject_binar.model.Notification

class NotificationAdapter(
    private val onItemClick: (Notification) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding, onItemClick)
    }

    fun setData(data: List<Notification>) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Notification>).bind(differ.currentList[position])
    }

    class NotificationViewHolder(
        private val binding: ItemNotificationBinding,
        private val onItemClick: (Notification) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Notification> {
        override fun bind(item: Notification) {
            binding.tvPromo.text = item.label
            binding.tvDateAndTime.text = item.date
            binding.tvNotifLine2.text = item.text
            binding.tvNotifLine3.text = item.description
            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }
}
