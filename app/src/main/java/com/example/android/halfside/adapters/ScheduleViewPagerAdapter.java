package com.example.android.halfside.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.halfside.R;

/**
 * Created by Calin Tesu on 2/21/2019.
 */
public class ScheduleViewPagerAdapter extends PagerAdapter {

    private static final int NUMBER_OF_STAGES = 3;

    private Context context;

    private int day;

    private TextView stageOfGig;
    private TextView dayOfGig;

    private BottomNavigationView bottomNavigationView;

    public ScheduleViewPagerAdapter(Context context) {
        this.context = context;
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
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.schedule_view_pager, container, false);

        stageOfGig = viewGroup.findViewById(R.id.stage_of_gig);
        dayOfGig = viewGroup.findViewById(R.id.day_of_gig);

        //Bottom navigation for day of the festival
        bottomNavigationView = viewGroup.findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(0);

        //TODO replace setText with a method that query Firebase database
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.day_1:
                                day = 1;
                                dayOfGig.setText("DAY 1");
                                return true;
                            case R.id.day_2:
                                day = 2;
                                dayOfGig.setText("DAY 2");
                                return true;
                            case R.id.day_3:
                                day = 3;
                                dayOfGig.setText("DAY 3");
                                return true;
                        }
                        return false;
                    }
                }
        );

        //dayOfGig.setText("DAY " + String.valueOf(day));
        stageOfGig.setText("STAGE " + String.valueOf(position + 1));

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
}
