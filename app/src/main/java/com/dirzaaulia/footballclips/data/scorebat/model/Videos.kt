package com.dirzaaulia.footballclips.data.scorebat.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Videos(
  val title: String?,
  val embed: String?
): Parcelable