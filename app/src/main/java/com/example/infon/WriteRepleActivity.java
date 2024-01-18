package com.example.infon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class WriteRepleActivity extends AppCompatActivity {
    EditText write;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_reple);

        write = findViewById(R.id.write);
        save = findViewById(R.id.save);

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
        Intent intent =getIntent();
        RepleInterface rpif = retrofit.create(RepleInterface.class);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reple reple = new Reple();
                reple.setContents(write.getText().toString());
                Call<Reple> call=rpif.callPostReple(intent.getStringExtra("name"),reple);
                call.enqueue(new Callback<Reple>() {

                    @Override
                    public void onResponse(Call<Reple> call, Response<Reple> response) {
                        if (response.isSuccessful()) {
                            showToast("댓글입력 성공");
                            startActivity(new Intent(WriteRepleActivity.this, NationListActivity.class));
                        }
                        else {
                            showToast("입력실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<Reple> call, Throwable t) {
                        Log.e("NetworkError", "Error: " +t.getMessage());
                            showToast("입력실패");

                    }
                });
            }

        });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}