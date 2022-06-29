package com.dirzaaulia.footballclips.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.data.Clips
import com.dirzaaulia.footballclips.databinding.ItemClipsBinding
import com.dirzaaulia.footballclips.util.formatDateTime

class ClipsAdapter(
  private val listener: ClipsAdapterListener
): ListAdapter<Clips, ClipsAdapter.ViewHolder>(DiffCallback()){

  interface ClipsAdapterListener {
    fun onItemClicked(item: Clips)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
      ItemClipsBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      ),
      listener
    )
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = getItem(position)
    if (item != null) {
      holder.bind(item)
    }
  }

  class ViewHolder(
    private val binding: ItemClipsBinding,
    private val listener: ClipsAdapterListener
  ): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Clips) {
      binding.apply {
        thumbnail.load(item.thumbnail) {
          val context = root.context
          val circularProgressDrawable = CircularProgressDrawable(context).apply {
            strokeWidth = 5f
            centerRadius = 30f
            setColorSchemeColors(ContextCompat.getColor(context, R.color.grey_700))
            start()
          }
          placeholder(circularProgressDrawable)
          error(R.drawable.ic_baseline_broken_image_24)
        }
        date.text = formatDateTime(item.date)
        competition.text = item.competition
        title.text = item.title

        root.setOnClickListener {
          listener.onItemClicked(item)
        }
      }
    }
  }
}

private class DiffCallback: DiffUtil.ItemCallback<Clips>() {
  override fun areItemsTheSame(oldItem: Clips, newItem: Clips): Boolean {
    return oldItem.matchviewUrl == newItem.matchviewUrl
  }

  override fun areContentsTheSame(oldItem: Clips, newItem: Clips): Boolean {
    return oldItem == newItem
  }

}
