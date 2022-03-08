package com.example.foodorder;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




public class RegisterActivity extends AppCompatActivity {

    private EditText userEmail, userPassword, userPassword2, userName;
    private ProgressDialog progressDialog;
    private Button regBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userEmail = findViewById(R.id.register_email_input);
        userPassword = findViewById(R.id.register_password_input);
        userPassword2 = findViewById(R.id.register_password2_input);
        userName = findViewById(R.id.register_name_input);
        regBtn = findViewById(R.id.register_btn);
        progressDialog = new ProgressDialog(this);

//        signBtn = findViewById(R.id.sigBtn);
//        signBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EmailAndPasswordRegisterActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });


        mAuth = FirebaseAuth.getInstance();



                regBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String user_name = userName.getText().toString();
                        String email = userEmail.getText().toString();
                        String password = userPassword.getText().toString();
                        String password2  = userPassword2.getText().toString();
                        if(checkpassword(password,password2)){
                            signinuser(user_name,email,password);
                            Toast.makeText(RegisterActivity.this,"please wait",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
                private void signinuser(String name, String email, String password) {

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                sendemail();
                                if(!user.isEmailVerified()){
                                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }else{

                                String error = task.getException().toString();
                                Log.d("login",error);
                                Toast.makeText(RegisterActivity.this,error,Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }

                private void sendemail() {
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user != null){
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this,"check email for verification",Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }
                }


                private boolean checkpassword(String password, String password2) {
                    Log.d("login","in check password");
                    if(password.equals(password2))
                        return true;
                    else{
                        Log.d("login","in toast");
                        Toast.makeText(RegisterActivity.this,"password and confirm password has to match",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }