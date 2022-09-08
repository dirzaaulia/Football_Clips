package com.dirzaaulia.footballclips.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupBottomNavigation()
  }

  private fun setupBottomNavigation() {
    binding.apply{
      val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
      val navController = navHostFragment.navController
      bottomNav.setupWithNavController(navController)

      // Hide bottom nav on screens which don't require it
      lifecycleScope.launchWhenResumed {
        navController.addOnDestinationChangedListener { _, destination, _ ->
          when (destination.id) {
            R.id.homeFragment, R.id.liveScoreFragment -> bottomNav.isVisible = true
            else -> bottomNav.isVisible = false
          }
        }
      }
    }
  }
}