package io.realworld.api.services

import io.realworld.api.models.entities.CommentData
import io.realworld.api.models.requests.UpsertArticleRequest
import io.realworld.api.models.requests.UserUpdateRequest
import io.realworld.api.models.response.*
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

    @GET("articles")
    suspend fun getArticles(
        @Query("author") author :String?=null,
        @Query("tag") tag :String?=null,
        @Query("favorited") favorited :String?=null
    ) :Response<ArticlesResponse>

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
    )

    @POST("articles/{slug}/comments")
    suspend fun commentOnArticle(
        @Path("slug") slug: String,
        @Body comment : CommentData
    ) :Response<CommentResponse>


    @GET("articles/{slug}/comments")
    suspend fun getComments(
        @Path("slug") slug:String
    ) :Response<CommentsResponse>

    @DELETE("articles/{slug}/comments/{id}")
    suspend fun deleteComment(
        @Path("slug") slug:String,
        @Path("id") id:Int
    )

    @POST("articles/{slug}/favorite/")
    suspend fun addFavorite(
        @Path("slug") slug: String,
    ) :Response<ArticleResponse>

    @DELETE("articles/{slug}/favorite/")
    suspend fun removeFavorite(
        @Path("slug") slug: String,
    ) :Response<ArticleResponse>

    @GET("articles/{slug}")
    suspend fun getArticlesBySlug(
        @Path("slug") slug:String
    ): Response<ArticleResponse>
}