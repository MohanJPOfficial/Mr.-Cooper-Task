package com.mohanjp.mrcoopertask.data.source.remote.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ImageAPI {

    @Streaming
    @GET
    suspend fun getRandomImage(
        @Url imageUrl: String
    ): ResponseBody
}