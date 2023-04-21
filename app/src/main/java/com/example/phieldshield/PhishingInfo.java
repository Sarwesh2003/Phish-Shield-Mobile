package com.example.phieldshield;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class PhishingInfo extends AppCompatActivity {
    ImageView home,search,about;
    int currentPage=0;
    private final int NUM_PAGES=6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phishing_info);
        BackBtn();
        Toolbar();
        getSupportActionBar().hide();
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPage);
        ImageAdapter adapterView = new ImageAdapter(this);
        mViewPager.setAdapter(adapterView);
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPage == NUM_PAGES) {
                            currentPage = 0;
                        }
                        mViewPager.setCurrentItem(currentPage++, true);
                    }
                });
            }
        }, 500, 3000);
    }
    protected void Toolbar()
    {
        home = findViewById(R.id.homebtn);
        search = findViewById(R.id.detect_phish);
        about = findViewById(R.id.contact_us);
        home.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            PhishingInfo.this.finish();
        });
        search.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DetechPhishing.class));
            PhishingInfo.this.finish();
        });
        about.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PhishingInfo.class));
            PhishingInfo.this.finish();
        });
    }
    protected void BackBtn()
    {
        ImageView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            PhishingInfo.this.finish();
        });
    }
}