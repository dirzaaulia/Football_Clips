package com.dirzaaulia.footballclips.repository

import androidx.annotation.WorkerThread
import com.dirzaaulia.footballclips.data.model.Clip.Companion.toClipState
import com.dirzaaulia.footballclips.network.ScoreBatService
import com.dirzaaulia.footballclips.util.ResponseResult
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class Repository @Inject constructor(
    private val scoreBatService: ScoreBatService
) {

    @WorkerThread
    fun getClips() = flow {
        emit(ResponseResult.Loading)
        try {
            val response = scoreBatService.getClips()
            response.body()?.response?.let {
                val clipStates = it.map { clip -> clip.toClipState() }
                emit(ResponseResult.Success(clipStates))
            } ?: run {
                throw HttpException(response)
            }
        } catch (throwable: Throwable) {
            emit(ResponseResult.Error(throwable))
        }
    }
}