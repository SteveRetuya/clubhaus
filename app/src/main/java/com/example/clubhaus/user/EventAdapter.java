package com.example.clubhaus.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubhaus.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final List<Event> eventList;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);

        // Title
        holder.titleTextView.setText(event.getTitle() != null ? event.getTitle() : "No Title");

        // Attendees (convert int to string)
        holder.attendeesTextView.setText(String.valueOf(event.getAttendees()));

        // Location
        holder.locationTextView.setText(event.getLocation() != null ? event.getLocation() : "No Location");

        // Date (format array to a readable string)
        String[] dateArray = event.getDate();
        holder.dateTextView.setText(dateArray != null ? String.join(", ", dateArray) : "No Date");

        // Time (format array to a readable string)
        String[] timeArray = event.getTime();
        holder.timeTextView.setText(timeArray != null ? String.join(", ", timeArray) : "No Time");

        // Description
        holder.descriptionTextView.setText(event.getDescription() != null ? event.getDescription() : "No Description");
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, attendeesTextView, locationTextView, dateTextView, timeTextView, descriptionTextView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.user_item_EventTitle);
            attendeesTextView = itemView.findViewById(R.id.user_item_Attendees);
            locationTextView = itemView.findViewById(R.id.user_item_Location);
            dateTextView = itemView.findViewById(R.id.user_item_Date);
            timeTextView = itemView.findViewById(R.id.user_item_Time);
            descriptionTextView = itemView.findViewById(R.id.user_item_Description);
        }
    }
}
