package com.example.phieldshield;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();
        TextView textView = findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);
        textView.setText(getIntent().getStringExtra("param"));
        if(textView.getText().equals("Viraj"))
        {
            textView2.setText("Desktop Developer");
        }
        else
        {
            textView2.setText("Developer");
        }
    }
}
