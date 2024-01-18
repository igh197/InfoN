package com.example.infon;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LoginDto {
     @SerializedName("username")
     String username;
     @SerializedName("password")
     String password;
     public LoginDto(String username,String password){
          this.username=username;
          this.password=password;
     }
}