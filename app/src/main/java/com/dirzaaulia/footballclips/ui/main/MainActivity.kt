package com.dirzaaulia.footballclips.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.databinding.ActivityMainBinding
import com.dirzaaulia.footballclips.util.openLink
import com.google.android.material.color.DynamicColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

//    private var adView: AdView? = null

    // Get the ad size with screen width.
//    private val adSize: AdSize
//        get() {
//            val displayMetrics = resources.displayMetrics
//            val adWidthPixels =
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    val windowMetrics: WindowMetrics = this.windowManager.currentWindowMetrics
//                    windowMetrics.bounds.width()
//                } else {
//                    displayMetrics.widthPixels
//                }
//            val density = displayMetrics.density
//            val adWidth = (adWidthPixels / density).toInt()
//            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        preOnCreateSetup()
        super.onCreate(savedInstanceState)
        postOnCreateSetup()
    }

//    /** Called when leaving the activity. */
//    public override fun onPause() {
//        adView?.pause()
//        super.onPause()
//    }
//
//    /** Called when returning to the activity. */
//    public override fun onResume() {
//        super.onResume()
//        adView?.resume()
//    }
//
//    /** Called before the activity is destroyed. */
//    public override fun onDestroy() {
//        adView?.destroy()
//        super.onDestroy()
//    }

    private fun preOnCreateSetup() {
        installSplashScreen()
        DynamicColors.applyToActivityIfAvailable(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun postOnCreateSetup() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupInsetter()
        setupAppBar()
        setupBottomNavigation()
        // Log the Mobile Ads SDK version.
//        Log.d("MainActivity", "Google Mobile Ads SDK Version: " + MobileAds.getVersion())

//        initializeMobileAdsSdk()
    }

    private fun setupInsetter() {
        binding.root.applyInsetter {
            type(statusBars = true) {
                padding(animated = true)
            }
        }
    }

    private fun setupAppBar() {
        binding.appBar.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_info -> {
                    showInformationDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupBottomNavigation() {
        binding.apply {
            this@MainActivity.navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = this@MainActivity.navHostFragment.navController
            bottomNav.setupWithNavController(navController)

            // Hide bottom nav on screens which don't require it
            lifecycleScope.launchWhenResumed {
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    appBar.root.isVisible = destination.id != R.id.viewerFragment
                    bottomNav.isVisible = destination.id != R.id.viewerFragment

                    val title = when (destination.id) {
                        R.id.homeFragment -> getString(R.string.videos)
                        R.id.liveScoreFragment -> getString(R.string.livescore)
                        R.id.eventFragment -> getString(R.string.euro2024)
                        else -> ""
                    }

                    binding.appBar.toolbar.title = title
                }
            }
        }
    }

    private fun showInformationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.information))
            .setMessage(getText(R.string.data_provider))
            .setPositiveButton(getString(R.string.go_to_scorebat)) { dialog, _ ->
                dialog.dismiss()
                openLink(this, getString(R.string.scorebat_link))
            }
            .show()
    }

//    private fun loadBanner() {
//        // [START create_ad_view]
//        // Create a new ad view.
//        val adView = AdView(this)
//        adView.adUnitId = AD_UNIT_ID
//        adView.setAdSize(adSize)
//        this.adView = adView
//
//        // Replace ad container with new ad view.
//        binding.adViewContainer.removeAllViews()
//        binding.adViewContainer.addView(adView)
//        // [END create_ad_view]
//
//        // [START load_ad]
//        // Start loading the ad in the background.
//        val adRequest = AdRequest.Builder().build()
//        adView.loadAd(adRequest)
//        // [END load_ad]
//    }
//
//    // [START request_ads]
//    private fun initializeMobileAdsSdk() {
////        if (isMobileAdsInitializeCalled.getAndSet(true)) {
////            return
////        }
//        // [START_EXCLUDE silent] // [START_EXCLUDE silent] Hide from developer docs code snippet
//        // Set your test devices.
////        MobileAds.setRequestConfiguration(
////            RequestConfiguration.Builder().setTestDeviceIds(listOf(TEST_DEVICE_HASHED_ID)).build()
////        )
//        // [END_EXCLUDE]
//        CoroutineScope(Dispatchers.IO).launch {
//            // Initialize the Google Mobile Ads SDK on a background thread.
//            MobileAds.initialize(this@MainActivity) {}
//
//            runOnUiThread {
//                // Load an ad on the main thread.
//                loadBanner()
//            }
//        }
//    }
//
//    // [END request_ads]

//    companion object {
        // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
//        private const val AD_UNIT_ID = "ca-app-pub-3940256099942544/9214589741"
//        private const val AD_UNIT_ID = "ca-app-pub-6717632447198427/3437837737"
//    }
}