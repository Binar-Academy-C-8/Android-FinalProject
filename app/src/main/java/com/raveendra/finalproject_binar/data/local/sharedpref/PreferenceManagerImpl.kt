package com.raveendra.finalproject_binar.data.local.sharedpref

import android.content.SharedPreferences

class PreferenceManagerImpl(
    private val preferences: SharedPreferences
): PreferenceManager {

    override var appToken: String
        get() = preferences.getString(PREFS_APP_TOKEN, "") ?: ""
        set(value) {
            preferences.edit().putString(PREFS_APP_TOKEN, value).apply()
        }

    override fun isLoggedIn(): Boolean = appToken.isNotEmpty()

    override fun clear() {
        appToken = ""
    }

    companion object {
        const val PREFS_APP_TOKEN = "PREFS_APP_TOKEN"
    }
}