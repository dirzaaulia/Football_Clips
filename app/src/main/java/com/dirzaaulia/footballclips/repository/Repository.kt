package com.dirzaaulia.footballclips.repository

import androidx.annotation.WorkerThread
import com.dirzaaulia.footballclips.network.Service
import com.dirzaaulia.footballclips.util.NotFoundException
import com.dirzaaulia.footballclips.util.ResponseResult
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class Repository @Inject constructor(
  private val service: Service
) {

  @WorkerThread
  fun getClips() = flow {
    emit(ResponseResult.Loading)
    try {
      val response = service.getClips()
      response.body()?.response?.let {
        emit(ResponseResult.Success(it))
      } ?: run {
        throw HttpException(response)
      }
    } catch (throwable: Throwable) {
      emit(ResponseResult.Error(throwable))
    }
  }
}