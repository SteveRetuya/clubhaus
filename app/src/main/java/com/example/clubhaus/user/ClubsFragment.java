package com.example.clubhaus.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.clubhaus.MainActivity;
import com.example.clubhaus.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClubsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clubs, container, false);
        Button joinButton = view.findViewById(R.id.join_button);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the method in MainActivity to handle the button click
                ((MainActivity) requireActivity()).joinButtonClubs(v);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}