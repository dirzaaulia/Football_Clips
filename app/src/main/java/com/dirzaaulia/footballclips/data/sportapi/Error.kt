package com.dirzaaulia.footballclips.data.sportapi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Error(
  val error: String? = null
): Parcelable