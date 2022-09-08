package com.dirzaaulia.footballclips.data.sportapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Venue(
  val id: String? = null,
  val name: String? = null,
  val city: String? = null
): Parcelable