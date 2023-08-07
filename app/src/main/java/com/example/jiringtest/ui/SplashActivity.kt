package com.example.jiringtest.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jiringtest.databinding.ActivitySplashBinding
import com.example.jiringtest.ui.loginScreen.LoginActivity
import java.util.Timer
import java.util.TimerTask

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val viewBinding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        Timer().schedule(object : TimerTask() { override fun run() {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        } }, 5000)
    }

    override fun onStop() {
        super.onStop()
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }
}