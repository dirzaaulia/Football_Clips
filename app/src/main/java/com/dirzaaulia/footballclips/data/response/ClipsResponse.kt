package com.dirzaaulia.footballclips.data.response

import androidx.annotation.Keep
import com.dirzaaulia.footballclips.data.Clips

@Keep
data class ClipsResponse(
  val response: List<Clips>?
)