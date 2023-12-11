package com.raveendra.finalproject_binar.data.local.sharedpref

interface PreferenceManager {
    var appToken: String

    fun isLoggedIn(): Boolean
    fun clear()
}