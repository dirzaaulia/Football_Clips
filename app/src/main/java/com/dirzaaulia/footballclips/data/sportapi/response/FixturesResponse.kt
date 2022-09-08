package com.dirzaaulia.footballclips.data.sportapi.response

import com.dirzaaulia.footballclips.data.sportapi.BaseResponse
import com.dirzaaulia.footballclips.data.sportapi.Error
import com.dirzaaulia.footballclips.data.sportapi.Paging
import com.dirzaaulia.footballclips.data.sportapi.model.Fixture

data class FixturesResponse(
  override val get: String = "",
  override val errors: List<Error> = emptyList(),
  override val results: Int = 0,
  override val paging: Paging? = null,
  val response: List<Fixture>? = emptyList()
): BaseResponse()