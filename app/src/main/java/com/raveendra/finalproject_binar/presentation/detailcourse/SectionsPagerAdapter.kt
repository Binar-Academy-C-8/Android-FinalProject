package com.raveendra.finalproject_binar.presentation.detailcourse

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 *hrahm,24/11/2023, 23:48
 **/
class SectionsPagerAdapter(
    activity: AppCompatActivity
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = AboutClassFragment()
            1 -> fragment = ListClassFragment()
        }
        return fragment as Fragment
    }
}