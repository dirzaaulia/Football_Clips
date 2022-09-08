package com.dirzaaulia.footballclips.data.sportapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Scores(
  val halftime: Score? = null,
  val fulltime: Score? = null,
  val extratime: Score? = null,
  val penalty: Score? = null
): Parcelable