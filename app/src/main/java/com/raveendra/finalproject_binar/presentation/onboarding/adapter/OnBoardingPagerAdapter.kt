package com.raveendra.finalproject_binar.presentation.onboarding.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raveendra.finalproject_binar.R

class OnBoardingPagerAdapter(
    private val context: Context
) : RecyclerView.Adapter<OnBoardingPagerAdapter.ViewHolder>() {

    private val onBoardingItems = listOf(
        Pair("Menggali Ilmu dari \n Pengalaman Paling Baik!", R.drawable.ic_onboarding_1),
        Pair("Belajar dari ahli \n yang berpengalaman!" +
                "", R.drawable.ic_onboarding_2),
        Pair("Pelajari lebih,\n Belajar lebih cepat!", R.drawable.ic_onboarding_3)
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_tagline_onboarding)
        val imageView: ImageView = itemView.findViewById(R.id.iv_on_boarding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_on_boarding, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (text, imageResId) = onBoardingItems[position]
        holder.textView.text = text
        holder.imageView.setImageResource(imageResId)
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }
}