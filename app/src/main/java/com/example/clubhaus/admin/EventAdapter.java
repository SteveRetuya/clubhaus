package com.example.clubhaus.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubhaus.R;
import com.example.clubhaus.admin.Event;

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

        EditText descriptionInput = new EditText(context);
        descriptionInput.setHint("Description");
        descriptionInput.setText(event.getDescription());
        layout.addView(descriptionInput);

        builder.setView(layout);

        // Set Save button to update the event
        builder.setPositiveButton("Save", (dialog, which) -> {
            // Update event details
            event.setTitle(titleInput.getText().toString());
            event.setLocation(locationInput.getText().toString());
            event.setDescription(descriptionInput.getText().toString());
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
        Button btnEditEvent;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            EventTitle = itemView.findViewById(R.id.EventTitle);
            Attendees = itemView.findViewById(R.id.Attendees);
            Location = itemView.findViewById(R.id.Location);
            Description = itemView.findViewById(R.id.Description);
            btnEditEvent = itemView.findViewById(R.id.btnEditEvent);
        }
    }
}
