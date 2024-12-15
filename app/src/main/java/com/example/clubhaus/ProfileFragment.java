package com.example.clubhaus;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String ARG_USERNAME = "username";
    private ImageView profileImageView;
    private TextView editButton, email;
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

        email = view.findViewById(R.id.emailTextView);

        Bundle arguments = getArguments();
        String username = ((MainActivity) requireActivity()).getUsername();
        // Update the UI with the username
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView usernameTextView = view.findViewById(R.id.profileName); // Ensure this TextView exists in fragment_profile.xml
        usernameTextView.setText(username);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");

        DatabaseReference reference = database.getReference("user").child(username);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String url = snapshot.child("imageURL").getValue(String.class);
                String emailString = snapshot.child("email").getValue(String.class);
                email.setText(emailString);
                if (url != null && !url.isBlank()) {
                    profileImageView = view.findViewById(R.id.profileImage);
                    Glide.with(view)
                            .load(url)
                            .into(profileImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editButton = view.findViewById(R.id.editProfile);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).editProfileButton(v);
            }
        });

        return view;
    }
}