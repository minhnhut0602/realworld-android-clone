package io.realworld.android.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realworld.android.repository.ArticleRepo
import io.realworld.api.models.entities.Article
import io.realworld.api.models.entities.Comment
import kotlinx.coroutines.launch

class ArticleViewModel:ViewModel() {


    private val _article =MutableLiveData<Article>()
    val article:LiveData<Article> =_article

    private val _comment =MutableLiveData<List<Comment>>()
    val comment :LiveData<List<Comment>> = _comment

    fun getArticle(slug:String) =viewModelScope.launch {
        ArticleRepo.getArticlesBySlug(slug).let {
            _article.postValue(it)
        }
    }

    fun createArticle(
        title:String?,
        description:String?,
        body:String?,
        tagList:List<String>?=null
    ) =viewModelScope.launch {
        ArticleRepo.createArticle(
                title=title,
                description = description,
                body=body,
                tagList = tagList
        )
    }

    fun getComment(slug:String) =viewModelScope.launch {
        ArticleRepo.getComments(slug).let{
            _comment.postValue(it)
        }
    }
}