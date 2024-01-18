package com.example.infon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NationListActivity extends AppCompatActivity {

    Button newNation;
    Gson gson = new Gson();
    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    ArrayList<NationDto> itemArrayList = new ArrayList<>();
    MyAdapter adapter;

    // 1페이지에 10개씩 데이터를 불러온다
    int page = 1, limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nation_list);


        nestedScrollView = findViewById(R.id.scroll_view);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);

        adapter = new MyAdapter(NationListActivity.this, itemArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        getData(page, limit);
        newNation = findViewById(R.id.newNation);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
        {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
            {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
                {
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page, limit);
                }
            }
        });
        newNation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NationListActivity.this,NewNationActivity.class);
                startActivity(i);
            }
        });
    }

    private void getData(int page, int limit)
    {
        // 레트로핏 초기화
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


        NationListInterface nationListInterface = retrofit.create(NationListInterface.class);
        Call<String> call = nationListInterface.string_call(page, limit);
        call.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    progressBar.setVisibility(View.GONE);
                    try
                    {
                        JSONArray jsonObject = new JSONArray(response.body());
                        parseResult(jsonObject);
                    }
                    catch (JSONException e)
                    {
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
    }

    private void parseResult(JSONArray jsonArray) throws JSONException {

        // 데이터 배열 파싱
        for (int i = 0; i < jsonArray.length(); i++)
        {
            try
            {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                NationDto nationDto = new NationDto();
                nationDto.setName(jsonObject.getString("name"));
                nationDto.setCapital(jsonObject.getString("capital"));
                itemArrayList.add(nationDto);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            adapter = new MyAdapter(NationListActivity.this, itemArrayList);
            recyclerView.setAdapter(adapter);
        }

    }

}