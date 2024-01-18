package com.example.infon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonArray;


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

public class RepleListActivity extends AppCompatActivity {
    RepleAdapter adapter;
    Button writeReple;

    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Gson gson = new Gson();
    ArrayList<Reple> repleArrayList = new ArrayList<>();

    int page=1,limit=10;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reple_list);


        nestedScrollView = findViewById(R.id.scroll_view);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);

        adapter = new RepleAdapter(RepleListActivity.this, repleArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        getData(name,page,limit);
        writeReple = findViewById(R.id.writeReple);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
        {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
            {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
                {
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getData(intent.getStringExtra("name"),page, limit);
                }
            }
        });




        writeReple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RepleListActivity.this,WriteRepleActivity.class);
                i.putExtra("name",intent.getStringExtra("name"));
                startActivity(i);
            }
        });
    }

    private void getData(String name,int page,int limit)
    {
        // 레트로핏 초기화
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.39.10.90:8080/")   // 실제 API의 base URL로 수정
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RepleInterface repleInterface = retrofit.create(RepleInterface.class);
        Call<List<Reple>> call = repleInterface.callReple(name);
        call.enqueue(new Callback<List<Reple>>() {
            @Override
            public void onResponse(@NonNull Call<List<Reple>> call, @NonNull Response<List<Reple>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    List<Reple> repleList = response.body();
                    repleArrayList.addAll(repleList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Reple>> call, @NonNull Throwable t) {
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
                Reple reple=new Reple();
                reple.setContents(jsonObject.getString("contents"));
                repleArrayList.add(reple);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
        adapter = new RepleAdapter(RepleListActivity.this, repleArrayList);
        recyclerView.setAdapter(adapter);

    }


}