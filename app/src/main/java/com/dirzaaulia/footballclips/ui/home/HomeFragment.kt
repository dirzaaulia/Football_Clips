package com.dirzaaulia.footballclips.ui.home

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.data.model.Clip
import com.dirzaaulia.footballclips.data.model.ClipState
import com.dirzaaulia.footballclips.databinding.FragmentHomeBinding
import com.dirzaaulia.footballclips.ui.home.adapter.ClipAdapter
import com.dirzaaulia.footballclips.ui.main.MainActivity
import com.dirzaaulia.footballclips.util.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("SourceLockedOrientationActivity")
class HomeFragment: Fragment() {

  private lateinit var binding: FragmentHomeBinding

  private val adapter = ClipAdapter(this::onClipsClicked)

  private val viewModel: HomeViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    activity?.let {
      it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
      (it as MainActivity).setupInsetter()
    }

    binding = FragmentHomeBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    setupAdapter()
    subscribeClips()
  }

  private fun setupAdapter() {
    binding.recyclerView.adapter = adapter
  }

  private fun subscribeClips() {
    lifecycleScope.launch {
      viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.clips.collect { state ->
          when {
            state.isLoading -> updateList(ClipState.getClipStatesPlaceholder())
            state.isSucceeded -> {
              state.success {
                updateList(it)
              }
            }
            state.isError -> {
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

  private fun updateList(list: List<ClipState>?) {
    adapter.submitList(list)
  }

  private fun onClipsClicked(item: Clip) {
    val videos = item.videos

    if (videos?.isNotEmpty() == true) {
      val url = item.videos[0].embed

      url?.let {
        val direction = HomeFragmentDirections.actionHomeFragmentToViewerFragment(it)
        findNavController().navigate(direction)
      }
    }
  }


}