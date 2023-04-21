package com.example.phieldshield;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Contactus extends AppCompatActivity {
    EditText name,email,subject,msg;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        getSupportActionBar().hide();
        BackBtn();
        Toolbar();
        sentmail();
    }
    protected void BackBtn()
    {
        ImageView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            Contactus.this.finish();
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
            Contactus.this.finish();
        });
        search.setOnClickListener(v -> {

            startActivity(new Intent(getApplicationContext(), DetechPhishing.class));
            Contactus.this.finish();
        });
        about.setOnClickListener(v -> {

            startActivity(new Intent(getApplicationContext(), AboutUs.class));
            Contactus.this.finish();
        });
    }
    protected void sentmail()
    {
        Button submit1;
        name = findViewById(R.id.Name);
        email = findViewById(R.id.email_id);
        subject = findViewById(R.id.subject);
        msg = findViewById(R.id.s_msg);
        submit1 = findViewById(R.id.submit);
        submit1.setOnClickListener(v -> {
            String[] emails=email.getText().toString().split(",");
            String sub=subject.getText().toString();
            String Message=msg.getText().toString();
            Intent SendEmail=new Intent(Intent.ACTION_SEND);
            SendEmail.setType("message/rfc822");
            SendEmail.putExtra(Intent.EXTRA_EMAIL,emails);
            SendEmail.putExtra(Intent.EXTRA_SUBJECT,sub);
            SendEmail.putExtra(Intent.EXTRA_TEXT,Message);
            startActivity(Intent.createChooser(SendEmail,"Select App to Continue"));
            Toast.makeText(getApplicationContext(),"Sending E-Mail",Toast.LENGTH_SHORT).show();
        });

    }
    protected  void default_value()
    {
        name.setText("");
        email.setText("");
        subject.setText("");
        msg.setText("");
    }
}