package com.example.newspal.network

import com.example.newspal.data.NewsAPIResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/v2/"


/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
/**
 * A public interface that exposes the [getTopHeadlines] method
 */
interface NewsApiService {
    @GET("top-headlines")
    fun getTopHeadlines(@Query("country") country: String?,
                        @Query("category") category: String?,
                        @Query("apiKey") apiKey: String?): Call<NewsAPIResponse?>?
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object NewsApi {
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}
