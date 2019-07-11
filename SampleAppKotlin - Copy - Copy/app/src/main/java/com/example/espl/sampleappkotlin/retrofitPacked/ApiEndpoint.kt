package com.example.espl.sampleappkotlin.retrofitPacked

import com.example.espl.sampleappkotlin.models.CategoryResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoint {

@GET("search?api_key=qSBMxm4zaiB6um6gFjYhuMOpu9Xjy82m&limit=25&rating=G&lang=en")
    suspend fun getPeople(@Query("q") query: String,@Query("offset") offsetInt: Int): Response<CategoryResponse>

    companion object Factory {
        val BASE_URL = "https://api.giphy.com/v1/gifs/"
        fun create(): ApiEndpoint {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiEndpoint::class.java)
        }
    }

}