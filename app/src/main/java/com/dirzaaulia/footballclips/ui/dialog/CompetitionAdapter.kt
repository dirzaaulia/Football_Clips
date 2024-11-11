package com.dirzaaulia.footballclips.ui.dialog

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewbinding.ViewBinding
import coil.load
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.data.model.ClipState
import com.dirzaaulia.footballclips.databinding.BannerAdViewBinding
import com.dirzaaulia.footballclips.databinding.ItemClipsBinding
import com.dirzaaulia.footballclips.databinding.ItemCompetitionBinding
import com.dirzaaulia.footballclips.databinding.ShimmerItemClipsBinding
import com.dirzaaulia.footballclips.ui.dialog.CompetitionAdapter.ViewHolder
import com.dirzaaulia.footballclips.ui.home.adapter.OnClipsClicked
import com.dirzaaulia.footballclips.util.formatDateTime
import com.google.android.gms.ads.AdView

typealias onCompetitionClicked = (Pair<String, String>) -> Unit

class CompetitionAdapter(
    private val currentCompetition: String,
    private val onCompetitionClicked: onCompetitionClicked
) : ListAdapter<Pair<String,String>, ViewHolder.CompetitionViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder.CompetitionViewHolder {
        return ViewHolder.CompetitionViewHolder(
            ItemCompetitionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            currentCompetition,
            onCompetitionClicked
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder.CompetitionViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        item?.let {
            val item = it
            holder.bind(item)
        }
    }

    sealed class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        class CompetitionViewHolder(
            private val binding: ItemCompetitionBinding,
            private val currentCompetition: String,
            private val onCompetitionClicked: onCompetitionClicked
        ) : ViewHolder(binding) {

            fun bind(item: Pair<String, String>) {
                binding.apply {
                    if (currentCompetition.isEmpty()) {
                        text1.isChecked = item.first == "Premier League"
                    } else {
                        text1.isChecked = item.first == currentCompetition
                        Log.d("CompetitionAdapter", "bind: $currentCompetition")
                    }
                    text1.text = item.first
                    text1.setOnClickListener {
                        onCompetitionClicked(item)
                    }
                }
            }
        }
    }

}

private class DiffCallback : DiffUtil.ItemCallback<Pair<String, String>>() {

    override fun areItemsTheSame(
        oldItem: Pair<String, String>,
        newItem: Pair<String, String>
    ): Boolean {
        return oldItem.first.length == newItem.second.length
    }

    override fun areContentsTheSame(
        oldItem: Pair<String, String>,
        newItem: Pair<String, String>
    ): Boolean {
       return oldItem == newItem
    }

}
