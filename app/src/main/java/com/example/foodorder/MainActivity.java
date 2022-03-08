package com.example.foodorder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {
    private Button canteenLoginButton, studentLoginButton;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canteenLoginButton = (Button) findViewById(R.id.canteen_login);
        studentLoginButton = (Button) findViewById(R.id.main_login_btn);

        // ini
        mAuth = FirebaseAuth.getInstance();
        studentLoginButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        canteenLoginButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CanteenLogin.class);
            startActivity(intent);
        });
    }
}
