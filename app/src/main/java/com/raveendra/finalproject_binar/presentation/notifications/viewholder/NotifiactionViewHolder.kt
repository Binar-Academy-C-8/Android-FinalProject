package com.raveendra.finalproject_binar.presentation.notifications.viewholder

import android.icu.text.SimpleDateFormat
import androidx.recyclerview.widget.RecyclerView
import com.raveendra.finalproject_binar.databinding.ItemNotificationBinding
import com.raveendra.finalproject_binar.utils.ViewHolderBinder
import com.raveendrag.finalproject_binar.domain.DataDomain
import java.util.Locale

class NotificationViewHolder(
    private val binding: ItemNotificationBinding,
    private val onItemClick: (DataDomain) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<DataDomain> {
    override fun bind(item: DataDomain) {
        binding.tvPromo.text = item.titleNotification
        binding.tvNotifLine2.text = item.description
        binding.tvDateAndTime.text = formatVerboseDate(item.createdAt)

        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
    }
    private fun formatVerboseDate(createdAt: String?): String {
        if (createdAt.isNullOrEmpty()) {
            return ""
        }

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())

        try {
            val date = inputFormat.parse(createdAt)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }
}