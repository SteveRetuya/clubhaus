package com.example.clubhaus.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clubhaus.R;
import com.example.clubhaus.admin.Event;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.EventViewHolder> {
    List<Event> eventListHome;
    public HomeAdapter(List<Event> eventList) {
        this.eventListHome = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventListHome.get(position);
        holder.userLocationTV.setText(event.getLocation());
        Glide.with(holder.itemView)
                .load(event.getImageLink())
                .into(holder.imageViewIV);
    }

    @Override
    public int getItemCount() {
        return eventListHome.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView userLocationTV;
        ImageView imageViewIV;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            userLocationTV = itemView.findViewById(R.id.userLocation);
            imageViewIV = itemView.findViewById(R.id.imageView);
        }
    }
}
