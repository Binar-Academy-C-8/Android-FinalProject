package com.raveendra.finalproject_binar.presentation.course

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class SwipeRefreshList (private val onRefreshCallback: () -> Unit) : SwipeRefreshLayout.OnRefreshListener  {
    override fun onRefresh() {
        onRefreshCallback.invoke()
    }


}