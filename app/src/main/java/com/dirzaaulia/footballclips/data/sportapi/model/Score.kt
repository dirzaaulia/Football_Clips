package com.dirzaaulia.footballclips.data.sportapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Score(
  val home: Int = 0,
  val away: Int = 0
): Parcelable