package io.realworld.api.services

import io.realworld.api.models.entities.AuthData
import io.realworld.api.models.entities.User
import io.realworld.api.models.requests.AuthRequest
import io.realworld.api.models.response.ArticleResponse
import io.realworld.api.models.response.ArticlesResponse
import io.realworld.api.models.response.TagsResponse
import io.realworld.api.models.response.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface MediumAPI {

    @GET("articles")
    suspend fun getArticles(
        @Query("author") author :String?=null,
        @Query("tag") tag :String?=null,
        @Query("favorited") favorited :String?=null
    ) :Response<ArticlesResponse>

    @GET("articles/{slug}")
    suspend fun getArticlesBySlug(
        @Path("slug") slug:String
    ): Response<ArticleResponse>

    @POST("users")
    suspend fun registerUser(
        @Body user: AuthRequest
    ) : Response<UserResponse>

    @POST("users/login")
    suspend fun loginUser(
        @Body user:AuthRequest
    ) : Response<UserResponse>

    @GET("tags")
    suspend fun getTags() : Response<TagsResponse>

}