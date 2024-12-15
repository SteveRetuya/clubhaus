package com.example.clubhaus.admin;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubhaus.MainActivity;
import com.example.clubhaus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private final List<Event> eventList;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);

        // Bind event data to views
        holder.EventTitle.setText(event.getTitle());
        holder.Attendees.setText(event.getAttendees() + " Attendees");
        holder.Location.setText(event.getLocation());
        holder.Description.setText(event.getDescription());

        // Fetch the date_list and time_list dynamically from Firebase
        fetchEventDateTimeList(event.getTitle(), holder.Date, holder.Time);

        // Set OnClickListener for btnEditEvent
        holder.btnEditEvent.setOnClickListener(v -> {
            // Handle edit button click
            editEvent(event, v);
        });

        holder.btnDeleteEvent.setOnClickListener(v -> {
            deleteEvent(event, v);
        });
    }

    private void fetchEventDateTimeList(String eventTitle, TextView dateTextView, TextView timeTextView) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("events")
                .child(eventTitle);

        // Fetch both date_list and time_list from Firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Fetch and concatenate date_list
                    StringBuilder dateList = new StringBuilder();
                    if (snapshot.child("date_List").exists()) {
                        for (DataSnapshot dateSnapshot : snapshot.child("date_List").getChildren()) {
                            dateList.append(dateSnapshot.getValue(String.class)).append("\n");
                        }
                    }

                    // Fetch and concatenate time_list
                    StringBuilder timeList = new StringBuilder();
                    if (snapshot.child("time_List").exists()) {
                        for (DataSnapshot timeSnapshot : snapshot.child("time_List").getChildren()) {
                            timeList.append(timeSnapshot.getValue(String.class)).append("\n");
                        }
                    }

                    // Update TextViews
                    dateTextView.setText(dateList.toString().trim().isEmpty() ? "No dates available" : dateList.toString().trim());
                    timeTextView.setText(timeList.toString().trim().isEmpty() ? "No times available" : timeList.toString().trim());
                } else {
                    dateTextView.setText("No dates available");
                    timeTextView.setText("No times available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dateTextView.setText("Error fetching dates");
                timeTextView.setText("Error fetching times");
                Log.e("FirebaseError", error.getMessage());
            }
        });
    }

    private void deleteEvent(Event event, View v) {

        String key = event.getTitle(); // Gets the Title of the to be deleted event

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("events");

        reference.child(key).removeValue().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(v.getContext(), "Event Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void editEvent(Event event, View view) {
        Context context = view.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Event");

        // Create a custom layout for the dialog
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create input fields for each field in the Event object
        EditText titleInput = new EditText(context);
        titleInput.setHint("Title");
        titleInput.setText(event.getTitle());
        layout.addView(titleInput);

        EditText locationInput = new EditText(context);
        locationInput.setHint("Location");
        locationInput.setText(event.getLocation());
        layout.addView(locationInput);

        EditText interestInput = new EditText(context);
        interestInput.setHint("Interests");
        interestInput.setText(event.getInterests());
        layout.addView(interestInput);

        EditText urlInput = new EditText(context);
        urlInput.setHint("Image URL");
        urlInput.setText(event.getImageLink());
        layout.addView(urlInput);

        EditText descriptionInput = new EditText(context);
        descriptionInput.setHint("Description");
        descriptionInput.setText(event.getDescription());
        layout.addView(descriptionInput);

        // To store selected dates and times
        List<String> selectedDate = event.getDate_List() != null ? new ArrayList<>(event.getDate_List()) : new ArrayList<>();
        List<String> selectedTime = event.getTime_List() != null ? new ArrayList<>(event.getTime_List()) : new ArrayList<>();

        // Show Date Picker Dialog
        Button datePickerButton = new Button(context);
        datePickerButton.setText(selectedDate.isEmpty() ? "Add Date" : "Dates: " + selectedDate.toString());
        layout.addView(datePickerButton);

        datePickerButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view1, year, month, dayOfMonth) -> {
                String newDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                selectedDate.add(newDate);
                datePickerButton.setText("Date: " + selectedDate.toString());
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        // Show Time Picker Dialog
        Button timePickerButton = new Button(context);
        timePickerButton.setText(selectedTime.isEmpty() ? "Add Time" : "Times: " + selectedTime.toString());
        layout.addView(timePickerButton);

        timePickerButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view12, hourOfDay, minute) -> {
                String newTime = String.format("%02d:%02d", hourOfDay, minute);
                selectedTime.add(newTime);
                timePickerButton.setText("Time: " + selectedTime.toString());
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
            timePickerDialog.show();
        });

        builder.setView(layout);

        // Set Save button to update the event
        builder.setPositiveButton("Save", (dialog, which) -> {
            String oldTitle = event.getTitle(); // Store old title in case it changes
            event.setTitle(titleInput.getText().toString());
            event.setLocation(locationInput.getText().toString());
            event.setDescription(descriptionInput.getText().toString());
            event.setInterests(interestInput.getText().toString());
            event.setDate_List(selectedDate);
            event.setTime_List(selectedTime);
            event.setImageLink(urlInput.getText().toString());

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
            DatabaseReference reference = database.getReference("events");

            if (!oldTitle.equals(event.getTitle())) {
                reference.child(oldTitle).removeValue();
            }

            reference.child(event.getTitle()).setValue(event).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Event updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to update event", Toast.LENGTH_SHORT).show();
                }
            });

            notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }



    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView EventTitle, Attendees, Location, Description, Date, Time;
        Button btnEditEvent, btnDeleteEvent;

        @SuppressLint("WrongViewCast")
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            EventTitle = itemView.findViewById(R.id.EventTitle);
            Attendees = itemView.findViewById(R.id.Attendees);
            Location = itemView.findViewById(R.id.Location);
            Description = itemView.findViewById(R.id.Description);
            Date = itemView.findViewById(R.id.Date);
            Time = itemView.findViewById(R.id.Time);
            btnEditEvent = itemView.findViewById(R.id.btnEditEvent);
            btnDeleteEvent = itemView.findViewById(R.id.btnDeleteEvent);
        }
    }
}