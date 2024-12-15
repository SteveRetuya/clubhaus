package com.example.clubhaus.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubhaus.MainActivity;
import com.example.clubhaus.R;
import com.example.clubhaus.SignUpActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddEventFragment extends Fragment {
    private List<Event> eventList;
    private EventAdapter eventAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize event list and adapter
        eventList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("events");
        DatabaseReference newReference;
        Event event;

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String title, location, description, interest, url;
                List<String> date, time;
                int attendees;

                GenericTypeIndicator<List<String>> listType = new GenericTypeIndicator<List<String>>() {};

                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    title = eventSnapshot.getKey();
                    location = eventSnapshot.child("location").getValue(String.class);
                    description = eventSnapshot.child("description").getValue(String.class);
                    attendees = eventSnapshot.child("attendees").getValue(Integer.class);
                    interest = eventSnapshot.child("interests").getValue(String.class);
                    date = eventSnapshot.child("date_List").getValue(listType);
                    time = eventSnapshot.child("time_List").getValue(listType);
                    url = eventSnapshot.child("imageLink").getValue(String.class);
                    eventList.add(new Event(title, location, description, attendees, interest, date, time, url));
                    eventAdapter = new EventAdapter(eventList);
                    recyclerView.setAdapter(eventAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Set up Add Event button
        view.findViewById(R.id.addEventButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).addEventButton(v);
            }
        });

        return view;
    }
}