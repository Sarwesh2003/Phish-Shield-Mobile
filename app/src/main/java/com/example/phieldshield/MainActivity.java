package com.example.phieldshield;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{
    ImageView search_btn;
    EditText et;
    RadioGroup ct;
    String url;
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        slideshow(2131231151);
        ct = (RadioGroup) findViewById(R.id.ct);
        search_btn = (ImageView)findViewById(R.id.search_icon);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchClick();
            }

        });
        ct.setOnCheckedChangeListener((group, checkedId) -> {
            slideshow(checkedId);
        });
            }


    @SuppressLint("ResourceAsColor")
    private void createLayout(String result)
    {
        Animation downtoup;
        ImageView success = new ImageView(this);
        downtoup = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.downtoup_card);
        ConstraintLayout myRoot = (ConstraintLayout) findViewById(R.id.cl1);
        LinearLayout a = new LinearLayout(this);
        a.setOrientation(LinearLayout.HORIZONTAL);
        TextView t1 = new TextView(MainActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        a.setLayoutParams(layoutParams);
        if(result.equals("-1"))
        {
            success.setImageResource(R.drawable.fail);
            t1.setLayoutParams(layoutParams);
            t1.setGravity(Gravity.CENTER);
            t1.setTextColor(R.color.shadow);
            t1.setText("The Website is Phishing!!!!...");
            a.addView(t1);
        }
        else if(result.equals("0")) {
            success.setImageResource(R.drawable.success);
            a.addView(success);
            t1.setLayoutParams(layoutParams);
            t1.setGravity(Gravity.CENTER);
            t1.setTextColor(R.color.shadow);
            t1.setText("The Website is Safe to Visit!!!!...");
            a.addView(t1);
        }
        a.setBackgroundResource(R.drawable.result_layout);
        search_btn.setVisibility(View.INVISIBLE);
        et.setVisibility(View.INVISIBLE);
        myRoot.addView(a);
        a.setAnimation(downtoup);
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                () -> {
                    Animation fade;
                    fade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                    success.setAnimation(fade);
                    success.setVisibility(View.GONE);
                    a.setBackgroundResource(R.drawable.result_layout);
                },
                2500);
    }
    private void slideshow(int slideshow)
    {
        Animation slidein;
        slidein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_slide_in_left);
        ImageView slide = findViewById(R.id.slide);
        if(slideshow==2131231151)
        {
            slide.setImageResource(R.drawable.ad1);
        }
        else if(slideshow==2131231155)
        {
            slide.setImageResource(R.drawable.ad2);
        }
        else if(slideshow==2131231156)
        {
            slide.setImageResource(R.drawable.ad3);
        }
        slide.setAnimation(slidein);
    }
    private void SearchClick() {
        et = findViewById(R.id.editTextTextPersonName);
        if(et.getVisibility()==View.INVISIBLE) {
            Path path = new Path();
            path.arcTo(20f, 380f, 850f, 1000f, 0, 0, true);
            ObjectAnimator animator = ObjectAnimator.ofFloat(search_btn, View.X, View.Y, path);
            animator.setDuration(1000);
            animator.start();
            et.setVisibility(View.VISIBLE);
        }
        else
        {
            if(et.getText().length()==0){
                Path path = new Path();
                path.arcTo(20f, 330f, 480f, 1000f, 0, 0, true);
                ObjectAnimator animator = ObjectAnimator.ofFloat(search_btn, View.X, View.Y, path);
                animator.setDuration(1000);
                animator.start();
                et.setVisibility(View.INVISIBLE);
            }
            else {
                do {
                    Toast.makeText(getApplicationContext(),"Loading...",Toast.LENGTH_SHORT).show();
                } while(!urlResult());

            }
        }
    }
    public boolean urlResult()
    {
        url= et.getText().toString();
        OkHttpClient okhttpClient=new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();
        RequestBody formBody=new FormBody.Builder().add("URL",url).build();
        Request request=new Request.Builder().url("https://phishsheild.herokuapp.com/post").post(formBody).build();
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if(e.toString().contains("SocketTimeoutException")){
                    Toast.makeText(getApplicationContext(), "-1",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "0",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                runOnUiThread(() -> {
                    try {
                        String results;
                        results = response.body().string();
                        createLayout(results);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        });
        return true;
    }
}
