package com.dirzaaulia.footballclips.ui.home

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dirzaaulia.footballclips.data.model.Clip
import com.dirzaaulia.footballclips.data.model.ClipState
import com.dirzaaulia.footballclips.databinding.FragmentHomeBinding
import com.dirzaaulia.footballclips.ui.home.adapter.ClipAdapter
import com.dirzaaulia.footballclips.util.ResponseResult
import com.dirzaaulia.footballclips.util.error
import com.dirzaaulia.footballclips.util.isError
import com.dirzaaulia.footballclips.util.isLoading
import com.dirzaaulia.footballclips.util.isSucceeded
import com.dirzaaulia.footballclips.util.success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("SourceLockedOrientationActivity")
class HomeFragment : Fragment() {

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
        }

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupSwipeRefresh()
        setupRecyclerView()
        setupFloatingActionButton()
        setupAdapter()
        subscribeClips()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener { viewModel.getClips() }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (firstVisibleItemPosition > 0) {
                    binding.fab.show()
                } else {
                    binding.fab.hide()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }


    private fun setupFloatingActionButton() {
        binding.fab.setOnClickListener {
            binding.recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter
    }

    private fun subscribeClips() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.clips.collect { state ->
                    setUIFromState(state)
                    when {
                        state.isLoading -> updateList(ClipState.getClipStatesPlaceholder())
                        state.isSucceeded -> {
                            state.success {
                                updateList(it)
                            }
                        }
                        state.isError -> {
                            state.error {
                                showErrorView(
                                    it.message
                                        ?: "Error occured when fetching data from server. Please try again"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUIFromState(state: ResponseResult<List<ClipState>?>) {
        binding.apply {
            swipeRefresh.apply {
                isRefreshing = false
                isVisible = !state.isError
            }
            viewCommonError.root.isVisible = state.isError
        }
    }

    private fun showErrorView(errorMessage: String) {
        binding.viewCommonError.apply {
            title.text = errorMessage
            button.setOnClickListener { viewModel.getClips() }
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