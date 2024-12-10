package com.example.clubhaus.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clubhaus.LoginActivity;
import com.example.clubhaus.MainActivity;
import com.example.clubhaus.R;
import com.example.clubhaus.SignUpActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditEventFragment extends Fragment {

    EditText titleET, locationET, interestET, descriptionET, urlET;
    Button addEventButton;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_event, container, false);
        addEventButton = view.findViewById(R.id.AddEventButton);

        addEventButton.setOnClickListener(v -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference reference = database.getReference("events");
            titleET = view.findViewById(R.id.editTitle);
            locationET = view.findViewById(R.id.editLocation);
            interestET = view.findViewById(R.id.editInterest);
            descriptionET = view.findViewById(R.id.editDescription);
            urlET = view.findViewById(R.id.editURL);
            String title = titleET.getText().toString().trim();
            String location = locationET.getText().toString().trim();
            String interest = interestET.getText().toString().trim();
            String description = descriptionET.getText().toString().trim();
            String url = urlET.getText().toString().trim();
            Event event = new Event(title, location, description, url, 0);
            DatabaseReference newReference = reference.child(title);
            newReference.setValue(event);
            ((MainActivity) requireActivity()).editEventButton(v);
        });

        return view;
    }

}