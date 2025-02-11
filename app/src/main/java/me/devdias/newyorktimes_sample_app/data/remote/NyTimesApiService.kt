package me.devdias.newyorktimes_sample_app.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NyTimesApiService {
    @GET("svc/topstories/v2/home.json")
    suspend fun getTopStories(
        @Query("api-key") apiKey: String
    ): Response<TopStoriesResponse>
}