package com.example.phieldshield;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {
    CardView s,d,h,c;
    ImageView home,search,about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        getSupportActionBar().hide();
        cardClick();
        BackBtn();
        Toolbar();
    }

    protected void Toolbar()
    {
        home = findViewById(R.id.homebtn);
        search = findViewById(R.id.detect_phish);
        about = findViewById(R.id.contact_us);
        home.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            HomeActivity.this.finish();
        });
        search.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DetechPhishing.class));
            HomeActivity.this.finish();
        });
        about.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PhishingInfo.class));
            HomeActivity.this.finish();
        });
    }
    protected void cardClick()
    {
        d = findViewById(R.id.detect_p);
        h = findViewById(R.id.home_screen);
        c = findViewById(R.id.contact);
        s = findViewById(R.id.showinfo);
        d.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DetechPhishing.class));
            HomeActivity.this.finish();
        });
        h.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AboutUs.class));
            HomeActivity.this.finish();
        });
        c.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Contactus.class));
            HomeActivity.this.finish();
        });
        s.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),PhishingInfo.class));
            HomeActivity.this.finish();
        });
    }
    protected void BackBtn()
    {
        ImageView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            HomeActivity.this.finish();
        });
    }


}