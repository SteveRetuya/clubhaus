package com.example.clubhaus.admin;

import android.os.Bundle;
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

        eventList.add(new Event("Car Meet", "Camp John Hay", "Lorem Ipsum", 20)); // For Testing Purposes
        eventList.add(new Event("Car Meet", "Camp John Hay", "Lorem Ipsum", 20)); // For Testing Purposes
        eventList.add(new Event("Car Meet", "Camp John Hay", "Lorem Ipsum", 20)); // For Testing Purposes
        eventList.add(new Event("Car Meet", "Camp John Hay", "Lorem Ipsum", 20)); // For Testing Purposes

        eventAdapter = new EventAdapter(eventList);
        recyclerView.setAdapter(eventAdapter);

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
