package com.example.phieldshield;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class StartActivity  extends AppCompatActivity{
    int check,check1;
    Button start,dismiss;
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitystart);
        getSupportActionBar().hide();
        start = findViewById(R.id.start);
        dismiss = findViewById(R.id.dismiss);
        start.setOnClickListener(v -> {
            Permissioncheck();
        });
        dismiss.setOnClickListener(v -> new AlertDialog.Builder(StartActivity.this)
                .setTitle("Stop application?")
                .setMessage("Stop this application and exit? You'll need to re-launch the application to use it again.")
                                .setPositiveButton("Don't Stop", (dialog, which) -> {

                                })
                .setNegativeButton("Stop and exit", (dialog, which) -> {
                    StartActivity.this.finish();
                })
                .setCancelable(false)
                .show());
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Permissioncheck()
    {
        boolean value = isReadStoragePermissionGranted();
        if(value)
        {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            StartActivity.this.finish();
        }
        else
        {
            new AlertDialog.Builder(StartActivity.this)
                    .setTitle("PERMISSION?")
                    .setMessage("Permission was denied.Some functionality of app may not work")
                    .setPositiveButton("Grant", (dialog, which) -> {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                    })
                    .setNegativeButton("Close", (dialog, which) -> {
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class ));
                    })
                    .setCancelable(false)
                    .show();
        }
    }
    public  boolean isReadStoragePermissionGranted() {
        boolean b=true;
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted1");
                b= true;
            } else if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                Toast.makeText(getApplicationContext(),"Read DENIED",Toast.LENGTH_SHORT).show();
                b = false;
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                isReadStoragePermissionGranted();
            }
        }
        return b;
    }
}
