package com.example.clubhaus.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubhaus.MainActivity;
import com.example.clubhaus.R;
import com.example.clubhaus.admin.Event;
import com.example.clubhaus.admin.EventAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class ClubForumsFragment extends Fragment {
    List<Event> eventList;
    ForumsAdapter forumsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club_forums, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_forums);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize event list and adapter

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("events");

        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                String title, location, description, url;
                eventList = new ArrayList<>();
                int attendees;
                for (DataSnapshot eventSnapshot : task.getResult().getChildren()) {
                    title = eventSnapshot.getKey();
                    location = eventSnapshot.child("location").getValue(String.class);
                    description = eventSnapshot.child("description").getValue(String.class);
                    attendees = eventSnapshot.child("attendees").getValue(Integer.class);
                    url = eventSnapshot.child("imageLink").getValue(String.class);
                    eventList.add(new Event(title, location, description, url, attendees));
                    forumsAdapter = new ForumsAdapter(eventList);
                    recyclerView.setAdapter(forumsAdapter);
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
