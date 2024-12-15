package com.example.clubhaus.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clubhaus.MainActivity;
import com.example.clubhaus.R;
import com.example.clubhaus.SignUpActivity;
import com.example.clubhaus.User;
import com.example.clubhaus.admin.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.List;

public class ForumsAdapter extends RecyclerView.Adapter<ForumsAdapter.EventViewHolder> {
    List<Event> eventList;
    public ForumsAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_forums, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.EventTitle.setText(event.getTitle());
        holder.Attendees.setText(event.getAttendees() + " Attendees");
        holder.Location.setText(event.getLocation());
        holder.Description.setText(event.getDescription());
        Glide.with(holder.itemView)
                .load(event.getImageLink())
                .into(holder.picIV);

        holder.JoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://clubhaus-37b05-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference reference = database.getReference("events").child(holder.EventTitle.getText().toString());

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long attendeeNum = snapshot.child("attendees").getValue(Long.class);
                        if (holder.JoinButton.getText().equals("Join")) {
                            holder.JoinButton.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.joined));
                            holder.JoinButton.setText("Joined");
                            reference.child("attendees").setValue(attendeeNum + 1);
                            holder.Attendees.setText(attendeeNum + 1 +" Attendees");
                        } else {
                            holder.JoinButton.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.join));
                            holder.JoinButton.setText("Join");
                            reference.child("attendees").setValue(attendeeNum - 1);
                            holder.Attendees.setText(attendeeNum - 1 +" Attendees");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView EventTitle, Attendees, Location, Description;
        ImageView picIV;
        Button JoinButton;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            EventTitle = itemView.findViewById(R.id.event_title_view);
            Attendees = itemView.findViewById(R.id.attendees_view);
            Location = itemView.findViewById(R.id.location_view);
            Description = itemView.findViewById(R.id.description_view);
            JoinButton = itemView.findViewById(R.id.join_button);
            picIV = itemView.findViewById(R.id.pic);
        }
    }
}
