package com.dirzaaulia.footballclips.ui.event

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.dirzaaulia.footballclips.databinding.FragmentViewerBinding
import com.dirzaaulia.footballclips.ui.main.ActivityViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue


@AndroidEntryPoint
class EventFragment : Fragment() {

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
        mAdView?.adUnitId = "ca-app-pub-6717632447198427/3363322717"
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

    fun refreshWebView(competition: String) {
        val url = competition
        val replacedUrl = url.replace("position:relative;padding-bottom:56.250%;", "")
        var data =
            "<html><meta name=\"viewport\" content='width=device-width, height=device-height, initial-scale=1.0,text/html,charset=utf-8'>"
        data =
            "$data<body style=\"padding: 0; margin: 0;\"><center>$replacedUrl</center></body></html>"
        binding.webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null)
    }

    private fun setupOnBackPressedDispatcher() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack()
                    } else {
                        NavHostFragment.findNavController(this@EventFragment).navigateUp()
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

                val url =
                    "<iframe src=\"https://www.scorebat.com/embed/competition/england-premier-league/?token=MjExMDVfMTczMTI2NzE2NV83MDkzYWI3MWU4MjdlZTFhYTVmM2NjMGQ5NmY0ODE5NzA5OTczMGY2\" frameborder=\"0\" width=\"600\" height=\"760\" allowfullscreen allow='autoplay; fullscreen' style=\"width:100%;height:760px;overflow:hidden;display:block;\" class=\"_scorebatEmbeddedPlayer_\"></iframe><script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = 'https://www.scorebat.com/embed/embed.js?v=arrv'; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'scorebat-jssdk'));</script>"
                val replacedUrl = url.replace("position:relative;padding-bottom:56.250%;", "")
                var data =
                    "<html><meta name=\"viewport\" content='width=device-width, height=device-height, initial-scale=1.0,text/html,charset=utf-8'>"
                data =
                    "$data<body style=\"padding: 0; margin: 0;\"><center>$replacedUrl</center></body></html>"
                loadDataWithBaseURL(null, data, "text/html", "UTF-8", null)
            }
        }
    }
}