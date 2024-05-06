package com.theduc.educationapplication.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.theduc.educationapplication.MainActivity
import com.theduc.educationapplication.R
import com.theduc.educationapplication.ui.login.LoginActivity


class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed (Runnable {
            val currentUser: FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser()

            if (currentUser == null) {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
            finish()
        }, 1000)
    }
}