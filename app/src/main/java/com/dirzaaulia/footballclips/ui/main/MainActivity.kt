package com.dirzaaulia.footballclips.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.databinding.ActivityMainBinding
import com.dirzaaulia.footballclips.ui.dialog.DialogCompetition
import com.dirzaaulia.footballclips.ui.event.EventFragment
import com.dirzaaulia.footballclips.util.openLink
import com.google.android.gms.ads.MobileAds
import com.google.android.material.color.DynamicColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    private val viewModel: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        preOnCreateSetup()
        super.onCreate(savedInstanceState)
        postOnCreateSetup()

        supportFragmentManager
            .setFragmentResultListener("competitionKey", this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported.
                val name = bundle.getString("competitionNameKey").orEmpty()
                val url = bundle.getString("competitionUrlKey").orEmpty()

                viewModel.currentCompetition = name
                refreshEventFragment(url)
            }
    }

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
        Log.d("MainActivity", "Google Mobile Ads SDK Version: " + MobileAds.getVersion())

        initializeMobileAdsSdk()
    }

    private fun refreshEventFragment(currentCompetition: String) {
        val fragment = supportFragmentManager.fragments.first().childFragmentManager.fragments.last() as EventFragment
        fragment.refreshWebView(currentCompetition)
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

                R.id.menu_competition -> {
                    val dialog = DialogCompetition.newInstance(
                        viewModel.currentCompetition
                    )
                    dialog.show(supportFragmentManager, "MyDialogFragment")
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
                        R.id.eventFragment -> getString(R.string.league)
                        else -> ""
                    }

                    binding.appBar.toolbar.menu.findItem(R.id.menu_competition).isVisible = destination.id == R.id.eventFragment
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

//    // [START request_ads]
    private fun initializeMobileAdsSdk() {
        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainActivity) {}
        }
    }
    // [END request_ads]
}