package com.dirzaaulia.footballclips.ui.home

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.data.scorebat.model.Clips
import com.dirzaaulia.footballclips.databinding.FragmentHomeBinding
import com.dirzaaulia.footballclips.ui.main.adapter.ClipsAdapter
import com.dirzaaulia.footballclips.util.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment:
  Fragment(),
  ClipsAdapter.ClipsAdapterListener {

  private lateinit var binding: FragmentHomeBinding

  private val viewModel: HomeViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    binding = FragmentHomeBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    subscribeClips()
    setupOnClickListeners()
  }

  override fun onItemClicked(item: Clips) {
    val videos = item.videos

    if (videos?.isNotEmpty() == true) {
      val url = item.videos[0].embed
      url?.let {
        val direction = HomeFragmentDirections.actionHomeFragmentToViewerFragment(it)
        findNavController().navigate(direction)
      }
    }
  }

  private fun setupOnClickListeners() {
    binding.toolbar.information.setOnClickListener {
      showInformationDialog()
    }
  }

  private fun subscribeClips() {
    lifecycleScope.launch {
      viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                  .show()
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
    MaterialAlertDialogBuilder(requireContext())
      .setTitle(getString(R.string.information))
      .setMessage(getText(R.string.data_provider))
      .setPositiveButton(getString(R.string.go_to_scorebat)) { dialog, _ ->
        dialog.dismiss()
        openLink(requireContext(), getString(R.string.scorebat_link))
      }
      .show()
  }
}