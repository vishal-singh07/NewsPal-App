package com.example.newspal.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM Article")
    LiveData<List<Article>> getSavedArticles();

    @Insert
    void saveArticle(Article article);

    @Delete
    void deleteSavedArticle(Article article);
}
