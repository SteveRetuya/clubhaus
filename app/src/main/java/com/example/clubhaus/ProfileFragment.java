package com.example.clubhaus;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String ARG_USERNAME = "username";

    public static ProfileFragment newInstance(String username) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String username = arguments.getString(ARG_USERNAME);

            // Update the UI with the username
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView usernameTextView = view.findViewById(R.id.profileName); // Ensure this TextView exists in fragment_profile.xml
            usernameTextView.setText(username);
        }


        return view;
    }
}