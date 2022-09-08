package com.dirzaaulia.footballclips.data.sportapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fixture(
  val id: Int? = null,
  val referee: String? = null,
  val timezone: String? = null,
  val date: String? = null,
  val timestamp: Long? = null,
  val venue: Venue? = null,
  val league: League? = null,
  val goals: Score? = null
): Parcelable