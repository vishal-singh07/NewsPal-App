package com.example.newspal.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ArticleDao {

    @Query("SELECT * FROM Article")
    fun getSavedArticles(): LiveData<List<Article>>

    @Insert
    suspend fun saveArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)

}