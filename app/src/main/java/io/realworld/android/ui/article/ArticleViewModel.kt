package io.realworld.android.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realworld.api.MediumClient
import io.realworld.api.models.entities.Article
import kotlinx.coroutines.launch

class ArticleViewModel:ViewModel() {

    val api =MediumClient.mediumAPI

    private val _article =MutableLiveData<Article>()

    val article:LiveData<Article> =_article

    fun getArticle(slug:String) =viewModelScope.launch {
        val response =api.getArticlesBySlug(slug)
    }
}