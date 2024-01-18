package com.example.infon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RepleInterface {
    @GET("/nation/reple/{name}")
    Call<List<Reple>> callReple(
            @Path("name")
            String name
    );
    @POST("/nation/reple/{name}")
    Call<Reple> callPostReple(
            @Path("name")
            String name,
            @Body
            Reple reple
    );
}
