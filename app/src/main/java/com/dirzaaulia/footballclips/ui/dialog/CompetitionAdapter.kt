package com.dirzaaulia.footballclips.ui.dialog

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.dirzaaulia.footballclips.databinding.ItemCompetitionBinding
import com.dirzaaulia.footballclips.ui.dialog.CompetitionAdapter.ViewHolder

typealias onCompetitionClicked = (Triple<String, String, String>) -> Unit

class CompetitionAdapter(
    private val currentCompetition: String,
    private val onCompetitionClicked: onCompetitionClicked
) : ListAdapter<Triple<String,String,String>, ViewHolder.CompetitionViewHolder>(DiffCallback()) {
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

            fun bind(item: Triple<String, String, String>) {
                binding.apply {
                    if (currentCompetition.isEmpty()) {
                        text1.isChecked = item.first == "Premier League"
                    } else {
                        text1.isChecked = item.first == currentCompetition
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

private class DiffCallback : DiffUtil.ItemCallback<Triple<String, String, String>>() {

    override fun areItemsTheSame(
        oldItem: Triple<String, String, String>,
        newItem: Triple<String, String, String>
    ): Boolean {
        return oldItem.first.length == newItem.second.length
    }

    override fun areContentsTheSame(
        oldItem: Triple<String, String, String>,
        newItem: Triple<String, String, String>
    ): Boolean {
       return oldItem == newItem
    }

}
