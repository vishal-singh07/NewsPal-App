package com.example.newspal.network;

import com.example.newspal.model.NewsAPIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsSearchService {
    @GET("top-headlines")
    Call<NewsAPIResponse> getTopHeadlines(@Query("country") String country,
                                           @Query("category") String category,
                                           @Query("apiKey") String apiKey);
}
