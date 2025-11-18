package com.example.mypractice.data.remote

import com.example.mypractice.domain.model.UnsplashPhoto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): List<UnsplashPhoto>
}