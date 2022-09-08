package com.dirzaaulia.footballclips.data.sportapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class League(
  val id: Int? = null,
  val name: String? = null,
  val country: String? = null,
  val logo: String? = null,
  val flag: String? = null,
  val season: Int? = null,
  val round: String? = null
): Parcelable