package com.example.newspal.repository


import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.newspal.NewsApplication
import com.example.newspal.data.*
import com.example.newspal.network.NewsApi
import retrofit2.*

enum class NewsApiStatus { LOADING, ERROR, DONE }

class NewsRepository(private val database: DBHelper) {

    private var articleDao: ArticleDao? = null
    private var savedArticles: LiveData<List<Article>> ?= null
    private var articles: ArrayList<Article> = ArrayList<Article>()
    private val mutableLiveData: MutableLiveData<List<Article>> = MutableLiveData<List<Article>>()
    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus> = _status


    init {
        articleDao = database.articleDao()
        savedArticles = articleDao?.getSavedArticles()
    }

    fun getMutableLiveData(category: String?, country: String?, apiKey: String?): MutableLiveData<List<Article>>? {
        _status.value = NewsApiStatus.LOADING
        val newsApiService =  NewsApi.retrofitService
        val call: Call<NewsAPIResponse?>? = newsApiService.getTopHeadlines(country, category, apiKey)
        call?.enqueue(object : Callback<NewsAPIResponse?> {
            override fun onResponse(
                call: Call<NewsAPIResponse?>,
                response: Response<NewsAPIResponse?>
            ) {
                val result: NewsAPIResponse? = response.body()
                if (result?.articles != null) {
                    articles = result.articles
                    mutableLiveData.value = articles
                    _status.value = NewsApiStatus.DONE
                }
            }

            override fun onFailure(call: Call<NewsAPIResponse?>, t: Throwable) {
                _status.value = NewsApiStatus.ERROR
                Log.d("TAG", "onFailure: ${status.value}")
            }
        })
        return mutableLiveData
    }

    fun getSavedArticles(): LiveData<List<Article>> {
        return savedArticles!!
    }

    suspend fun saveArticle(article: Article) {
        articleDao?.saveArticle(article)
    }

    suspend fun deleteArticle(article: Article) {
       articleDao?.deleteArticle(article)
    }


}