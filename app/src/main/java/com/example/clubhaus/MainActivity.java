package com.example.clubhaus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.clubhaus.admin.AddEventFragment;
import com.example.clubhaus.admin.AdminHomeFragment;
import com.example.clubhaus.admin.AnalyticsFragment;
import com.example.clubhaus.admin.EditEventFragment;
import com.example.clubhaus.user.ClubForumsFragment;
import com.example.clubhaus.user.ClubsFragment;
import com.example.clubhaus.user.HomeFragment;
import com.example.clubhaus.user.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private String userRole;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);

        // Fetch the user role (e.g., from SharedPreferences or passed intent)
        userRole = getUserRole();

        // Set the BottomNavigationView menu based on the user role
        setupBottomNavigationMenu();

        // Set the listener for BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.Home) {
                    loadFragment(new HomeFragment(), false);
                } else if (itemId == R.id.Search) {
                    loadFragment(new SearchFragment(), false);
                } else if (itemId == R.id.Clubs) {
                    loadFragment(new ClubsFragment(), false);
                } else if (itemId == R.id.Admin_Analytics) {
                    loadFragment(new AnalyticsFragment(), false);
                } else if (itemId == R.id.Admin_AddEvents) {
                    loadFragment(new AddEventFragment(), false);
                } else if (itemId == R.id.Admin_Home) {
                    loadFragment(new AdminHomeFragment(), false);
                } else {
                    loadFragment(new ProfileFragment(), false);
                }

                return true;
            }
        });

        if (userRole.equalsIgnoreCase("admin")) {
            loadFragment(new AdminHomeFragment(), true);
        } else {
            loadFragment(new HomeFragment(), true);
        }
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialize) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialize) {
            fragmentTransaction.add(R.id.frameLayout, fragment);
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }

        fragmentTransaction.commit();
    }

    private void setupBottomNavigationMenu() {
        if ("admin".equalsIgnoreCase(getUserRole())) {
            // Inflate admin-specific menu
            bottomNavigationView.inflateMenu(R.menu.admin_menu);

        } else {
            // Inflate user-specific menu
            bottomNavigationView.inflateMenu(R.menu.user_menu);
        }
    }

    private String getUserRole() {
         return getIntent().getStringExtra("role");
    }

    public void joinButtonClubs(View view) {
        loadFragment(new ClubForumsFragment(), false);
    }

    public void addEventButton(View view){
        loadFragment(new EditEventFragment(), false);
    }
}
