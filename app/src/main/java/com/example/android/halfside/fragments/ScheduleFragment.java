package com.example.android.halfside.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
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
    private TextView stageOfGig;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        dayOfGig = rootView.findViewById(R.id.day_of_gig);
        dayOfGig.setText("Day 1");
        stageOfGig = rootView.findViewById(R.id.stage_of_gig);

        //Top navigation for stages of the festival
        final TabLayout tabLayout = rootView.findViewById(R.id.top_sliding_tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                stageOfGig.setText("STAGE " + String.valueOf(tabLayout.getSelectedTabPosition() + 1));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                stageOfGig.setText("STAGE " + String.valueOf(tabLayout.getSelectedTabPosition() + 1));
            }
        });

        //Bottom navigation for day of the festival
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
