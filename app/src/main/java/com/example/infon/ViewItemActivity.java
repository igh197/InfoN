package com.example.infon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewItemActivity extends AppCompatActivity {
    TextView nationName;
    Button details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        details=findViewById(R.id.details);
        nationName=findViewById(R.id.nationName);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ViewItemActivity.this,NationDetailsActivity.class);
                startActivity(i);
            }
        });
    }
}

