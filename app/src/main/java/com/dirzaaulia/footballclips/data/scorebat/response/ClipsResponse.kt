package com.dirzaaulia.footballclips.data.scorebat.response

import androidx.annotation.Keep
import com.dirzaaulia.footballclips.data.scorebat.model.Clips

@Keep
data class ClipsResponse(
  val response: List<Clips>?
)