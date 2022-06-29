package com.dirzaaulia.footballclips.ui.viewer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dirzaaulia.footballclips.databinding.ActivityViewerBinding

class ViewerActivity : AppCompatActivity() {

  private lateinit var binding: ActivityViewerBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityViewerBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupView()
  }

  private fun setupView() {
    loadExtra()
  }

  private fun loadExtra() {
    intent?.let {
      val url = it.getStringExtra(URL)
      setWebView(url)
    }
  }

  @SuppressLint("SetJavaScriptEnabled")
  private fun setWebView(url: String?) {
    binding.webView.apply {
      settings.javaScriptEnabled = true
      settings.loadWithOverviewMode = true
      settings.useWideViewPort = true

      url?.let {
        val replacedUrl = it.replace("position:relative;padding-bottom:56.250%;", "")
        var data =
          "<html><meta name=\"viewport\" content='width=device-width, height=device-height, initial-scale=1.0,text/html,charset=utf-8'>"
        data = "$data<body style=\"padding: 0; margin: 0;\"><center>$replacedUrl</center></body></html>"
//        val formattedUrl = it.replace("<div style='width:100%;height:0px;position:relative;padding-bottom:56.250%;'>", "")
//          .replace("</div>","")
//        val html = "<head><style type=\"text/css\"> html, body { width:100%; height: 100%; margin: 0px; padding: 0px; }</style></head><body>$formattedUrl</body>"
        loadDataWithBaseURL(null, data, "text/html", "UTF-8", null)
      }
    }
  }

  companion object {
    private const val URL = "URL"

    fun newIntent(context: Context, url: String): Intent {
      val intent = Intent(context, ViewerActivity::class.java).apply {
        putExtra(URL, url )
      }
      return intent
    }
  }
}