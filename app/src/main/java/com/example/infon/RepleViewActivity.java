package com.example.infon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RepleViewActivity extends AppCompatActivity {
    TextView reple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reple_view);
        reple= findViewById(R.id.reple);
    }
}