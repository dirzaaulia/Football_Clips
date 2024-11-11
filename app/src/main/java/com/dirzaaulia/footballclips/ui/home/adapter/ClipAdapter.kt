package com.dirzaaulia.footballclips.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
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
import com.dirzaaulia.footballclips.databinding.BannerAdViewBinding
import com.dirzaaulia.footballclips.databinding.ItemClipsBinding
import com.dirzaaulia.footballclips.databinding.ShimmerItemClipsBinding
import com.dirzaaulia.footballclips.util.formatDateTime
import com.google.android.gms.ads.AdView

typealias OnClipsClicked = (Clip) -> Unit

class ClipAdapter(
    private val onClipsClicked: OnClipsClicked
) : ListAdapter<ClipState, ClipAdapter.ViewHolder>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        val isPlaceholder = currentList[position].isPlaceholder
        return if (isPlaceholder) PLACEHOLDER else {
            if (position % 5 == 0) AD else DATA
        }
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

            AD -> {
                ViewHolder.AdViewHolder(
                    BannerAdViewBinding.inflate(
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
                if (currentList.isNotEmpty()) {
                    val item = getItem(position)
                    item?.let {
                        val item = it
                        holder.bind(item)
                    }
                }
            }
            is ViewHolder.AdViewHolder -> {
                val item = getItem(position)
                holder.bindAd(item, position)
            }
        }
    }

    sealed class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        class PlaceholderViewHolder(
            binding: ShimmerItemClipsBinding
        ) : ViewHolder(binding) {
            fun bindPlaceholder() {}
        }

        class AdViewHolder(
            binding: BannerAdViewBinding
        ) : ViewHolder(binding) {
            fun bindAd(item: ClipState, position: Int) {
                val bannerHolder = this
                val adView = item.ad as AdView
                val adCardView = this.itemView as ViewGroup

                if (adCardView.childCount > 0) {
                    adCardView.removeAllViews()
                }

                if (adView.parent != null) {
                    (adView.parent as ViewGroup).removeView(adView)
                }

                adCardView.addView(adView)
            }
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
                    if (item.data.videos?.isNotEmpty() == true) {
                        type.text = item.data.videos[0].title.orEmpty()
                    }

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
        const val AD = 2
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
