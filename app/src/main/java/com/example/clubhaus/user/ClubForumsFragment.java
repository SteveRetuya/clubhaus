package com.example.clubhaus.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.clubhaus.MainActivity;
import com.example.clubhaus.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class ClubForumsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club_forums, container, false);
        Button joinButton = view.findViewById(R.id.join_button);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (joinButton.getText().equals("Join")) {
                    joinButton.setBackgroundColor(getResources().getColor(R.color.joined));
                    joinButton.setText("Joined");
                } else {
                    joinButton.setBackgroundColor(getResources().getColor(R.color.join));
                    joinButton.setText("Join");
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
