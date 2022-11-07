package com.dirzaaulia.footballclips.data.response

import androidx.annotation.Keep
import com.dirzaaulia.footballclips.data.model.Clip

@Keep
data class ClipsResponse(
    val response: List<Clip>?
)