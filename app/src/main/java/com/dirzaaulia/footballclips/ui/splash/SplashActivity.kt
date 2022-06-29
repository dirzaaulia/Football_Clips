package com.dirzaaulia.footballclips.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dirzaaulia.footballclips.databinding.ActivitySplashBinding
import com.dirzaaulia.footballclips.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySplashBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySplashBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupSplashScreen()
  }

  private fun setupSplashScreen() {
    val splashScreenTimeout = 3000L
    val intent = Intent(this, MainActivity::class.java)
    Handler(Looper.getMainLooper()).postDelayed({
      startActivity(intent)
      finish()
    }, splashScreenTimeout)
  }
}