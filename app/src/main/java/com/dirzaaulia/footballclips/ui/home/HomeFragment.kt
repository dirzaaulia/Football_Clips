package com.dirzaaulia.footballclips.ui.home

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
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
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
@SuppressLint("SourceLockedOrientationActivity")
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val adapter = ClipAdapter(this::onClipsClicked)

    private val viewModel: HomeViewModel by viewModels()

    var recyclerViewItems = mutableListOf<ClipState>()

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
        subscribeClips()
        setupAdapter()
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
                        state.isLoading -> updateListForPlaceholder(
                            ClipState.getClipStatesPlaceholder()
                        )
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

    private fun updateListForPlaceholder(list: List<ClipState>?) {
        adapter.submitList(list)
    }

    private fun updateList(list: List<ClipState>?) {
        val newList = addBannerAds(list ?: emptyList())
        recyclerViewItems = newList.toMutableList()
        loadBannerAds(newList)
        adapter.submitList(newList)
    }

    /** Adds banner ads to the items list. */
    private fun addBannerAds(list: List<ClipState>): List<ClipState> {
        // Loop through the items array and place a new banner ad in every ith position in
        // the items List.
        val mutableList = list.toMutableList()
        var i = 0
        while (i <= mutableList.size) {
            val adView = AdView(requireContext())
            adView.setAdSize(
                AdSize.getPortraitInlineAdaptiveBannerAdSize(requireContext(), adWidth)
            )
            adView.adUnitId = AD_UNIT_ID
            val item = ClipState(data = Clip(), ad = adView)
            mutableList.add(i, item)
            i += ITEMS_PER_AD
        }
        return mutableList.toList()
    }

    /** Sets up and loads the banner ads. */
    private fun loadBannerAds(list: List<ClipState>) {
        // Load the first banner ad in the items list (subsequent ads will be loaded automatically
        // in sequence).
        loadBannerAd(0, list)
    }

    /** Loads the banner ads in the items list. */
    private fun loadBannerAd(index: Int, list: List<ClipState>) {
        if (index >= list.size) {
            return
        }
        val item =
            list[index].ad
                ?: throw ClassCastException("Expected item at index $index to be a banner ad ad.")

        // Set an AdListener on the AdView to wait for the previous banner ad
        // to finish loading before loading the next ad in the items list.
        item.adListener =
            object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    // The previous banner ad loaded successfully, call this method again to
                    // load the next ad in the items list.
                    loadBannerAd(index + ITEMS_PER_AD, list)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // The previous banner ad failed to load. Call this method again to load
                    // the next ad in the items list.
                    val error =
                        String.format(
                            Locale.getDefault(),
                            "domain: %s, code: %d, message: %s",
                            loadAdError.domain,
                            loadAdError.code,
                            loadAdError.message,
                        )
                    Log.e(
                        "MainActivity",
                        "The previous banner ad failed to load with error: " +
                                error +
                                ". Attempting to" +
                                " load the next banner ad in the items list.",
                    )
                    loadBannerAd(index + ITEMS_PER_AD, list)
                }
            }

        // Load the banner ad.
        item.loadAd(AdRequest.Builder().build())
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

    override fun onResume() {
        for (item in recyclerViewItems) {
            item.ad?.resume()
        }
        super.onResume()
    }

    override fun onPause() {
        for (item in recyclerViewItems) {
            item.ad?.pause()
        }
        super.onPause()
    }

    override fun onDestroy() {
        for (item in recyclerViewItems) {
           item.ad?.destroy()
        }
        super.onDestroy()
    }

    companion object {
        //         This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
//        private const val AD_UNIT_ID = "ca-app-pub-3940256099942544/9214589741"
        private const val AD_UNIT_ID = "ca-app-pub-6717632447198427/3437837737"
        const val ITEMS_PER_AD = 5
    }
}