package com.example.infon;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NewNationApiService {
    @POST("/nation/new")
    Call<NationDto> newNation(@Body NationDto nationDto);
}
