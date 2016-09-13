package com.youtube.sample.app.net.service;

import com.youtube.sample.app.net.gson.SearchResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    @GET("?part=snippet&maxResults=10")
    Observable<SearchResponse> searchVideos(@Query("q") String query, @Query("key") String key);

}
