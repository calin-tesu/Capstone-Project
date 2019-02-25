package com.example.android.halfside.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.halfside.R;
import com.example.android.halfside.adapters.ScheduleViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    private int day;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        final ViewPager viewPager = rootView.findViewById(R.id.schedule_view_pager);

        //Bottom navigation for day of the festival
        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.bottom_nav);

        //TODO replace setText with a method that query Firebase database
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    //Set the day of the festival
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.day_1:
                                day = 1;
                                viewPager.setAdapter(new ScheduleViewPagerAdapter(getContext(), day));
                                return true;
                            case R.id.day_2:
                                day = 2;
                                viewPager.setAdapter(new ScheduleViewPagerAdapter(getContext(), day));
                                return true;
                            case R.id.day_3:
                                day = 3;
                                viewPager.setAdapter(new ScheduleViewPagerAdapter(getContext(), day));
                                return true;
                        }
                        return false;
                    }
                }
        );

        viewPager.setAdapter(new ScheduleViewPagerAdapter(getContext(), 1));

        //Top navigation for stages of the festival
        TabLayout tabLayout = rootView.findViewById(R.id.top_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Inflate the layout for this fragment
        return rootView;
    }
}


