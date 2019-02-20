package com.example.android.halfside.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.halfside.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    private TextView dayOfGig;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        dayOfGig = rootView.findViewById(R.id.day_of_gig);

        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.day_1:
                                dayOfGig.setText("Day 1");
                                break;
                            case R.id.day_2:
                                dayOfGig.setText("Day 2");
                                break;
                            case R.id.day_3:
                                dayOfGig.setText("Day 3");
                                break;
                        }

                        return false;
                    }
                }
        );

        // Inflate the layout for this fragment
        return rootView;
    }

}
