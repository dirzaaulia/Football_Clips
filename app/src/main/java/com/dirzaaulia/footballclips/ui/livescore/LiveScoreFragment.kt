package com.dirzaaulia.footballclips.ui.livescore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.databinding.FragmentViewerBinding
import com.dirzaaulia.footballclips.util.openLink
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LiveScoreFragment: Fragment() {

  private lateinit var binding: FragmentViewerBinding

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
    setupOnClickListeners()
    setWebView()
  }

  private fun setupOnClickListeners() {
    binding.toolbar.information.setOnClickListener {
      showInformationDialog()
    }
  }

  private fun showInformationDialog() {
    MaterialAlertDialogBuilder(requireContext())
      .setTitle(getString(R.string.information))
      .setMessage(getText(R.string.data_provider))
      .setPositiveButton(getString(R.string.go_to_scorebat)) { dialog, _ ->
        dialog.dismiss()
        openLink(requireContext(), getString(R.string.scorebat_link))
      }
      .show()
  }

  @SuppressLint("SetJavaScriptEnabled")
  private fun setWebView() {
    binding.apply {
      toolbar.title.text = getString(R.string.livescore)
      webView.apply {
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        webChromeClient = object: WebChromeClient() {

          override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)

            progressBar.setProgressCompat(newProgress, true)
            if (newProgress == 100) {
              progressBar.isVisible = false
            }
          }
        }

        val url = "<iframe src=\"https://www.scorebat.com/embed/livescore/?token=MjExMDVfMTY2MTkyNzk1N19iNTk0MDA0MmJmMDhiNzdjZjY4NjUxYWRlMTlmM2M0MTc5MTJiNTkx\" frameborder=\"0\" width=\"600\" height=\"760\" allowfullscreen allow='autoplay; fullscreen' style=\"width:100%;height:760px;overflow:hidden;display:block;\" class=\"_scorebatEmbeddedPlayer_\"></iframe><script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = 'https://www.scorebat.com/embed/embed.js?v=arrv'; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'scorebat-jssdk'));</script>"
        val replacedUrl = url.replace("position:relative;padding-bottom:56.250%;", "")
        var data =
          "<html><meta name=\"viewport\" content='width=device-width, height=device-height, initial-scale=1.0,text/html,charset=utf-8'>"
        data = "$data<body style=\"padding: 0; margin: 0;\"><center>$replacedUrl</center></body></html>"
        loadDataWithBaseURL(null, data, "text/html", "UTF-8", null)
      }
    }
  }
}