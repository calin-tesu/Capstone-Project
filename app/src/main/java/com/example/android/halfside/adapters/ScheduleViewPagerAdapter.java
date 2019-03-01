package com.example.android.halfside.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.halfside.R;
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
 * Created by Calin Tesu on 2/21/2019.
 */
public class ScheduleViewPagerAdapter extends PagerAdapter {

    private static final int NUMBER_OF_STAGES = 3;

    List<PerformingArtist> performingArtistList;
    RecyclerView scheduleRecyclerView;
    ScheduleRecyclerViewAdapter scheduleRecyclerViewAdapter;

    // Firebase instance variables
    private FirebaseDatabase artistsFirebaseDatabase;
    private DatabaseReference artistsDatabaseReference;
    private ValueEventListener artistListener;

    private Context context;

    private int day;

    private TextView stageOfGig;
    private TextView dayOfGig;

    public ScheduleViewPagerAdapter(Context context, int day) {
        this.context = context;
        this.day = day;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = "STAGE 1";
                break;
            case 1:
                title = "STAGE 2";
                break;
            case 2:
                title = "STAGE 3";
                break;
        }
        return title;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        final ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.schedule_view_pager, container, false);

        //Initialize Firebase components
        artistsFirebaseDatabase = FirebaseDatabase.getInstance();
        artistsDatabaseReference = artistsFirebaseDatabase.getReference("artists");

        //performingArtistList = new ArrayList<>();

        /*stageOfGig = viewGroup.findViewById(R.id.stage_of_gig);
        dayOfGig = viewGroup.findViewById(R.id.day_of_gig);

        dayOfGig.setText("DAY " + String.valueOf(day));
        stageOfGig.setText("STAGE " + String.valueOf(position + 1));*/

        //#stageNumber is position + 1 because the first adapter position is 0 and
        //is corresponding to Stage 1
        //performingArtistList = queryFirebaseArtists(position + 1);

        scheduleRecyclerView = viewGroup.findViewById(R.id.schedule_rv);
        scheduleRecyclerView.setHasFixedSize(true);
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(context));
       // scheduleRecyclerView.setAdapter(new ScheduleRecyclerViewAdapter(performingArtistList));

        Query myQuery = artistsDatabaseReference.child("day-" + String.valueOf(day))
                .child("stage-" + String.valueOf(position + 1))
                .orderByChild("timeOfPerforming");

        myQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                performingArtistList = new ArrayList<>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    PerformingArtist artist = childSnapshot.getValue(PerformingArtist.class);
                    performingArtistList.add(artist);
                }

                ScheduleRecyclerViewAdapter scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter(performingArtistList);
                scheduleRecyclerView.setAdapter(scheduleRecyclerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Failed to load data!", Toast.LENGTH_SHORT).show();
            }
        });

        /*scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter(performingArtistList);
        scheduleRecyclerView.setAdapter(scheduleRecyclerViewAdapter);*/

        container.addView(viewGroup);

        return viewGroup;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return NUMBER_OF_STAGES;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    //
    private List<PerformingArtist> queryFirebaseArtists(int stageNumber) {
        final List<PerformingArtist> artists = new ArrayList<>();
        Query myQuery = artistsDatabaseReference.child("day-" + day).child("stage-" + stageNumber).orderByChild("timeOfPerforming");
        myQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    PerformingArtist artist = childSnapshot.getValue(PerformingArtist.class);
                    artists.add(artist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Failed to load data!", Toast.LENGTH_SHORT).show();
            }
        });

        //scheduleRecyclerView.setAdapter(new ScheduleRecyclerViewAdapter(artists));

        return artists;
    }
}
