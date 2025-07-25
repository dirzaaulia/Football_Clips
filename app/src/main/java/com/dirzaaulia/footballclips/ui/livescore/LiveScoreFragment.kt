package com.dirzaaulia.footballclips.ui.livescore

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.dirzaaulia.footballclips.databinding.FragmentViewerBinding
import com.dirzaaulia.footballclips.ui.main.MainActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class LiveScoreFragment : Fragment() {

    private lateinit var binding: FragmentViewerBinding

    private var mAdView: AdView? = null

    // Determine the screen width to use for the ad width.
    private val adWidth: Int
        get() {
            val displayMetrics = resources.displayMetrics
            val adWidthPixels =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val windowMetrics: WindowMetrics = requireActivity().windowManager.currentWindowMetrics
                    windowMetrics.bounds.width()
                } else {
                    displayMetrics.widthPixels
                }
            val density = displayMetrics.density
            return (adWidthPixels / density).toInt()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupOnBackPressedDispatcher()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWebView()

        mAdView = activity?.let { AdView(it) }
        binding.bannerAdView.addView(mAdView)
        mAdView?.adUnitId = "ca-app-pub-6717632447198427/3722415366"
        mAdView?.setAdSize(
            AdSize.getInlineAdaptiveBannerAdSize(
                adWidth,
                100
            )
        )
        mAdView?.loadAd(AdRequest.Builder().build())
    }

    override fun onResume() {
        mAdView?.resume()
        super.onResume()
    }

    override fun onPause() {
        mAdView?.pause()
        super.onPause()
    }

    override fun onDestroy() {
        mAdView?.destroy()
        super.onDestroy()
    }

    private fun setupOnBackPressedDispatcher() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack()
                    } else {
                        NavHostFragment.findNavController(this@LiveScoreFragment).navigateUp()
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        binding.apply {
            webView.apply {
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                webViewClient = object : WebViewClient() {

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        binding.progressBar.isVisible = true
                        super.onPageStarted(view, url, favicon)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        binding.progressBar.isVisible = false
                        super.onPageFinished(view, url)
                    }

                }

                MobileAds.registerWebView(this)
                val url = "https://www.scorebat.com/embed/livescore/?token=MjExMDVfMTc1MjU0ODc2N19kMGM1OGQyNDllZjlmNjdiNTY3ZTZiYzM1OWRjMjRhYjQ2MmRkNTJm"
                loadUrl(url)
            }
        }
    }
}