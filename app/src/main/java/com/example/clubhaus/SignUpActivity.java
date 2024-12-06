package com.example.clubhaus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    EditText inputEmail, inputUsername, inputBirthday, inputPassword;
    Button signUpButton;
    FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); // Confirm this layout exists

        mAuth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.emailInput);
        inputUsername = findViewById(R.id.usernameInput);
        inputBirthday = findViewById(R.id.birthdayInput);
        inputPassword = findViewById(R.id.passwordInput);
        signUpButton = findViewById(R.id.signUpButton_RegisterUser);

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);

            String email, username, birthday, password;

            email = String.valueOf(inputEmail).trim();
            username = String.valueOf(inputUsername).trim();
            birthday = String.valueOf(inputBirthday).trim();
            password = String.valueOf(inputPassword).trim();

            if(TextUtils.isEmpty(email)){
                Toast.makeText(SignUpActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(username)){
                Toast.makeText(SignUpActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(birthday)){
                Toast.makeText(SignUpActivity.this, "Enter birthday", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(SignUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });

            startActivity(intent);
        });
    }
}
