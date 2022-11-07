package com.dirzaaulia.footballclips.di

import android.content.Context
import com.dirzaaulia.footballclips.network.ChuckerInterceptor
import com.dirzaaulia.footballclips.network.LoggingInterceptor
import com.dirzaaulia.footballclips.network.ScoreBatService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideScoreBatInterceptorClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor.create())
            .addInterceptor(ChuckerInterceptor.create(context))
            .callTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideScoreBatService(
        client: OkHttpClient
    ): ScoreBatService {
        return ScoreBatService.create(client)
    }
}