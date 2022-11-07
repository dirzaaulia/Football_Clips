package com.dirzaaulia.footballclips.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptor {
    companion object {
        fun create(): HttpLoggingInterceptor {
            // Add logcat logging
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            return logging
        }
    }
}

class ChuckerInterceptor {
    companion object {
        fun create(@ApplicationContext context: Context): ChuckerInterceptor {
            return ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(true)
                .build()
        }
    }
}