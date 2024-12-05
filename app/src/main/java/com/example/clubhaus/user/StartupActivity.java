package com.operatingsystem.clubhausos;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        // Redirect to LoginActivity after a delay (2 seconds)
        new android.os.Handler().postDelayed(() -> {
            Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}
