package com.example.clubhaus.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        holder.EventTitle.setText(event.getTitle());
        holder.Attendees.setText(event.getAttendees() + " Attendees");
        holder.Location.setText(event.getLocation());
        holder.Description.setText(event.getDescription());
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
