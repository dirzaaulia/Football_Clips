package com.dirzaaulia.footballclips.data.sportapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Teams(
  val home: Team? = null,
  val away: Team? = null
): Parcelable