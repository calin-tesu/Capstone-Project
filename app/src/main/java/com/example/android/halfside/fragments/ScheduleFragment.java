package com.example.android.halfside.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.halfside.R;
import com.example.android.halfside.adapters.ScheduleRecyclerViewAdapter;
import com.example.android.halfside.models.PerformingArtist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    private int dayOfFestival;
    private int stageOfFestival;

    // Firebase instance variables
    private FirebaseDatabase artistsFirebaseDatabase;
    private DatabaseReference artistsDatabaseReference;

    private RecyclerView scheduleRecyclerView;
    private ScheduleRecyclerViewAdapter scheduleRecyclerViewAdapter;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        dayOfFestival = 1;
        stageOfFestival = 1;

        //Initialize Firebase components
        artistsFirebaseDatabase = FirebaseDatabase.getInstance();
        artistsDatabaseReference = artistsFirebaseDatabase.getReference("artists");

        scheduleRecyclerView = rootView.findViewById(R.id.schedule_rv);
        scheduleRecyclerView.setHasFixedSize(true);
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        queryFirebaseArtists();

        //Top navigation for stages of the festival
        final TabLayout tabLayout = rootView.findViewById(R.id.top_sliding_tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                stageOfFestival = tab.getPosition() + 1;
                Log.i("TAB POSITION = ", String.valueOf(stageOfFestival));
                queryFirebaseArtists();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Bottom navigation for day of the festival
        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    //Set the day of the festival
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.day_1:
                                dayOfFestival = 1;
                                TabLayout.Tab tab = tabLayout.getTabAt(0);
                                tab.select();
                                queryFirebaseArtists();
                                return true;
                            case R.id.day_2:
                                dayOfFestival = 2;
                                tab = tabLayout.getTabAt(0);
                                tab.select();
                                queryFirebaseArtists();
                                return true;
                            case R.id.day_3:
                                dayOfFestival = 3;
                                tab = tabLayout.getTabAt(0);
                                tab.select();
                                queryFirebaseArtists();
                                return true;
                        }
                        return false;
                    }
                }
        );

        // Inflate the layout for this fragment
        return rootView;
    }

    public void queryFirebaseArtists() {
        Query myQuery = artistsDatabaseReference
                .child("day-" + String.valueOf(dayOfFestival))
                .child("stage-" + String.valueOf(stageOfFestival))
                .orderByChild("timeOfPerforming");

        myQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<PerformingArtist> artists = new ArrayList<>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    PerformingArtist artist = childSnapshot.getValue(PerformingArtist.class);
                    artists.add(artist);
                }

                scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter(artists);
                scheduleRecyclerView.setAdapter(scheduleRecyclerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


