package com.comunidadedevspace.taskbeats.data.remote

import retrofit2.http.GET

interface NewsService {

    @GET("resto da url")
    suspend fun fetchData(): NewsResponse
}