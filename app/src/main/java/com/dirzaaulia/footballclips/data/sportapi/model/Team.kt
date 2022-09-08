package com.dirzaaulia.footballclips.data.sportapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
  val id: Int? = null,
  val name: String? = null,
  val logo: String? = null,
  val winner: Boolean? = null
): Parcelable