package com.dirzaaulia.footballclips.ui.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.data.Clips
import com.dirzaaulia.footballclips.databinding.ActivityMainBinding
import com.dirzaaulia.footballclips.ui.main.adapter.ClipsAdapter
import com.dirzaaulia.footballclips.ui.viewer.ViewerActivity
import com.dirzaaulia.footballclips.util.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity :
  AppCompatActivity(),
  ClipsAdapter.ClipsAdapterListener {

  private lateinit var binding: ActivityMainBinding

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupView()
  }

  override fun onItemClicked(item: Clips) {
    val videos = item.videos

    if (videos?.isNotEmpty() == true) {
      val url = item.videos[0].embed
      url?.let {
        val intent = ViewerActivity.newIntent(this, it)
        startActivity(intent)
      }
    }
  }

  private fun setupView() {
    subscribeClips()
    setupOnClickListeners()
  }

  private fun setupOnClickListeners() {
    binding.information.setOnClickListener {
      showInformationDialog()
    }
  }

  private fun subscribeClips() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.clips.collect { state ->
          when {
            state.isLoading -> {
              binding.apply {
                progressBar.isVisible = true
                recyclerView.isVisible = false
              }
            }
            state.isSucceeded -> {
              binding.apply {
                progressBar.isVisible = false
                recyclerView.isVisible = true
              }
              state.success {
                setAdapter(it)
              }
            }
            state.isError -> {
              binding.apply {
                progressBar.isVisible = false
                recyclerView.isVisible = true
              }
              state.error {
                Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_INDEFINITE)
                  .setAction("Retry") {
                    viewModel.getClips()
                  }
              }
            }
          }
        }
      }
    }
  }

  private fun setAdapter(list: List<Clips>?) {
    val adapter = ClipsAdapter(this).apply {
      submitList(list)
    }
    binding.recyclerView.adapter = adapter
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
}