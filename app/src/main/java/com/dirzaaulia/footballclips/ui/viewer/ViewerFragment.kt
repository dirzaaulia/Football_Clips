package com.dirzaaulia.footballclips.ui.viewer

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.dirzaaulia.footballclips.databinding.FragmentViewerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewerFragment : Fragment() {

    private lateinit var binding: FragmentViewerBinding

    private val args: ViewerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        binding = FragmentViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadArguments()
    }

    private fun loadArguments() {
        setWebView(args.url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView(url: String) {
        binding.apply {
            webView.apply {
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                webChromeClient = object : WebChromeClient() {

                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)

                        progressBar.setProgressCompat(newProgress, true)
                        if (newProgress == 100) {
                            progressBar.isVisible = false
                        }
                    }
                }

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