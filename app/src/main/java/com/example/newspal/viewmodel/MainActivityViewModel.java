package com.example.newspal.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newspal.model.Article;
import com.example.newspal.model.NewsRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private NewsRepository repository;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new NewsRepository(application);
    }
    public LiveData<List<Article>> getAllArticles(String category){
        if(repository==null)
            Log.d("TAG", "getAllArticles: Respo null");
        return repository.getMutableLiveData(category);
    }
    public LiveData<List<Article>> getSavedArticles()
    {
        if(repository==null)
            Log.d("TAG", "getAllArticles: Respo null");
        return repository.getSavedArticles();
    }
    public void saveArticle(Article article)
    {
        repository.saveArticle(article);
    }
    public void deleteArticle(Article article)
    {
        repository.deleteArticle(article);
    }
}
