package com.dirzaaulia.footballclips.ui.event

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.databinding.FragmentEventBinding
import com.dirzaaulia.footballclips.databinding.FragmentViewerBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class EventTabFragment : Fragment() {

    private lateinit var binding: FragmentViewerBinding

    private var code: Int = 0

    private var mAdView: AdView? = null

    // Determine the screen width to use for the ad width.
    private val adWidth: Int
        get() {
            val displayMetrics = resources.displayMetrics
            val adWidthPixels =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val windowMetrics: WindowMetrics =
                        requireActivity().windowManager.currentWindowMetrics
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAds()
        arguments?.takeIf { it.containsKey("code") }?.apply {
            val code = getInt("code")
            this@EventTabFragment.code = code
            setWebView()
        }
    }

    private fun setupAds() {
        mAdView = activity?.let { AdView(it) }
        binding.bannerAdView.addView(mAdView)
        mAdView?.adUnitId = "ca-app-pub-6717632447198427/3363322717"
        mAdView?.setAdSize(
            AdSize.getInlineAdaptiveBannerAdSize(adWidth, 100)
        )
        mAdView?.loadAd(AdRequest.Builder().build())
    }

    fun refreshWebView(data: Triple<String, String, String>) {
        val url = if (code == 0) data.second else data.third
        binding.webView.loadUrl(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        binding.apply {
            webView.apply {
                isNestedScrollingEnabled = false
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

                val data = Triple(
                    "Premier League",
                    "https://www.scorebat.com/embed/competition/england-premier-league/?token=MjExMDVfMTc1MzEwMzY0OV84ODVjNWE1Y2E3MTRlODBhYTAxOWU1YmE4ZDBmYjNiNWFmZDYyYjcx",
                    "https://www.scorebat.com/embed/videofeed/competition/england-premier-league/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5"
                )
                val url = if (code == 0) data.second else data.third
                MobileAds.registerWebView(this)
                loadUrl(url)
            }
        }
    }

    private fun setupOnBackPressedDispatcher() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack()
                    } else {
                        NavHostFragment.findNavController(this@EventTabFragment).navigateUp()
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
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

}