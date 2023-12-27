package com.raveendra.finalproject_binar.data.local.sharedpref

interface PreferenceManager {
    var appToken: String
    var isPassOnboarding: Boolean

    fun isLoggedIn(): Boolean
    fun clear()
}