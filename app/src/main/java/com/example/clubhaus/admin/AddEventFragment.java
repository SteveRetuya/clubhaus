package com.example.clubhaus.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

        for (int index = 0; index < 4; index++) {
            event = new Event("Car Meet " + index, "Camp John Hay", "Lorem Ipsum", 20);
            newReference = reference.child(event.getTitle());
            newReference.setValue(event);
        }

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String title, location, description;
                int attendees;
                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    title = eventSnapshot.getKey();
                    location = eventSnapshot.child("location").getValue(String.class);
                    description = eventSnapshot.child("description").getValue(String.class);
                    attendees = eventSnapshot.child("attendees").getValue(Integer.class);
                    Log.v("Tag", title);
                    Log.v("Tag", location);
                    Log.v("Tag", description);
                    Log.v("Tag", attendees+"");
                    eventList.add(new Event(title, location, description, attendees));
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

    private void addEvent() {
        // Create a new sample event
        Event newEvent = new Event("New Event", "New Location", "New Description", 0);
        eventList.add(newEvent);
        eventAdapter.notifyDataSetChanged();
    }
}
