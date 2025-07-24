package com.dirzaaulia.footballclips.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.databinding.ActivityAboutBinding
import com.dirzaaulia.footballclips.databinding.ActivityMainBinding
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import androidx.core.net.toUri
import com.dirzaaulia.footballclips.util.openLink

@AndroidEntryPoint
class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        preOnCreateSetup()
        super.onCreate(savedInstanceState)
        postOnCreateSetup()
        setupOnClickListeners()
    }

    private fun preOnCreateSetup() {
        DynamicColors.applyToActivityIfAvailable(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun setupInsetter() {
        binding.root.applyInsetter {
            type(statusBars = true) {
                padding(animated = true)
            }
        }
    }

    private fun postOnCreateSetup() {
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupInsetter()
    }

    private fun setupOnClickListeners() {
        binding.cardDataSource.setOnClickListener {
            val url = getString(R.string.scorebat_link)
            openLink(this, url)
        }

        binding.buttonKofi.setOnClickListener {
            val url = getString(R.string.kofi_link)
            openLink(this, url)
        }

        binding.buttonSaweria.setOnClickListener {
            val url = getString(R.string.saweria_link)
            openLink(this,url)
        }
    }
}