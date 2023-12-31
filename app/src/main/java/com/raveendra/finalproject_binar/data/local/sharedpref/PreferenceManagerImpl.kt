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

    override var isPassOnboarding: Boolean
        get() = preferences.getBoolean(IS_PASS_ONBOARDING, false)
        set(value) {
            preferences.edit().putBoolean(IS_PASS_ONBOARDING, value).apply()
        }

    override fun isLoggedIn(): Boolean = appToken.isNotEmpty()

    override fun clear() {
        appToken = ""
    }

    companion object {
        const val PREFS_APP_TOKEN = "PREFS_APP_TOKEN"
        const val IS_PASS_ONBOARDING = "IS_PASS_ONBOARDING"
    }
}