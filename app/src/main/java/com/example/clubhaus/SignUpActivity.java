package com.example.clubhaus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
    EditText inputEmail, inputUsername, inputPassword;
    Button signUpButton;
    FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); // Confirm this layout exists

        // mAuth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.usernameEdit);
        inputUsername = findViewById(R.id.usernameInput);
        inputPassword = findViewById(R.id.passwordInput);
        signUpButton = findViewById(R.id.signUpButton_RegisterUser);

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);

            String email, username, birthday, password;

            email = inputEmail.getText().toString().trim();
            username = inputUsername.getText().toString().trim();
            password = inputPassword.getText().toString().trim();

            if(email.isEmpty() || email.equalsIgnoreCase("admin")){
                Toast.makeText(SignUpActivity.this, "Enter appropriate email", Toast.LENGTH_SHORT).show();
                return;
            }

            if(username.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                return;
            }

            if(password.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference reference = database.getReference("user");
            reference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(SignUpActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference reference = database.getReference("user");
                        DatabaseReference newReference = reference.child(username);
                        newReference.setValue(new User(username, password, email, ""));
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    startActivity(intent);
                }
            });
        });
    }
}
