package com.example.clubhaus.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.clubhaus.LoginActivity;
import com.example.clubhaus.MainActivity;
import com.example.clubhaus.R;
import com.example.clubhaus.SignUpActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditEventFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    EditText titleET, locationET, interestET, descriptionET, urlET;
    private Button selectDateButton, selectTimeButton, selectImageButton;
    private ImageView eventImage;
    private String selectedDate, selectedTime;
    Button addEventButton;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_event, container, false);
        addEventButton = view.findViewById(R.id.AddEventButton);

        urlET = view.findViewById(R.id.editURL);
        titleET = view.findViewById(R.id.editTitle);
        locationET = view.findViewById(R.id.editLocation);
        interestET = view.findViewById(R.id.editInterest);
        descriptionET = view.findViewById(R.id.editDescription);
        selectDateButton = view.findViewById(R.id.datePicker);
        selectTimeButton = view.findViewById(R.id.timePicker);
        selectImageButton = view.findViewById(R.id.addImageButton);
        eventImage = view.findViewById(R.id.eventImage);


        // Set Date Picker
        selectDateButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view1, year, month, dayOfMonth) -> {
                        selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        selectDateButton.setText(selectedDate);
                    },
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        // Set Time Picker
        selectTimeButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                    (view1, hourOfDay, minute) -> {
                        selectedTime = hourOfDay + ":" + String.format("%02d", minute);
                        selectTimeButton.setText(selectedTime);
                    },
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
            timePickerDialog.show();
        });

        selectImageButton.setOnClickListener(v -> showImagePickerOptions());

        addEventButton.setOnClickListener(v -> {

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference reference = database.getReference("events");

            urlET = view.findViewById(R.id.editURL);
            String title = titleET.getText().toString().trim();
            String location = locationET.getText().toString().trim();
            String interest = interestET.getText().toString().trim();
            String description = descriptionET.getText().toString().trim();
            // Convert selected date and time to Lists
            List<String> date = new ArrayList<>();
            date.add(selectedDate);

            List<String> time = new ArrayList<>();
            time.add(selectedTime);

            String url = urlET.getText().toString().trim();

            if(title.isEmpty()){
                Toast.makeText(getContext(), "Enter Title", Toast.LENGTH_SHORT).show();
                return;
            }

            if(location.isEmpty()){
                Toast.makeText(getContext(), "Enter location", Toast.LENGTH_SHORT).show();
                return;
            }

            if(interest.isEmpty()){
                Toast.makeText(getContext(), "Enter interest", Toast.LENGTH_SHORT).show();
                return;
            }

            if(description.isEmpty()){
                Toast.makeText(getContext(), "Enter description", Toast.LENGTH_SHORT).show();
                return;
            }


            Event event = new Event(title, location, description, 0, interest, date, time, url);
            DatabaseReference newReference = reference.child(title);
            newReference.setValue(event);
            ((MainActivity) requireActivity()).editEventButton(v);
        });



        return view;
    }

    private void showImagePickerOptions() {
        String[] options = {"Select from Gallery", "Take a Photo"};
        new AlertDialog.Builder(getContext())
                .setTitle("Choose Image Source")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        openGallery();
                    } else if (which == 1) {
                        openCamera();
                    }
                })
                .show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null) {
                Uri imageUri = data.getData();
                eventImage.setImageURI(imageUri);
            } else if (requestCode == CAMERA_REQUEST && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                eventImage.setImageBitmap(photo);
            }
        }
    }

}