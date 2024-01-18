package com.example.infon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NationDetailsActivity extends AppCompatActivity {
    Button readReples,revision;
    Gson gson = new Gson();
    TextView nationName,nationCapital,nationCurrency,nationPopulation,nationContin,nationContents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nation_details);
        nationName=findViewById(R.id.nationName);
        nationCapital=findViewById(R.id.nationCapital);
        nationCurrency=findViewById(R.id.nationCurrency);
        nationPopulation=findViewById(R.id.nationPopulation);
        nationContin=findViewById(R.id.nationContin);
        nationContents=findViewById(R.id.nationContents);

        readReples=findViewById(R.id.readReples);
        revision=findViewById(R.id.revision);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.39.10.90:8080/")  // 실제 API의 base URL로 수정
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        revision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NationDetailsActivity.this, RevisionActivity.class);
                i.putExtra("name",nationName.getText());
                startActivity(i);
            }
        });
        Intent intent = getIntent();
        NationDetailsInterface nationDetailsInterface =retrofit.create(NationDetailsInterface.class);
        Call<String> call = nationDetailsInterface.nationDetails(intent.getStringExtra("name"));
        call.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response.body());
                        nationName.setText(jsonObject.getString("name"));
                        nationCapital.setText(jsonObject.getString("capital"));
                        nationCurrency.setText(jsonObject.getString("currency"));
                        nationPopulation.setText(jsonObject.getString("population"));
                        nationContin.setText(jsonObject.getString("contin"));
                        nationContents.setText(jsonObject.getString("contents"));
                    }
                    catch (JSONException e)
                    {
                        Log.e("NetworkError", "Error: " + e.getMessage());
                        e.printStackTrace();

                    }
                }
            }



            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t)
            {
                Log.e("에러 : ", t.getMessage());
            }
        });

        readReples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NationDetailsActivity.this,RepleListActivity.class);
                i.putExtra("name",nationName.getText());
                startActivity(i);
            }
        });
    }
}



