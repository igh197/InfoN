package com.example.infon;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginApiService {

    @POST("loginProc") // 로그인 API 엔드포인트
    Call<LoginDto> login(@Body LoginDto loginDto);
    @POST("user/new")
    Call<SignUpDto> signUp(@Body SignUpDto signUpDto);

}
