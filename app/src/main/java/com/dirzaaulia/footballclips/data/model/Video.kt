package com.dirzaaulia.footballclips.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Video(
    val title: String?,
    val embed: String?
) : Parcelable