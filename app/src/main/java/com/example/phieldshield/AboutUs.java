package com.example.phieldshield;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class AboutUs extends AppCompatActivity {
    ViewPager viewPager;
    Adpter adapter;
    List<Model> models;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiyabout);
        getSupportActionBar().hide();
        BackBtn();
        Toolbar();
        models = new ArrayList<>();
        models.add(new Model(R.drawable.atharva, "Atharva Raut", "Contribution:\nUI/UX Designer\nPrototyping\nWireframing"));
        models.add(new Model(R.drawable.sarwesh, "Sarwesh Khairnar", "Team Lead\nMachine Learning Developer\nBackend Development\nWeb Scraping"));
        models.add(new Model(R.drawable.viraj, "Viraj Sonanwane", "Desktop Development\nResearch Work\nSoftware Engineering"));
        models.add(new Model(R.drawable.sanket, "Sanket Shirsath", "Android Development\nResearch Work\nSoftware Engineering"));
        adapter = new Adpter(models,this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(110, 0, 170, 100);
        viewPager.setPageMargin(20);
    }
    protected void BackBtn()
    {
        ImageView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            AboutUs.this.finish();
        });
    }
    protected void Toolbar()
    {
        ImageView home,search,about;
        home = findViewById(R.id.homebtn);
        search = findViewById(R.id.detect_phish);
        about = findViewById(R.id.contact_us);
        home.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            AboutUs.this.finish();
        });
        search.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DetechPhishing.class));
            AboutUs.this.finish();
        });
        about.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AboutUs.class));
            AboutUs.this.finish();
        });
    }

}