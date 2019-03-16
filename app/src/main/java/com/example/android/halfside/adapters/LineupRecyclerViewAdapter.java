package com.example.android.halfside.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.halfside.R;
import com.example.android.halfside.activities.ArtistDetailsActivity;
import com.example.android.halfside.models.PerformingArtist;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Calin Tesu on 2/11/2019.
 */
public class LineupRecyclerViewAdapter extends RecyclerView.Adapter<LineupRecyclerViewAdapter.LineupViewHolder> {

    // Cached copy of performing artists
    private List<PerformingArtist> performingArtistList;

    private Context context;

    // Firebase instance variables
    private FirebaseStorage artistsPhotoFirebaseStorage;
    private StorageReference artistPhotoStorageReference;

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

        //Initialize Firebase components
        artistsPhotoFirebaseStorage = FirebaseStorage.getInstance();
        artistPhotoStorageReference = artistsPhotoFirebaseStorage.getReference("artists_photo");

        return new LineupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LineupViewHolder lineupViewHolder, int position) {
        final String photoFirebaseUrl = performingArtistList.get(position)
                .getArtistUrls()
                .getPhotoFirebaseUrl() + ".jpg";

        //Get the Uri of artist photo from Firebase Storage
        artistPhotoStorageReference.child(photoFirebaseUrl).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        displayPhoto(uri, lineupViewHolder);
                    }
                });

        String artistName = performingArtistList.get(position).getArtistName();
        lineupViewHolder.artistName.setText(artistName);
    }

    private void displayPhoto(Uri photoStorageUri, LineupViewHolder lineupViewHolder) {
        Glide.with(context)
                .load(photoStorageUri)
                .into(lineupViewHolder.artistPhoto);
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

            PerformingArtist performingArtist = performingArtistList.get(adapterPosition);
            Intent intent = new Intent(context, ArtistDetailsActivity.class);

            Gson gson = new Gson();
            String json = gson.toJson(performingArtist);

            intent.putExtra("performingArtist", json);
            context.startActivity(intent);
        }
    }
}
