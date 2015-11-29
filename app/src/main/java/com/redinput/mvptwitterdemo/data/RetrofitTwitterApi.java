package com.redinput.mvptwitterdemo.data;

import com.redinput.mvptwitterdemo.model.entities.SearchResult;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface RetrofitTwitterApi {

    @GET("search/tweets.json")
    Call<SearchResult> searchTweets(
            @Query("q") String query,
            @Query("result_type") String type
    );
}
