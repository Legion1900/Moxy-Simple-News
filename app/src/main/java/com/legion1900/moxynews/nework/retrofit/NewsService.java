package com.legion1900.moxynews.nework.retrofit;

import com.legion1900.moxynews.nework.data.Response;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NewsService {
    @GET("v2/everything")
    Call<Response> queryNews(@QueryMap Map<String, String> options);
}
