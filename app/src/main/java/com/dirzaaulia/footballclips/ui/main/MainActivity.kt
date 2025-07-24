package com.dirzaaulia.footballclips.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.databinding.ActivityMainBinding
import com.dirzaaulia.footballclips.ui.about.AboutActivity
import com.dirzaaulia.footballclips.ui.dialog.DialogCompetition
import com.dirzaaulia.footballclips.ui.event.EventFragment
import com.dirzaaulia.footballclips.ui.event.EventTabFragment
import com.google.android.gms.ads.MobileAds
import com.google.android.material.color.DynamicColors
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
                val secondUrl = bundle.getString("competitionUrlSecondKey").orEmpty()
                val thirdUrl = bundle.getString("competitionUrlThirdKey").orEmpty()

                viewModel.currentCompetition = name
                refreshEventFragment(Triple(name, secondUrl, thirdUrl))
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
        initializeMobileAdsSdk()
        setupInsetter()
        setupAppBar()
        setupBottomNavigation()
    }

    private fun refreshEventFragment(data: Triple<String, String, String>) {
        val fragment = supportFragmentManager.fragments.first().childFragmentManager.fragments.last() as EventFragment
        val firstFragment = fragment.childFragmentManager.fragments.first() as EventTabFragment
        firstFragment.refreshWebView(data)

        if (fragment.childFragmentManager.fragments.size > 1) {
            val secondFragment = fragment.childFragmentManager.fragments.last() as EventTabFragment
            secondFragment.refreshWebView(data)
        }
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
                    navigateToAbout()
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
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
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
    }

    private fun navigateToAbout() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun initializeMobileAdsSdk() {
        CoroutineScope(Dispatchers.IO).launch {
            MobileAds.initialize(this@MainActivity) {}
        }
    }
}