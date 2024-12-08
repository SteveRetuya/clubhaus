package com.example.clubhaus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clubhaus.user.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText emailET, passwordET;
    String email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button loginButton = findViewById(R.id.loginButton);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
            emailET = findViewById(R.id.emailInput);
            passwordET = findViewById(R.id.passwordInput);
            email = emailET.getText().toString().trim();
            password = passwordET.getText().toString().trim();

            DatabaseReference reference = database.getReference(email);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String realPassword = snapshot.child("password").getValue(String.class);
                        checkPassword(realPassword);
                    } else {
                        Toast.makeText(LoginActivity.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    protected void checkPassword(String realPassword) {
        if (password.equals(realPassword)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
        }
    }
}
