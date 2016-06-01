package com.meliismyself.restoranmeli.api;

import com.meliismyself.restoranmeli.api.response.DetailRestoran;
import com.meliismyself.restoranmeli.api.response.Restoran;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Meli Oktavia on 5/10/2016.
 */
public interface ApiClient {
    @GET("/apicafe/public/list")
    Call<Restoran> getRestoran();

    @GET("http://dev.gits.co.id/apicafe/public/list/{id}")
    Call<DetailRestoran> getDetailRestoran(@Path("id") String id);
}
