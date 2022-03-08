package com.example.foodorder;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    private EditText userMail,userPassword;
    private Button btnLogin, logToRegBtn;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logToRegBtn = findViewById(R.id.register_btn);
        logToRegBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });

        progressDialog = new ProgressDialog(this);
        userMail = findViewById(R.id.login_email_input);
        userPassword = findViewById(R.id.login_password_input);
        btnLogin = findViewById(R.id.login_btn);
        mAuth = FirebaseAuth.getInstance();



        btnLogin.setOnClickListener(view -> {
            String email = userMail.getText().toString();
            String password = userPassword.getText().toString();
            if(email.isEmpty() && password.isEmpty()){
                Toast.makeText(LoginActivity.this,"fill in the empty field",Toast.LENGTH_SHORT).show();
            }else{
                usersignin(email,password);
                progressDialog.setTitle("Login Account");
                progressDialog.setMessage("checking database...please wait");
                progressDialog.show();
            }

        });

        logToRegBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });



    }

    private void usersignin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                progressDialog.dismiss();
                Intent tomainintent = new Intent(LoginActivity.this,StallsActivity.class);
                startActivity(tomainintent);
                finish();
            }else{
                progressDialog.dismiss();
                String error = task.getException().toString();
                Toast.makeText(LoginActivity.this,error,Toast.LENGTH_LONG).show();
            }
        });
    }
}