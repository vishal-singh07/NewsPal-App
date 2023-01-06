package com.example.newspal.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.newspal.NewsApplication
import com.example.newspal.data.*
import com.example.newspal.repository.NewsApiStatus
import com.example.newspal.repository.NewsRepository
import kotlinx.coroutines.launch

class MainViewModel(private val database: DBHelper) : ViewModel() {
    private val newsRepository = NewsRepository(database)

    val status:LiveData<NewsApiStatus> = newsRepository.status

    fun getAllArticles(category: String, country: String, apiKey: String): LiveData<List<Article>> {
        var articles :LiveData<List<Article>>  ?= null
        viewModelScope.launch {
            articles =  newsRepository.getMutableLiveData(category,country,apiKey)!!
        }
        return articles!!
    }

    fun getSavedArticles(): LiveData<List<Article>> {
        return newsRepository.getSavedArticles();
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
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}
