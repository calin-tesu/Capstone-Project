package com.example.android.halfside.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.halfside.R;
import com.example.android.halfside.activities.ArtistDetailsActivity;
import com.example.android.halfside.models.PerformingArtist;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Calin Tesu on 2/11/2019.
 */
public class LineupRecyclerViewAdapter extends RecyclerView.Adapter<LineupRecyclerViewAdapter.LineupViewHolder> {

    // Cached copy of performing artists
    private List<PerformingArtist> performingArtistList;

    private Context context;

    // Provide a suitable constructor
    public LineupRecyclerViewAdapter(List<PerformingArtist> artists) {
        performingArtistList = artists;
    }

    @NonNull
    @Override
    public LineupViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lineup_grid_item, viewGroup, false);
        return new LineupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LineupViewHolder lineupViewHolder, int position) {
        lineupViewHolder.artistPhoto.setImageResource(R.drawable.generic_photo);

        String artistName = performingArtistList.get(position).getArtistName();
        lineupViewHolder.artistName.setText(artistName);
    }

    @Override
    public int getItemCount() {
        return performingArtistList.size();
    }

    public class LineupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView artistPhoto;

        public final TextView artistName;

        public LineupViewHolder(@NonNull View itemView) {
            super(itemView);
            artistPhoto = itemView.findViewById(R.id.artist_photo);
            artistName = itemView.findViewById(R.id.artist_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            //Toast.makeText(context, String.valueOf(performingArtistList.get(adapterPosition).getStagePerforming()), Toast.LENGTH_SHORT).show();

            PerformingArtist performingArtist = performingArtistList.get(adapterPosition);
            Intent intent = new Intent(context, ArtistDetailsActivity.class);

            Gson gson = new Gson();
            String json = gson.toJson(performingArtist);

            intent.putExtra("performingArtist", json);
            context.startActivity(intent);
        }
    }
}
