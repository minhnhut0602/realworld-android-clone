package io.realworld.android.repository

import io.realworld.api.MediumClient
import io.realworld.api.models.entities.Article
import io.realworld.api.models.entities.ArticleData
import io.realworld.api.models.entities.Comment
import io.realworld.api.models.entities.CommentData
import io.realworld.api.models.requests.UpsertArticleRequest

object ArticleRepo {

    private  val api=MediumClient.mediumAPI
    private val authApi=MediumClient.mediumAuthAPI


    suspend fun getArticles(author:String?=null,tag:String?=null,favorite:String?=null) : List<Article>? {
        val response= api.getArticles(author,tag,favorite)
        return response.body()?.articles
    }

    suspend fun getFeedArticle():List<Article>? {
        val response=authApi.getArticleFeed()
        return response.body()?.articles
    }

    suspend fun getArticlesBySlug(slug:String) : Article?{
        val response=api.getArticlesBySlug(slug)

        return response.body()?.article
    }
    suspend fun getTags() :List<String>? {
        val response=api.getTags()
        return response.body()?.tags
    }

    suspend fun createArticle(
        title:String?,
        description:String?,
        body:String?,
        tagList:List<String>?=null
    ) :Article? {
        val response =authApi.createArticle(UpsertArticleRequest(
            ArticleData(
                title=title,
                description = description,
                body = body,
                tagList = tagList
            )
        ))

        return response.body()?.article
    }

    suspend fun updateArticle(slug:String,article:ArticleData) :Article? {
        val response =authApi.updateArticle(slug, UpsertArticleRequest(article))

        return response.body()?.article
    }

    suspend fun deleteArticle(slug:String) :Article? {

        val response=authApi.deleteArticle(slug)

        return response.body()?.article
    }

    suspend fun getComments(slug:String) :List<Comment>? {
        val response =authApi.getComments(slug)

        return response.body()?.comments
    }
    suspend fun createComment(slug:String, comment: CommentData) : Comment? {
        val response =authApi.commentOnArticle(slug,comment)

        return response.body()?.comments
    }

    suspend fun deleteComment(slug:String,id:Int) {
        authApi.deleteComment(slug,id)
    }

    suspend fun addFavorite(slug:String) :Article? {
        val response=authApi.addFavorite(slug)
        return response.body()?.article
    }

    suspend fun removeFavorite(slug:String) :Article? {
        val response =authApi.removeFavorite(slug)
        return response.body()?.article
    }

}