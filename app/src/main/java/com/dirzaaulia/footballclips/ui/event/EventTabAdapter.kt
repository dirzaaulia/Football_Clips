package com.dirzaaulia.footballclips.ui.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class EventTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int).
        val fragment = EventTabFragment()
        fragment.arguments = Bundle().apply {
            // The object is just an integer.
            putInt("code", position)
        }
        return fragment
    }

}