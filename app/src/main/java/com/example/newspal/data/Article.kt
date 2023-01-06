package com.example.newspal.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Entity(tableName = "Article")
@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @Ignore
    var source : Source? = Source(),

    @ColumnInfo(name = "author")
    var author : String? = null,


    @ColumnInfo(name = "title")
    var title : String? = null,

    @ColumnInfo(name = "description")
    var description : String? = null,

    @ColumnInfo(name = "url")
    var url : String? = null,

    @ColumnInfo(name = "urlToImage")
    var urlToImage : String? = null,

    @ColumnInfo(name = "publishedAt")
    var publishedAt : String? = null,

    @ColumnInfo(name = "content")
    var content : String? = null
) : Parcelable
