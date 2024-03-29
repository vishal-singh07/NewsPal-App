package com.example.newspal.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsAPIResponse(
     @SerializedName("status")
     @Expose
     var status       : String? = null,
     @SerializedName("totalResults")
     @Expose
     var totalResults : Int? = null,
     @SerializedName("articles")
     @Expose
     var articles     : ArrayList<Article> = arrayListOf(),

)
