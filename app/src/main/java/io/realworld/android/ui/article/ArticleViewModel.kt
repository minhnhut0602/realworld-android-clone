package io.realworld.android.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realworld.android.repository.ArticleRepo
import io.realworld.api.MediumClient
import io.realworld.api.models.entities.Article
import io.realworld.api.models.entities.ArticleData
import kotlinx.coroutines.launch

class ArticleViewModel:ViewModel() {


    private val _article =MutableLiveData<Article>()

    val article:LiveData<Article> =_article

    fun getArticle(slug:String) =viewModelScope.launch {
        ArticleRepo.getArticlesBySlug(slug).let {
            _article.postValue(it)
        }
    }

    fun createArticle(article: ArticleData) =viewModelScope.launch {
        ArticleRepo.createArticle(article)
    }
}