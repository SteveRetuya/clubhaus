package com.example.clubhaus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clubhaus.user.ForumsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET, passwordET;
    private String username, password;
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
            usernameET = findViewById(R.id.usernameEdit);
            passwordET = findViewById(R.id.passwordInput);
            username = usernameET.getText().toString().trim();
            password = passwordET.getText().toString().trim();

            DatabaseReference reference = database.getReference("user");
            reference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String realPassword = snapshot.child("password").getValue(String.class);
                        checkPassword(realPassword);
                    } else {
                        Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
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
            String role;
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            if (username.equalsIgnoreCase("admin")) {
                role = "admin";
            } else {
                role = "user";
            }

            intent.putExtra("role", role);
            intent.putExtra("displayName", username);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
        }
    }
}
