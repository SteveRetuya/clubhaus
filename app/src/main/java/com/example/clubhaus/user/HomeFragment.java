package com.example.clubhaus.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clubhaus.R;
import com.example.clubhaus.admin.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    List<Event> eventListHome;
    private HomeAdapter homeAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize event list and adapter

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("events");

        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                String title, location, description, url;
                eventListHome = new ArrayList<>();
                int attendees;
                for (DataSnapshot eventSnapshot : task.getResult().getChildren()) {
                    title = eventSnapshot.getKey();
                    location = eventSnapshot.child("location").getValue(String.class);
                    description = eventSnapshot.child("description").getValue(String.class);
                    attendees = eventSnapshot.child("attendees").getValue(Integer.class);
                    url = eventSnapshot.child("imageLink").getValue(String.class);
                    eventListHome.add(new Event(title, location, description, url,attendees));
                    homeAdapter = new HomeAdapter(eventListHome);
                    recyclerView.setAdapter(homeAdapter);
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}