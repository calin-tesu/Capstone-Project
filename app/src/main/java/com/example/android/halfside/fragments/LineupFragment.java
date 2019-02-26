package com.example.android.halfside.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.halfside.R;
import com.example.android.halfside.adapters.LineupRecyclerViewAdapter;
import com.example.android.halfside.models.PerformingArtist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LineupFragment extends Fragment {

    List<PerformingArtist> performingArtistList;

    // Firebase instance variables
    private FirebaseDatabase artistsFirebaseDatabase;
    private DatabaseReference artistsDatabaseReference;
    private FirebaseStorage artistsPhotoFirebaseStorage;
    private StorageReference artistPhotoStorageReference;
    private ValueEventListener artistEventListener;

    private RecyclerView lineupRecyclerView;
    private GridLayoutManager layoutManager;
    private LineupRecyclerViewAdapter lineupAdapter;


    public LineupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lineup, container, false);

        lineupRecyclerView = rootView.findViewById(R.id.lineup_rv);

         //Initialize Firebase components
        artistsFirebaseDatabase = FirebaseDatabase.getInstance();
        artistsPhotoFirebaseStorage = FirebaseStorage.getInstance();
        artistsDatabaseReference = artistsFirebaseDatabase.getReference("artists");
        //Enable offline persistence
        if (artistsFirebaseDatabase == null) {
            artistsFirebaseDatabase.setPersistenceEnabled(true);
        }
        artistsDatabaseReference.keepSynced(true);
        artistPhotoStorageReference = artistsPhotoFirebaseStorage.getReference("artists_photo");

        layoutManager = new GridLayoutManager(getContext(), 2);
        lineupRecyclerView.setHasFixedSize(true);
        lineupRecyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Add value event listener to the artists list
        artistEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                performingArtistList = new ArrayList<>();

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot daySnapshot : artistSnapshot.getChildren()) {
                        for (DataSnapshot stageSnapshot : daySnapshot.getChildren()) {
                            PerformingArtist artist = stageSnapshot.getValue(PerformingArtist.class);
                            performingArtistList.add(artist);
                        }
                    }
                }

                lineupAdapter = new LineupRecyclerViewAdapter(performingArtistList);
                lineupRecyclerView.setAdapter(lineupAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load data!", Toast.LENGTH_SHORT).show();
            }
        };

        artistsDatabaseReference.addValueEventListener(artistEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (artistEventListener != null) {
            artistsDatabaseReference.removeEventListener(artistEventListener);
        }
    }
}
