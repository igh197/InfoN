package com.example.infon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private LoginApiService loginApiService;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.39.10.90:8080/")  // 실제 API의 base URL로 수정
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loginApiService = retrofit.create(LoginApiService.class);

        // 로그인 버튼 클릭 시
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 아이디와 비밀번호를 가져옴
                String username = ((EditText) findViewById(R.id.editID)).getText().toString();
                String password = ((EditText) findViewById(R.id.editPassword)).getText().toString();
                LoginDto loginDto = new LoginDto(username,password);
                // Retrofit을 사용하여 로그인 요청을 보냄
                Call<LoginDto> call = loginApiService.login(loginDto);
                call.enqueue(new Callback<LoginDto>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginDto> call, @NonNull Response<LoginDto> response) {
                        if (response.code()==200) {
                            // 로그인 성공 시 처리
                            // 예: 로그인 성공 메시지를 보여줌

                            showToast("로그인 성공");
                            startActivity(new Intent(MainActivity.this,NationListActivity.class));
                        } else {

                            // 로그인 실패 시 처리
                            // 예: 사용자에게 실패 메시지를 보여줌
                            showToast("로그인 실패");

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginDto> call, @NonNull Throwable t) {
                        showToast("로그인 실패");
                    }


                });
            }
        });
        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}