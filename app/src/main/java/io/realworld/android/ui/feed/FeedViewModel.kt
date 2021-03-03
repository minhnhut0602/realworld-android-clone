package io.realworld.android.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realworld.android.repository.ArticleRepo
import io.realworld.api.models.entities.Article
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    private var _feedData=MutableLiveData<List<Article>>()
    val feedData:LiveData<List<Article>> = _feedData

    fun getGlobalFeed()=viewModelScope.launch {
        ArticleRepo.getArticles()?.let {
            _feedData.postValue(it)
        }
    }
    fun getMyFeed() = viewModelScope.launch {
        ArticleRepo.getFeedArticle()?.let{
            _feedData.postValue(it)
        }
    }

    fun getUserFeed(username:String?) =viewModelScope.launch {
        ArticleRepo.getArticles(author = username)?.let {
            _feedData.postValue(it)
        }
    }

    fun getUserFavoriteFeed(username:String?) =viewModelScope.launch {
        ArticleRepo.getArticles(favorite = username )?.let {
            _feedData.postValue(it)
        }
    }

}