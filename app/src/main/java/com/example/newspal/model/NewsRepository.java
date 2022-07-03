package com.example.newspal.model;

import static android.content.Context.MODE_APPEND;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newspal.R;
import com.example.newspal.network.NewsSearchService;
import com.example.newspal.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private ArticleDao articleDao;
    private LiveData<List<Article>> savedArticles;
    private ArrayList<Article> articles = new ArrayList<>();
    private MutableLiveData<List<Article>> mutableLiveData = new MutableLiveData<>();
    private Application application;
    public NewsRepository(Application application)
    {
        this.application = application;
        DBHelper db =  DBHelper.getInstance(application);
        articleDao = db.articleDao();
        savedArticles =  articleDao.getSavedArticles();
    }
    public MutableLiveData<List<Article>> getMutableLiveData(String category)
    {
        SharedPreferences preferences = application.getSharedPreferences("My_Shared_Pref", Context.MODE_PRIVATE);
        String country = preferences.getString("country","in");
        NewsSearchService newsSearchService = RetrofitInstance.getService();
        Call<NewsAPIResponse> call = newsSearchService.getTopHeadlines(country,category, application.getApplicationContext().getString(R.string.api_key));
        call.enqueue(new Callback<NewsAPIResponse>() {
            @Override
            public void onResponse(Call<NewsAPIResponse> call, Response<NewsAPIResponse> response) {
                NewsAPIResponse result = response.body();

                if (result != null && result.getArticles() != null){
                    articles = (ArrayList<Article>) result.getArticles();
                    mutableLiveData.setValue(articles);
                }
            }

            @Override
            public void onFailure(Call<NewsAPIResponse> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }
    public LiveData<List<Article>> getSavedArticles()
    {
        return savedArticles;
    }
    public void saveArticle(Article article)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                articleDao.saveArticle(article);
            }
        });
    }
    public void deleteArticle(Article article)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                articleDao.deleteSavedArticle(article);
            }
        });
    }
}
