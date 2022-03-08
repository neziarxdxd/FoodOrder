package com.example.foodorder;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CanteenRegisterActivity extends AppCompatActivity {
    private Button CreateAccountButton;
    private EditText InputName, InputCanteen, InputPassword;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen_register);
        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_name_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputCanteen = (EditText) findViewById(R.id.register_canteen_input);
        loadingBar = new ProgressDialog(this);
        CreateAccountButton.setOnClickListener(view -> CreateAccount());
    }
    private void CreateAccount(){
        String name = InputName.getText().toString();
        String canteen = InputCanteen.getText().toString();
        String password = InputPassword.getText().toString();
        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(canteen))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateCanteenOwners(name, canteen, password);
        }

    }

    private void ValidateCanteenOwners(final String name, final String canteen,final String password) {
        final DatabaseReference CanteenRef;
        CanteenRef = FirebaseDatabase.getInstance().getReference();
        CanteenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Canteen").child(canteen).exists())){
                    HashMap<String, Object> canteendataMap = new HashMap<>();
                    canteendataMap.put("canteen", canteen);
                    canteendataMap.put("password", password);
                    canteendataMap.put("name", name);
                    CanteenRef.child("Canteen").child(canteen).updateChildren(canteendataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(CanteenRegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(CanteenRegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(CanteenRegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }
                else {
                    Toast.makeText(CanteenRegisterActivity.this, "This " + canteen + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(CanteenRegisterActivity.this, "Please try again using another email.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CanteenRegisterActivity.this, com.example.foodorder.MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
