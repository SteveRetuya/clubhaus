package com.example.clubhaus.user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.clubhaus.MainActivity;
import com.example.clubhaus.ProfileFragment;
import com.example.clubhaus.R;
import com.example.clubhaus.admin.AddEventFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {
    private Button cancel, save;

    private EditText name, email, newPassword;
    private String emailString, passwordString, username, newNameString, newEmailString, newPasswordString;
    private FirebaseDatabase database;
    private ConstraintLayout editPicture;
    private ImageView profileImageView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        username = ((MainActivity) requireActivity()).getUsername();
        cancel = view.findViewById(R.id.cancelButton);
        save = view.findViewById(R.id.saveButton);
        editPicture = view.findViewById(R.id.editProfilePictureButton);
        name = view.findViewById(R.id.nameEditText);
        email = view.findViewById(R.id.emailEditText);
        newPassword = view.findViewById(R.id.newPasswordEditText);
        database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
        name.setText(username);


        DatabaseReference reference = database.getReference("user").child(username);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                emailString = snapshot.child("email").getValue(String.class);
                passwordString = snapshot.child("password").getValue(String.class);
                email.setText(emailString);
                String url = snapshot.child("imageURL").getValue(String.class);
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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).goBackToProfileButton(v);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNameString = name.getText().toString().trim();
                newEmailString = email.getText().toString().trim();
                newPasswordString = newPassword.getText().toString().trim();

                if (!newEmailString.equals(emailString) || !newPasswordString.isBlank()) {
                    enterPassword(view);
                } else if (!newNameString.equals(username)) {
                    changeUsername();
                    ((MainActivity) requireActivity()).goBackToProfileButton(v);
                } else {
                    ((MainActivity) requireActivity()).goBackToProfileButton(v);
                }
            }
        });

        editPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeURL(v);
            }
        });

        return view;
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void enterPassword(View view) {
        Context context = view.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter Original Password");

        // Create a custom layout for the dialog
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create input fields for each field in the Event object
        EditText passwordInput = new EditText(context);
        passwordInput.setHint("Password");
        layout.addView(passwordInput);

        builder.setView(layout);

        // Set Save button to update the event
        builder.setPositiveButton("Enter", (dialog, which) -> {
            String inputPassword = passwordInput.getText().toString().trim();
            DatabaseReference userReference = database.getReference("user").child(username);
            if (inputPassword.equals(passwordString)) {
                if (!newEmailString.equals(emailString)) {
                    userReference.child("email").setValue(newEmailString);
                }

                if (!newPasswordString.isBlank()) {
                    userReference.child("password").setValue(newPasswordString);
                }

                if (!newNameString.equals(username)) {
                    changeUsername();
                }
            } else {
                Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
            }
            ((MainActivity) requireActivity()).goBackToProfileButton(view);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void changeURL(View view) {
        Context context = view.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter Image URL (Use Direct Link)");

        // Create a custom layout for the dialog
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create input fields for each field in the Event object
        EditText urlInput = new EditText(context);
        urlInput.setHint("Image URL");
        layout.addView(urlInput);

        builder.setView(layout);

        // Set Save button to update the event
        builder.setPositiveButton("Enter", (dialog, which) -> {
            String url = urlInput.getText().toString().trim();
            database.getReference("user").child(username).child("imageURL").setValue(url);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void changeUsername() {
        DatabaseReference userRef = database.getReference("user");

        userRef.child(newNameString).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(getContext(), "Username already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    // Username doesn't exist, proceed with change

                    // 1. Get user data from old username reference
                    DatabaseReference oldRef = userRef.child(username);
                    oldRef.child("username").setValue(newNameString);
                    oldRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                HashMap<String, Object> userData = (HashMap<String, Object>) task.getResult().getValue();

                                // 2. Create new reference with new username
                                DatabaseReference newRef = userRef.child(newNameString);

                                // 3. Set data to new reference
                                newRef.setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // 4. Delete old reference after successful write
                                            oldRef.removeValue();
                                        } else {
                                            // Handle error
                                        }
                                    }
                                });
                            } else {
                                // Handle error
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        ((MainActivity) requireActivity()).setUsername(newNameString);
    }
}