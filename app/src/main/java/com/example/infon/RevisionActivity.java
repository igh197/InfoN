package com.example.infon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class RevisionActivity extends AppCompatActivity {
    EditText editName,editCapital,editCurrency,editPopulation,editContin,editContents;
    Button save;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision);
        editName=findViewById(R.id.editName);
        editCapital=findViewById(R.id.editCapital);
        editCurrency=findViewById(R.id.editCurrency);
        editPopulation=findViewById(R.id.editPopulation);
        editContin=findViewById(R.id.editContin);
        editContents=findViewById(R.id.editContents);
        save=findViewById(R.id.save);
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
                        editName.setText(jsonObject.getString("name"));
                        editCapital.setText(jsonObject.getString("capital"));
                        editCurrency.setText(jsonObject.getString("currency"));
                        editPopulation.setText(jsonObject.getString("population"));
                        editContin.setText(jsonObject.getString("contin"));
                        editContents.setText(jsonObject.getString("contents"));



                    }
                    catch (JSONException e)
                    {
                        Log.e("Error", "Error: " + e.getMessage());
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
        Retrofit rf = new Retrofit.Builder()
                .baseUrl("http://3.39.10.90:8080/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NationDto nationDto = new NationDto(editName,editCapital,editCurrency,editPopulation,editContin,editContents);
                Intent i = getIntent();
                NationDetailsInterface ndif=rf.create(NationDetailsInterface.class);
                Call<NationDto> call = ndif.nationRevision(i.getStringExtra("name"),nationDto);
                call.enqueue(new Callback<NationDto>() {
                    @Override
                    public void onResponse(Call<NationDto> call, Response<NationDto> response) {
                        if (response.code()==200){
                            Intent i= new Intent(RevisionActivity.this,NationListActivity.class);
                            startActivity(i);
                            showToast("수정완료");
                        }
                    }

                    @Override
                    public void onFailure(Call<NationDto> call, Throwable t) {
                        showToast("수정실패");
                    }
                });
            }

        });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}