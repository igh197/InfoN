package com.example.infon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    EditText editID, editEmail, editPassword;
    Button buttonSignUp;
    LoginApiService loginApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editID=(EditText) findViewById(R.id.editID);
        editEmail=(EditText) findViewById(R.id.editEmail);
        editPassword=(EditText) findViewById(R.id.editPassword);
        buttonSignUp=(Button) findViewById(R.id.signUp);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.39.10.90:8080/")  // 실제 API의 base URL로 수정
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginApiService = retrofit.create(LoginApiService.class);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = ((EditText) findViewById(R.id.editID)).getText().toString();
                String password = ((EditText) findViewById(R.id.editPassword)).getText().toString();
                String email = ((EditText) findViewById(R.id.editEmail)).getText().toString();
                SignUpDto signUpDto = new SignUpDto(username,password,email);
                // Retrofit을 사용하여 로그인 요청을 보냄
                Call<SignUpDto> call = loginApiService.signUp(signUpDto);
                call.enqueue(new Callback<SignUpDto>() {
                    @Override
                    public void onResponse(@NonNull Call<SignUpDto> call, @NonNull Response<SignUpDto> response) {
                        if (response.code()==200) {
                            // 로그인 성공 시 처리
                            // 예: 로그인 성공 메시지를 보여줌

                            showToast("회원가입 성공");
                            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                        } else {

                            // 로그인 실패 시 처리
                            // 예: 사용자에게 실패 메시지를 보여줌
                            showToast("회원가입 실패");

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SignUpDto> call, @NonNull Throwable t) {
                        showToast("네트워크 오류");
                    }


                });
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}


