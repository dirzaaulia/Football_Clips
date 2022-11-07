package com.dirzaaulia.footballclips.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Clip(
    val title: String? = null,
    val competition: String? = null,
    val matchviewUrl: String? = null,
    val competitionUrl: String? = null,
    val thumbnail: String? = null,
    val date: String? = null,
    val videos: List<Video>? = emptyList()
) : Parcelable {
    companion object {
        fun Clip.toClipState() = ClipState(data = this)
    }
}