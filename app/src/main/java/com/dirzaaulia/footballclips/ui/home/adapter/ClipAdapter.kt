package com.dirzaaulia.footballclips.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewbinding.ViewBinding
import coil.load
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.data.model.Clip
import com.dirzaaulia.footballclips.data.model.ClipState
import com.dirzaaulia.footballclips.databinding.ItemClipsBinding
import com.dirzaaulia.footballclips.databinding.ShimmerItemClipsBinding
import com.dirzaaulia.footballclips.util.formatDateTime

typealias OnClipsClicked = (Clip) -> Unit

class ClipAdapter(
    private val onClipsClicked: OnClipsClicked
) : ListAdapter<ClipState, ClipAdapter.ViewHolder>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        val isPlaceholder = currentList[position].isPlaceholder
        return if (isPlaceholder) PLACEHOLDER else DATA
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            PLACEHOLDER -> {
                ViewHolder.PlaceholderViewHolder(
                    ShimmerItemClipsBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }

            DATA -> {
                ViewHolder.DataViewHolder(
                    ItemClipsBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ),
                    onClipsClicked
                )
            }
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.PlaceholderViewHolder -> holder.bindPlaceholder()
            is ViewHolder.DataViewHolder -> {
                val item = getItem(position)
                item?.let { holder.bind(item) }
            }
        }
    }

    sealed class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        class PlaceholderViewHolder(
            binding: ShimmerItemClipsBinding
        ) : ViewHolder(binding) {
            fun bindPlaceholder() {}
        }

        class DataViewHolder(
            private val binding: ItemClipsBinding,
            private val onClipsClicked: OnClipsClicked
        ) : ViewHolder(binding) {

            fun bind(item: ClipState) {
                binding.apply {
                    thumbnail.load(item.data.thumbnail) {
                        val context = root.context
                        val circularProgressDrawable = CircularProgressDrawable(context).apply {
                            strokeWidth = 5f
                            centerRadius = 30f
                            start()
                        }
                        placeholder(circularProgressDrawable)
                        error(R.drawable.ic_baseline_broken_image_24)
                    }

                    date.text = formatDateTime(item.data.date)
                    type.text = item.data.videos?.get(0)?.title ?: ""
                    competition.text = item.data.competition
                    title.text = item.data.title

                    root.setOnClickListener {
                        onClipsClicked(item.data)
                    }
                }
            }
        }
    }

    companion object {
        const val PLACEHOLDER = 0
        const val DATA = 1
    }
}

private class DiffCallback : DiffUtil.ItemCallback<ClipState>() {
    override fun areItemsTheSame(oldItem: ClipState, newItem: ClipState): Boolean {
        return oldItem.data.matchviewUrl == newItem.data.matchviewUrl
    }

    override fun areContentsTheSame(oldItem: ClipState, newItem: ClipState): Boolean {
        return oldItem == newItem
    }

}
