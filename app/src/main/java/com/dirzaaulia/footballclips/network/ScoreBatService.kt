package com.dirzaaulia.footballclips.network

import com.dirzaaulia.footballclips.data.response.ClipsResponse
import com.dirzaaulia.footballclips.util.SCOREBAT_BASE_URL
import com.dirzaaulia.footballclips.util.SCOREBAT_TOKEN
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ScoreBatService {

    @GET("video-api/v3/feed")
    suspend fun getClips(
        @Query("token") token: String = SCOREBAT_TOKEN
    ): Response<ClipsResponse>

    companion object {
        fun create(client: OkHttpClient): ScoreBatService {

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .baseUrl(SCOREBAT_BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ScoreBatService::class.java)
        }
    }
}