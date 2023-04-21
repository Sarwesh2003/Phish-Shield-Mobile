package com.example.phieldshield;

import android.animation.ArgbEvaluator;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class Sample extends AppCompatActivity {

    ViewPager viewPager;
    Adpter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiyabout);
        getSupportActionBar().hide();
        models = new ArrayList<>();
        models.add(new Model(R.drawable.brochure, "Atharva", ""));
        models.add(new Model(R.drawable.sticker, "Sarwesh", ""));
        models.add(new Model(R.drawable.poster, "Viraj", ""));
        models.add(new Model(R.drawable.namecard, "Sanket", ""));
        adapter = new Adpter(models,this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(110, 0, 130, 100);

    }
}
