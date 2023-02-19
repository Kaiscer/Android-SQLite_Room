package com.example.proyectobadt2_kaiscervasquez.recycleutil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectobadt2_kaiscervasquez.R;
import com.example.proyectobadt2_kaiscervasquez.entity.Earthquake;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsVH>  {

    private ArrayList<Earthquake> list;


    public EventsAdapter(ArrayList<Earthquake> list ) {
        this.list = list;

    }

    @NonNull
    @Override
    public EventsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsVH holder, int position) {
        holder.bindEvent(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class EventsVH extends RecyclerView.ViewHolder{
        TextView tvDateTime, tvScale, tvDeviceName, tvLocation,tvCoordinates, tvDead;
        public EventsVH(@NonNull View itemView) {
            super(itemView);
            tvDateTime = itemView.findViewById(R.id.tv_dateTime);
            tvScale = itemView.findViewById(R.id.tv_scale);
            tvDeviceName = itemView.findViewById(R.id.tv_deviceName);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvCoordinates = itemView.findViewById(R.id.tv_coordinates);
            tvDead = itemView.findViewById(R.id.tv_dead);
        }

        public void bindEvent(Earthquake event){
            tvDateTime.setText(event.getDateTime());
            tvScale.setText(String.valueOf(event.getScale()));
            tvDeviceName.setText(event.getDeviceName());
            tvLocation.setText(event.getLocation());
            tvCoordinates.setText(event.getCoordinates());
            tvDead.setText(event.getDead());
        }
    }
}
