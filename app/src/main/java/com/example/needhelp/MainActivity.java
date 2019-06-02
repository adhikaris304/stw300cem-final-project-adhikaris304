package com.example.needhelp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practice.R;


public class MainActivity extends AppCompatActivity {
//    private static final String TAG = "LoginActivity";
//    private static final int REQUEST_SIGNUP = 0;
//
    private EditText emailText, passwordText;
    private Button loginButton;
    private TextView signupLink;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emailText=findViewById(R.id.input_email);
        passwordText=findViewById(R.id.input_password);
        loginButton=findViewById(R.id.btn_login);
        signupLink=findViewById(R.id.link_signup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });


        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });



//        _signupLink.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // Start the Signup activity
//                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
//                startActivityForResult(intent, REQUEST_SIGNUP);
//            }
//        });
//    }
//
//    public void login() {
//        Log.d(TAG, "Login");
//
//        if (!validate()) {
//            onLoginFailed();
//            return;
//        }
//
//        _loginButton.setEnabled(false);
//
//        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
//                R.style.ThemeOverlay_AppCompat_Dark);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();
//
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();
//
//
//
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//
//                        onLoginSuccess();
//
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SIGNUP) {
//            if (resultCode == RESULT_OK) {
//
//
//                this.finish();
//            }
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//
//        moveTaskToBack(true);
//    }
//
//    public void onLoginSuccess() {
//        _loginButton.setEnabled(true);
//        finish();
//    }
//
//    public void onLoginFailed() {
//        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
//
//        _loginButton.setEnabled(true);
//    }
//
//    public boolean validate() {
//        boolean valid = true;
//
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();
//
//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            _emailText.setError("enter a valid email address");
//            valid = false;
//        } else {
//            _emailText.setError(null);
//        }
//
//        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
//            _passwordText.setError("between 4 and 10 alphanumeric characters");
//            valid = false;
//        } else {
//            _passwordText.setError(null);
//        }
//
//        return valid;
    }
}