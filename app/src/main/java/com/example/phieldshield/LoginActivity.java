package com.example.phieldshield;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    TextView tv,tv_sign_up,message;
    String error;
    EditText editEmail, editPassword, editName;
    Button  btnRegister;
    ImageView btnSignIn;
    String URL= "https://finalcpp.000webhostapp.com/test_andriod/index.php";

    JSONParser jsonParser=new JSONParser();
    CheckBox rem_me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        getSupportActionBar().hide();
        BackBtn();

        tv = (TextView) findViewById(R.id.toptext) ;
        tv_sign_up = (TextView) findViewById(R.id.tx_signup);
        message = (TextView) findViewById(R.id.msg);

        editName=(EditText)findViewById(R.id.editName);
        editPassword=(EditText)findViewById(R.id.editPassword);
        editEmail = (EditText) findViewById(R.id.editEmail);

        btnSignIn=(ImageView)findViewById(R.id.btnSignIn);
        btnRegister=(Button)findViewById(R.id.btnSignUp);

        rem_me = (CheckBox) findViewById(R.id.rem_me);
        rem_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    editPassword.setInputType(129);
                } else {
                    editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        btnSignIn.setOnClickListener(view -> {
            if(editEmail.getVisibility()==View.GONE) {
                if (editName.getText().length()>0 && editPassword.getText().length()>0) {
                    AttemptLogin attemptLogin = new AttemptLogin();
                    attemptLogin.execute(editName.getText().toString(), editPassword.getText().toString(), editEmail.getText().toString());
                } else if (editName.getText().length()==0) {
                    error_msg("Username is Empty",getResources().getColor(R.color.red));
                } else if (editPassword.getText().length()==0) {
                    error_msg("Password is Empty",getResources().getColor(R.color.red));
                }
            }
            else {
                if (editName.getText().length()>0 && editPassword.getText().length()>0 && editEmail.getText().length()>0) {

                    btnSignIn.setEnabled(false);
                    AttemptLogin attemptLogin = new AttemptLogin();
                    attemptLogin.execute(editName.getText().toString(), editPassword.getText().toString(), editEmail.getText().toString());

                } else if (editName.getText().length()==0) {
                    error_msg("Username is Empty",getResources().getColor(R.color.red));
                } else if (editPassword.getText().length()==0) {
                    error_msg("Password is Empty",getResources().getColor(R.color.red));
                } else if (editEmail.getText().length()==0) {
                    error_msg("Email is Empty",getResources().getColor(R.color.red));
                }
            }

        });

        btnRegister.setOnClickListener(v -> {
            if(editEmail.getVisibility()==View.GONE) {
                editEmail.setVisibility(View.VISIBLE);
                tv_sign_up.setText("Have an Account");
                tv.setText("REGISTER");
                btnRegister.setText("LOGIN IN");
                rem_me.setVisibility(View.GONE);
            }
            else
            {
                rem_me.setVisibility(View.VISIBLE);
                editEmail.setVisibility(View.GONE);
                tv_sign_up.setText("Don't have an Account");
                tv.setText("LOGIN");
                btnRegister.setText("SIGN UP");
            }
            default_value();
        });

    }
    protected void BackBtn()
    {
        ImageView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            LoginActivity.this.finish();
        });
    }
    public void default_value()
    {
        editEmail.setText("");
        editName.setText("");
        editPassword.setText("");
        rem_me.setChecked(false);
    }

    public void error_msg(String error, int color)
    {
        message.setVisibility(View.VISIBLE);
        message.setTextColor(color);
        message.setText(error);
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                () -> {
                    message.setVisibility(View.GONE);
                    message.setText("");
                },
                5000);;
    }

    @SuppressLint("StaticFieldLeak")
    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        AlertDialog dialog;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("Authorizing");
            final View customLayout
                    = getLayoutInflater()
                    .inflate(R.layout.customdialog,null);
            builder.setView(customLayout);
            builder.setCancelable(false);
            dialog=builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogdrawable);
            dialog.show();

        }

        @Override

        protected JSONObject doInBackground(String... args) {



            String email = args[2];
            String password = args[1];
            String name= args[0];

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("password", password));
            if(email.length()>0)
                params.add(new BasicNameValuePair("email",email));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                if (result != null) {
                    if(result.getString("success").equals("1"))
                    {
                        if(editEmail.getVisibility()!=View.GONE)
                        {
                            btnRegister.performClick();
                            default_value();
                            error_msg(result.getString("message"),getResources().getColor(R.color.teal_700));
                        }
                        else
                        {
                            default_value();
                            error_msg(result.getString("message"),getResources().getColor(R.color.teal_700));
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            LoginActivity.this.finish();
                        }
                    }
                    else if(result.getString("success").equals("0"))
                    {

                        error_msg(result.getString("message"),getResources().getColor(R.color.red));
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        dialog.dismiss();

        }

    }
}