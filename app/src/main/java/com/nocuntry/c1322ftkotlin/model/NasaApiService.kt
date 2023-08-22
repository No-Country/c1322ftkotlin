package com.nocuntry.c1322ftkotlin.model

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {
    @GET("planetary/apod")
    suspend fun getApod(
        @Query("date") date: String,
        @Query("api_key") apiKey: String = "8wtiBy1sx8gWy4fs7VM3d7R8BGLxf0Wl3UEbVa72"
    ): Response<ApodResponse>

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/"

        fun create(): NasaApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(NasaApiService::class.java)
        }
    }
}
