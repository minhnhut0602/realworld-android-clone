package io.realworld.api.services

import io.realworld.api.models.entities.Article
import io.realworld.api.models.entities.User
import io.realworld.api.models.requests.AuthRequest
import io.realworld.api.models.requests.UpsertArticleRequest
import io.realworld.api.models.requests.UserUpdateRequest
import io.realworld.api.models.response.ArticleResponse
import io.realworld.api.models.response.ArticlesResponse
import io.realworld.api.models.response.ProfileResponse
import io.realworld.api.models.response.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface MediumAuthAPI {



    @GET("user")
    suspend fun getUser() :Response<UserResponse>

    @PUT("user")
    suspend fun updateUser(
        @Body user : UserUpdateRequest
    ) :Response<UserResponse>

    @GET("profiles/{username}")
    suspend fun getProfile(
        @Path("username") username:String
    ) :Response<ProfileResponse>

    @POST("profiles/{username}/follow")
    suspend fun followUser(
        @Path("username") username:String
    ) :Response<ProfileResponse>

    @DELETE("profiles/{username}/follow")
    suspend fun unfollowUser(
        @Path("username") username:String
    ) :Response<ProfileResponse>

    @POST("articles")
    suspend fun createArticle(
        @Body article: UpsertArticleRequest
    ) :Response<ArticleResponse>

    @GET("articles/feed")
    suspend fun getArticleFeed() :Response<ArticlesResponse>


    @PUT("articles/{slug}")
    suspend fun updateArticle(
        @Path("slug") slug:String,
        @Body article :UpsertArticleRequest
    ) :Response<ArticleResponse>

    @DELETE("articles/{slug}")
    suspend fun deleteArticle(
        @Path("slug") slug:String,
    ) :Response<ArticleResponse>
}