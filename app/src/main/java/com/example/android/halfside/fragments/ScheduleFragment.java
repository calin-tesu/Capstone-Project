package com.example.android.halfside.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.halfside.R;
import com.example.android.halfside.adapters.ScheduleViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        ViewPager viewPager = rootView.findViewById(R.id.schedule_view_pager);
        viewPager.setAdapter(new ScheduleViewPagerAdapter(getContext()));

        //Top navigation for stages of the festival
        TabLayout tabLayout = rootView.findViewById(R.id.top_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Inflate the layout for this fragment
        return rootView;
    }
}


