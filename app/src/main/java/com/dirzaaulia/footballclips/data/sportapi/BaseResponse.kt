package com.dirzaaulia.footballclips.data.sportapi

import com.dirzaaulia.footballclips.data.sportapi.model.Fixture

abstract class BaseResponse(
  open val get: String = "",
  open val errors: List<Error> = emptyList(),
  open val results: Int = 0,
  open val paging: Paging? = null,
)