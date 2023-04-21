package com.example.phieldshield;

import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class About extends AppCompatActivity {

    ViewPager viewPager;
    Adpter adapter;
    List<Model> models;
    RelativeLayout rl;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_main);
        rl = findViewById(R.id.about_r);
        getSupportActionBar().hide();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    AlertDialog dialog;
                    AlertDialog.Builder builder= new AlertDialog.Builder(About.this);
                    builder.setTitle("Developers Information");
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
                    success.setImageResource(R.drawable.atharva);
                    title.setText("Developers Information");
                    msg.setText("");
                    Button ok=customLayout1.findViewById(R.id.button);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
