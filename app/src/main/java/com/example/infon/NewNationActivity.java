package com.example.infon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewNationActivity extends AppCompatActivity {
    Button editSave;
    EditText name, capital, currency, population, contin, contents;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_nation);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.39.10.90:8080/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        name = findViewById(R.id.editName);
        capital = findViewById(R.id.editCapital);
        currency = findViewById(R.id.editCurrency);
        population = findViewById(R.id.editPopulation);
        contin = findViewById(R.id.editContin);
        contents = findViewById(R.id.editContents);
        editSave = findViewById(R.id.editSave);
        NewNationApiService newNationApiService = retrofit.create(NewNationApiService.class);
        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NationDto nationDto = new NationDto(name,capital,currency,population,contin,contents);
                // Retrofit을 사용하여 로그인 요청을 보냄
                Call<NationDto> call = newNationApiService.newNation(nationDto);
                call.enqueue(new Callback<NationDto>() {
                    @Override
                    public void onResponse(Call<NationDto> call, Response<NationDto> response) {
                        if (response.code() == 200) {
                            // 로그인 성공 시 처리
                            // 예: 로그인 성공 메시지를 보여줌

                            showToast("추가 성공");
                            startActivity(new Intent(NewNationActivity.this,NationListActivity.class));
                        } else {

                            // 로그인 실패 시 처리
                            // 예: 사용자에게 실패 메시지를 보여줌
                            showToast("추가 실패");

                        }
                    }


                    @Override
                    public void onFailure(@NonNull Call<NationDto> call, @NonNull Throwable t) {
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