package com.example.phieldshield;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetechPhishing extends AppCompatActivity {
    int currentPage=0;
    private final int NUM_PAGES=3;
    AlertDialog dialog;
    Button result;
    //String results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);
        getSupportActionBar().hide();
        BackBtn();
        Toolbar();
        Result();
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageDetect);
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
    protected void BackBtn()
    {
        ImageView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            DetechPhishing.this.finish();
        });
    }
    protected void Result()
    {
        result = findViewById(R.id.detect);
        result.setOnClickListener(v -> {
            urlResult();
            result.setEnabled(false);
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
            DetechPhishing.this.finish();
        });
        search.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DetechPhishing.class));
            DetechPhishing.this.finish();
        });
        about.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AboutUs.class));
            DetechPhishing.this.finish();
        });
    }
    public void urlResult()
    {
        String url= ((EditText) findViewById(R.id.urltext)).getText().toString();
        OkHttpClient okhttpClient=new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();
        RequestBody formBody=new FormBody.Builder().add("URL",url).build();
        Request request=new Request.Builder().url("https://phishsheild.herokuapp.com/post").post(formBody).build();
        okhttpClient.newCall(request).enqueue(new Callback() {
            public String results="";
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if(e.toString().contains("SocketTimeoutException")){
                    //Toast.makeText(getApplicationContext(), "-1",Toast.LENGTH_SHORT).show();
                    //String results;
                    results ="-1";
                    AlertDialog.Builder builder= new AlertDialog.Builder(DetechPhishing.this);
                    builder.setTitle("Results");
                    final View customLayout1
                            = getLayoutInflater()
                            .inflate(R.layout.customdialogdetect,null);
                    builder.setView(customLayout1);
                    builder.setCancelable(false);
                    dialog=builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogdrawable);
                    ImageView success = customLayout1.findViewById(R.id.result_img);
                    TextView title = customLayout1.findViewById(R.id.main_text);
                    TextView msg = customLayout1.findViewById(R.id.mes_text);
                    TextView note=customLayout1.findViewById(R.id.info_txt);
                    success.setImageResource(R.drawable.fail);
                    Button ok=customLayout1.findViewById(R.id.button);
                    Animation downtoup = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                    success.startAnimation(downtoup);
                    title.setText("Result: Phishing");
                    msg.setText("According to our System:\nThe Website is not safe to visit");
                    note.setText("Please Note:\nIf the result is website is not recognized, then it means that website is not famous but safe to visit.");
                    dialog.show();
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            result.setEnabled(true);
                        }
                    });
                    dialog.show();
                }
                else{
                    results ="0";
                    AlertDialog.Builder builder= new AlertDialog.Builder(DetechPhishing.this);
                    builder.setTitle("Results");
                    final View customLayout1
                            = getLayoutInflater()
                            .inflate(R.layout.customdialogdetect,null);
                    builder.setView(customLayout1);
                    builder.setCancelable(false);
                    dialog=builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogdrawable);
                    ImageView success = customLayout1.findViewById(R.id.result_img);
                    TextView title = customLayout1.findViewById(R.id.main_text);
                    TextView msg = customLayout1.findViewById(R.id.mes_text);
                    Button ok=customLayout1.findViewById(R.id.button);
                    TextView note=customLayout1.findViewById(R.id.info_txt);
                    success.setImageResource(R.drawable.success);
                    Animation downtoup = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                    success.startAnimation(downtoup);
                    title.setText("Result: Not Phishing");
                    msg.setText("According to our System:\nThe Website is Safe to Visit");
                    note.setText("Please Note:\nIf the result is website is not recognized, then it means that website is not famous but safe to visit.");
                    dialog.show();
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            result.setEnabled(true);
                        }
                    });
                }
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                runOnUiThread(() -> {
                    try {
                        results = response.body().string();
                        AlertDialog.Builder builder= new AlertDialog.Builder(DetechPhishing.this);
                        builder.setTitle("Results");
                        final View customLayout1
                                = getLayoutInflater()
                                .inflate(R.layout.customdialogdetect,null);
                        builder.setView(customLayout1);
                        builder.setCancelable(false);
                        dialog=builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogdrawable);
                        ImageView success = customLayout1.findViewById(R.id.result_img);
                        TextView title = customLayout1.findViewById(R.id.main_text);
                        TextView msg = customLayout1.findViewById(R.id.mes_text);
                        Button ok=customLayout1.findViewById(R.id.button);
                        TextView note=customLayout1.findViewById(R.id.info_txt);
                        note.setText("Please Note:\nIf the result is website is not recognized, then it means that website is not famous but safe to visit.");
                        if(results.equals("-1"))
                        {
                            success.setImageResource(R.drawable.fail);
                            title.setText("Result: Phishing");
                            msg.setText("According to our System:\nThe Website is not safe to visit");
                            Animation downtoup = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                            success.startAnimation(downtoup);
                            dialog.show();
                        }
                        else if(results.equals("0")) {
                            success.setImageResource(R.drawable.success);
                            Animation downtoup = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                            success.startAnimation(downtoup);
                            title.setText("Result: Safe");
                            msg.setText("According to our System:\nThe Website is Safe to Visit");
                            dialog.show();
                        }
                        else if(results.equals("1")){
                            success.setImageResource(R.drawable.success);
                            Animation downtoup = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                            success.startAnimation(downtoup);
                            title.setText("Result: Not Recognized");
                            msg.setText("It looks like this is not famous website.\nSystem Says: Its Safe");
                            dialog.show();
                        }
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                result.setEnabled(true);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                        results ="-1";
                        AlertDialog.Builder builder= new AlertDialog.Builder(DetechPhishing.this);
                        builder.setTitle("Results");
                        final View customLayout1
                                = getLayoutInflater()
                                .inflate(R.layout.customdialogdetect,null);
                        builder.setView(customLayout1);
                        builder.setCancelable(false);
                        dialog=builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogdrawable);
                        ImageView success = customLayout1.findViewById(R.id.result_img);
                        TextView title = customLayout1.findViewById(R.id.main_text);
                        TextView msg = customLayout1.findViewById(R.id.mes_text);
                        TextView note=customLayout1.findViewById(R.id.info_txt);
                        success.setImageResource(R.drawable.fail);
                        Animation downtoup = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                        success.startAnimation(downtoup);
                        Button ok=customLayout1.findViewById(R.id.button);
                        title.setText("Result: Phishing");
                        note.setText("Please Note:\nIf the result is website is not recognized, then it means that website is not famous but safe to visit.");
                        msg.setText("The Website your visiting is phishing and it is not safe to visit");
                        dialog.show();
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                result.setEnabled(true);
                            }
                        });
                    }
                });
            }
        });
    }
}