package com.dirzaaulia.footballclips.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Clips(
  val title: String?,
  val competition: String?,
  val matchviewUrl: String?,
  val competitionUrl: String?,
  val thumbnail: String?,
  val date: String?,
  val videos: List<Videos>?
): Parcelable