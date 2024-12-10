package com.example.clubhaus.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubhaus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText searchInput;
    private RecyclerView recyclerView;
    private TextView placeholderText;

    private EventAdapter adapter;
    private final List<Event> eventList = new ArrayList<>();
    private final List<Event> originalEventList = new ArrayList<>();

    private DatabaseReference databaseRef;
    private final Handler searchHandler = new Handler(Looper.getMainLooper());
    private static final int SEARCH_DELAY_MS = 300; // Debounce delay

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize UI components
        searchInput = rootView.findViewById(R.id.user_search_bar);
        recyclerView = rootView.findViewById(R.id.search_results);
        placeholderText = rootView.findViewById(R.id.placeholder_text);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new EventAdapter(eventList);
        recyclerView.setAdapter(adapter);

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseRef = database.getReference("events");

        // Load data and set up search
        loadAllEvents();
        setupSearchFeature();

        return rootView;
    }

    private void loadAllEvents() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                originalEventList.clear();
                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    Event event = eventSnapshot.getValue(Event.class);
                    if (event != null) {
                        originalEventList.add(event);
                    }
                }
                resetToDefaultData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load events. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSearchFeature() {
        searchInput.addTextChangedListener(new TextWatcher() {
            private Runnable searchRunnable;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Debounce search input
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }
                searchRunnable = () -> performLocalSearch(s.toString().trim());
                searchHandler.postDelayed(searchRunnable, SEARCH_DELAY_MS);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void performLocalSearch(String searchText) {
        if (searchText.isEmpty()) {
            resetToDefaultData();
            return;
        }

        eventList.clear();
        for (Event event : originalEventList) {
            if (event.getTitle() != null && event.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                eventList.add(event);
            }
        }
        adapter.notifyDataSetChanged();
        togglePlaceholder(eventList.isEmpty());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void resetToDefaultData() {
        eventList.clear();
        eventList.addAll(originalEventList);
        adapter.notifyDataSetChanged();
        togglePlaceholder(eventList.isEmpty());
    }

    private void togglePlaceholder(boolean show) {
        placeholderText.setVisibility(show ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up handler to avoid memory leaks
        searchHandler.removeCallbacksAndMessages(null);
    }
}
