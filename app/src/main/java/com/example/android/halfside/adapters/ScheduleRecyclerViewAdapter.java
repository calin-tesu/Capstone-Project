package com.example.android.halfside.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.halfside.R;
import com.example.android.halfside.models.PerformingArtist;

import java.util.List;

/**
 * Created by Calin Tesu on 3/1/2019.
 */
public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ScheduleViewHolder> {

    // Cached copy of performing artists
    private List<PerformingArtist> performingArtistList;

    // Provide a suitable constructor
    public ScheduleRecyclerViewAdapter(List<PerformingArtist> artists) {
        performingArtistList = artists;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.schedule_listview, viewGroup, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder scheduleViewHolder, int position) {
        scheduleViewHolder.startTime.setText(performingArtistList.get(position).getTimeOfPerforming());
        scheduleViewHolder.artistName.setText(performingArtistList.get(position).getArtistName());

        //Alternate background color of the RecyclerView items
        if (position % 2 == 0) {
            scheduleViewHolder.itemView.setBackgroundColor(Color.parseColor("#f1f8e9"));
        } else {
            scheduleViewHolder.itemView.setBackgroundColor(Color.parseColor("#f9fbe7"));
        }
    }

    @Override
    public int getItemCount() {
        return performingArtistList.size();
    }


    public class ScheduleViewHolder extends RecyclerView.ViewHolder {

        TextView startTime;
        TextView artistName;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.start_time);
            artistName = itemView.findViewById(R.id.artist_name);
        }
    }
}
