package com.example.infon;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NationDetailsInterface {
    @GET("/nation/{name}") // 로그인 API 엔드포인트
    Call<String> nationDetails(@Path("name") String name);
    @PUT("/nation/{name}")
    Call<NationDto> nationRevision(@Path("name")String name,@Body NationDto nationDto);
}
