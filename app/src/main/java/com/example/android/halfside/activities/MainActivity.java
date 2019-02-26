package com.example.android.halfside.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.halfside.R;
import com.example.android.halfside.fragments.LineupFragment;
import com.example.android.halfside.fragments.ScheduleFragment;
import com.example.android.halfside.models.ArtistUrls;
import com.example.android.halfside.models.PerformingArtist;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Generate a random list of artists and push them to Firebase database
         * Should be commented out after first use
         * TODO DELETE this method
         **/
        //List<PerformingArtist> performingArtistList = generateArtistsList();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedFragment(new LineupFragment());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_lineup) {
            fragment = new LineupFragment();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_schedule) {
            fragment = new ScheduleFragment();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_food_drinks) {

        } else if (id == R.id.nav_buy_tickets) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Loads the specified fragment to the frame
     *
     * @param fragment
     */
    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    /*
     * Helper method to generate a list of 18 artists (3 days * 6 artists per day)
     * TODO DELETE this method
     */
    private List<PerformingArtist> generateArtistsList() {
        List<PerformingArtist> performingArtistList = new ArrayList<>();

        PerformingArtist currentArtist;

        //All artists will use the same urls
        ArtistUrls artistUrls = new ArtistUrls(
                "generic_photo",
                "https://www.bugmafiaoficial.ro/",
                "https://www.facebook.com/bugmafia/",
                "https://www.youtube.com/channel/UCJvN0Z19jneaLfztVoO0ZGg");

        int counterArtist = 1;

        for (int day = 1; day <= 3; day++) {
            //Performing start hour for every day

            for (int stage = 1; stage <= 3; stage++) {
                int timeOfPerforming = 10;

                for (int i = 1; i <= 10; i++) {
                    currentArtist = new PerformingArtist();
                    currentArtist.setArtistName("Artist " + String.valueOf(counterArtist++));
                    currentArtist.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
                            "ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                            "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint " +
                            "occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
                    currentArtist.setDayOfPerforming(day);

                    //Every artist will perform for 1 hour
                    currentArtist.setTimeOfPerforming(String.valueOf(timeOfPerforming) + ":00");
                    timeOfPerforming = timeOfPerforming + 1;

                    currentArtist.setStagePerforming(stage);

                    /**
                     *  For simplification all artists will have the same
                     *  photo, facebook, web page & Youtube account
                     */
                    currentArtist.setArtistUrls(artistUrls);

                    performingArtistList.add(currentArtist);

                    //Save the artist to Firebase realtime database
                    FirebaseDatabase artistsFirebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference artistsDatabaseReference = artistsFirebaseDatabase.getReference("artists");
                    //save the artist at path "artists/day-*/stage-* (ex. "artist/day-1/stage-1
                    artistsDatabaseReference.child("day-" + String.valueOf(day)).child("stage-" + String.valueOf(stage)).push().setValue(currentArtist);
                }
            }
        }
        return performingArtistList;
    }
}
