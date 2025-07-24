package com.dirzaaulia.footballclips.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.dirzaaulia.footballclips.databinding.FragmentEventBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventFragment : Fragment() {

    private lateinit var binding: FragmentEventBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
    }

    private fun setupViewPager() {
        val tabAdapter = EventTabAdapter(this)
        binding.viewpager.adapter = tabAdapter
        binding.viewpager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            if (position == 0) {
                tab.text = "Competition"
            } else tab.text = "Highlights"
        }.attach()
    }
}