package com.raveendra.finalproject_binar.presentation.splash_screen

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.data.local.sharedpref.PreferenceManager
import com.raveendra.finalproject_binar.presentation.MainActivity
import com.raveendra.finalproject_binar.presentation.onboarding.OnBoardingActivity
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val preferences: PreferenceManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler(Looper.getMainLooper()).postDelayed({
            checkIfUserLogin()

        }, 2000)
    }

    private fun checkIfUserLogin() {
        if (preferences.isPassOnboarding) {
            MainActivity.navigateWithFlag(this)
        } else {
            OnBoardingActivity.navigate(this)
        }
    }

}