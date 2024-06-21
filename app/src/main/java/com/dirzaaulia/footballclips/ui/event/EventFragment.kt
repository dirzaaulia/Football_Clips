package com.dirzaaulia.footballclips.ui.event

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.dirzaaulia.footballclips.databinding.FragmentViewerBinding


class EventFragment : Fragment() {

    private lateinit var binding: FragmentViewerBinding

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
                    "<iframe src=\"https://www.scorebat.com/embed/league/euro2024/?token=MjExMDVfMTY2Nzc5MjA3OV82N2IxNTE5Njg1MTI4MTRiZjRhYzNlOGIwMDBkOGVjODBmZWNhZDRj\" frameborder=\"0\" width=\"600\" height=\"760\" allowfullscreen allow='autoplay; fullscreen' style=\"width:100%;height:760px;overflow:hidden;display:block;\" class=\"_scorebatEmbeddedPlayer_\"></iframe><script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = 'https://www.scorebat.com/embed/embed.js?v=arrv'; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'scorebat-jssdk'));</script>"
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