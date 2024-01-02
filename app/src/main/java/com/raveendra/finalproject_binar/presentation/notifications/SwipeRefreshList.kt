package com.raveendra.finalproject_binar.presentation.notifications

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class SwipeRefreshList (private val onRefreshCallback: () -> Unit) : SwipeRefreshLayout.OnRefreshListener  {
    override fun onRefresh() {
        onRefreshCallback.invoke()
    }
}