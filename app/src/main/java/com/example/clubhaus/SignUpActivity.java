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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

            email = inputEmail.getText().toString().trim();
            username = inputUsername.getText().toString().trim();
            birthday = inputBirthday.getText().toString().trim();
            password = inputPassword.getText().toString().trim();

            if(email.isEmpty() || email.equalsIgnoreCase("admin")){
                Toast.makeText(SignUpActivity.this, "Enter appropriate email", Toast.LENGTH_SHORT).show();
                return;
            }

            if(username.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                return;
            }

            if(birthday.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Enter birthday", Toast.LENGTH_SHORT).show();
                return;
            }

            if(password.isEmpty()){
                Toast.makeText(SignUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            /*
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
             */

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference reference = database.getReference(email);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(SignUpActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        DatabaseReference newReference = database.getReference().child(email);
                        newReference.setValue(new User(username, birthday, password));
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
