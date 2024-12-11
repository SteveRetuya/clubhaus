package com.example.clubhaus.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.clubhaus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsFragment extends Fragment {

    private TextView eventsCount, location1, location2, location3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_analytics, container, false);

        // Initialize views
        eventsCount = view.findViewById(R.id.events_count);
        location1 = view.findViewById(R.id.location_1);
        location2 = view.findViewById(R.id.location_2);
        location3 = view.findViewById(R.id.location_3);

        // Fetch data from Firebase
        fetchAnalyticsData();

        return view;
    }

    private void fetchAnalyticsData() {
        // Get a reference to the Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference eventsRef = database.getReference("events");

        // Add a ValueEventListener to read data
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalEvents = 0;
                Map<String, Integer> locationCounts = new HashMap<>();

                // Iterate through each event
                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    totalEvents++; // Count the total number of events

                    // Get the location and attendees
                    String location = eventSnapshot.child("location").getValue(String.class);
                    Integer attendees = eventSnapshot.child("attendees").getValue(Integer.class);

                    // Update location counts based on attendees
                    if (location != null && attendees != null) {
                        locationCounts.put(location, locationCounts.getOrDefault(location, 0) + attendees);
                    }
                }

                // Update total events in the UI
                eventsCount.setText(String.valueOf(totalEvents));

                // Sort locations by attendees in descending order
                List<Map.Entry<String, Integer>> sortedLocations = new ArrayList<>(locationCounts.entrySet());
                Collections.sort(sortedLocations, (entry1, entry2) -> entry2.getValue() - entry1.getValue());

                // Update top 3 locations in the UI
                if (sortedLocations.size() > 0) {
                    location1.setText(sortedLocations.get(0).getKey());
                }
                if (sortedLocations.size() > 1) {
                    location2.setText(sortedLocations.get(1).getKey());
                }
                if (sortedLocations.size() > 2) {
                    location3.setText(sortedLocations.get(2).getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to fetch data", error.toException());
            }
        });
    }
}
