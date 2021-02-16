package io.realworld.api.services

import io.realworld.api.models.ArticlesResponse
import retrofit2.Response
import retrofit2.http.GET

interface MediumAPI {

    @GET("articles")
    suspend fun getArticles() :Response<ArticlesResponse>
}