package com.example.clubhaus.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubhaus.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
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

        // Set OnClickListener for btnEditEvent
        holder.btnEditEvent.setOnClickListener(v -> {
            // Handle edit button click
            editEvent(event, v);
        });

        holder.btnDeleteEvent.setOnClickListener(v -> {deleteEvent(event, v);});
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

    @SuppressLint("NotifyDataSetChanged")
    private void editEvent(Event event, View view) {
        // For simplicity, open a dialog for editing event details
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

        EditText descriptionInput = new EditText(context);
        descriptionInput.setHint("Description");
        descriptionInput.setText(event.getDescription());
        layout.addView(descriptionInput);

        // Add Date and Time Pickers
        Button datePickerButton = new Button(context);
        datePickerButton.setText("Pick Date");
        layout.addView(datePickerButton);

        Button timePickerButton = new Button(context);
        timePickerButton.setText("Pick Time");
        layout.addView(timePickerButton);

        // To store selected date and time
        final String[] selectedDate = {""};
        final String[] selectedTime = {""};

        // Show Date Picker Dialog
        datePickerButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view1, year, month, dayOfMonth) -> {
                selectedDate[0] = dayOfMonth + "/" + (month + 1) + "/" + year;
                datePickerButton.setText("Date: " + selectedDate[0]);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        // Show Time Picker Dialog
        timePickerButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view12, hourOfDay, minute) -> {
                selectedTime[0] = String.format("%02d:%02d", hourOfDay, minute);
                timePickerButton.setText("Time: " + selectedTime[0]);
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        });


        builder.setView(layout);

        // Set Save button to update the event
        builder.setPositiveButton("Save", (dialog, which) -> {
            // Update event details
            event.setTitle(titleInput.getText().toString());
            event.setLocation(locationInput.getText().toString());
            event.setDescription(descriptionInput.getText().toString());
            event.setInterests(interestInput.getText().toString());
            event.setDate(selectedDate);
            event.setTime(selectedTime);
//
//            // Save updated event to Firebase
//            String key = event.getTitle(); // Or use a unique ID
//            databaseReference.child(key).setValue(event)
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(context, "Event updated successfully", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "Error updating event", Toast.LENGTH_SHORT).show();
//                        }
//                    });

            // Notify RecyclerView to refresh the updated data
            notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView EventTitle, Attendees, Location, Description;
        Button btnEditEvent, btnDeleteEvent;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            EventTitle = itemView.findViewById(R.id.EventTitle);
            Attendees = itemView.findViewById(R.id.Attendees);
            Location = itemView.findViewById(R.id.Location);
            Description = itemView.findViewById(R.id.Description);
            btnEditEvent = itemView.findViewById(R.id.btnEditEvent);
            btnDeleteEvent = itemView.findViewById(R.id.btnDeleteEvent);
        }
    }
}
