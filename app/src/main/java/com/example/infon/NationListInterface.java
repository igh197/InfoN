package com.example.infon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NationListInterface {
    @GET("/nations")
    Call<String> string_call(
        @Query("currentPage")
        int currentPage,
        @Query("currentElements")
        int currentElements
    );
}
