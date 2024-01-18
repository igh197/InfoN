package com.example.infon;

import com.google.gson.annotations.SerializedName;

public class SignUpDto {
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    @SerializedName("email")
    String email;

    public SignUpDto(String username,String password,String email){
        this.username=username;

        this.password=password;
        this.email=email;
    }
}
