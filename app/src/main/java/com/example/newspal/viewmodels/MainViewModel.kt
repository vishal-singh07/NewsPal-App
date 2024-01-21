package com.example.newspal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newspal.BuildConfig
import com.example.newspal.data.Article
import com.example.newspal.data.DBHelper
import com.example.newspal.repository.NewsApiStatus
import com.example.newspal.repository.NewsRepository
import kotlinx.coroutines.launch


class MainViewModel(database: DBHelper) : ViewModel() {
    private val newsRepository = NewsRepository(database)
    val status:LiveData<NewsApiStatus> = newsRepository.status

    fun getAllArticles(category: String, country: String): LiveData<List<Article>> {
        var articles: LiveData<List<Article>>? = null
        viewModelScope.launch {
            articles =  newsRepository.getMutableLiveData(category,country,BuildConfig.API_KEY)!!
        }
        return articles!!
    }

    fun getSavedArticles(): LiveData<List<Article>> {
        return newsRepository.getSavedArticles()
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.saveArticle(article)
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.deleteArticle(article)
        }
    }
}


class MainViewModelFactory(private val database: DBHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
