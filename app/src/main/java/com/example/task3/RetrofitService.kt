package com.example.task3

import com.example.task3.Constants.api_key
import com.example.task3.Constants.api_value
import com.example.task3.Constants.base_url
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url: HttpUrl = chain.request().url().newBuilder()
                    .addQueryParameter(api_key, api_value)
                    .build()

                val request: Request = chain.request().newBuilder()
                    .url(url)
                    .build()

                chain.proceed(request)
            }
            .build()
    }

    private val factory by lazy {
        GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        )
    }

    val instance by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(factory)
            .baseUrl(base_url)
            .build()
    }
}