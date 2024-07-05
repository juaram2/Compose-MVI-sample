package com.myapp.data.remote

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


interface ApiService {
    @GET("/v2/search/image")
    suspend fun searchImage(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = RECENCY
    ) : Response<ImagesModel>

    @GET("/v2/search/vclip")
    suspend fun searchVideo(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = RECENCY
    ) : Response<VideosModel>

    companion object {
        private const val BASE_URL = "https://dapi.kakao.com"
        private const val AUTH = "KakaoAK e2cdbbbae4b1f597f7d7337b42f587b3"
        const val RECENCY = "recency"

        fun create(): ApiService {
            val logger = HttpLoggingInterceptor { message ->
                Log.d("debug", "okHttpClientBuilder: $message")
            }.apply {
//                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
//                } else {
//                    HttpLoggingInterceptor.Level.NONE
//                }
            }

            val client = OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", AUTH)
                    chain.proceed(request.build())
                }
                .addInterceptor(logger)
                .build()

            val gson = GsonBuilder().registerTypeAdapter(
                LocalDateTime::class.java,
                JsonDeserializer { json, _, _ ->
                    LocalDateTime.parse(
                        json.asString,
                        DateTimeFormatter.ISO_OFFSET_DATE_TIME
                    )
                }).create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }
}