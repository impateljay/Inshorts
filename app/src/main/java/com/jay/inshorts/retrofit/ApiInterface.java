package com.jay.inshorts.retrofit;

import com.jay.inshorts.model.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jay on 16-09-2017.
 */

public interface ApiInterface {
    @GET("newsjson")
    Call<List<News>> getNews();
}
