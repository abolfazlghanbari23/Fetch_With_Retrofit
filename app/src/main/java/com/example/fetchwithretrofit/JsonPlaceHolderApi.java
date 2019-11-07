package com.example.fetchwithretrofit;

import android.database.Observable;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    @GET
    Call<List<Post>> getPosts(
            @Url String url,
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );


    @GET
    Call<List<Post>> getPosts(
            @Url String url,
            @QueryMap Map<String, String> parameters);

    @GET
    Call<List<Post>> getPosts(@Url String url);

    @GET("posts")
    Observable<ResponseBody> makeObservableQuery();


}
