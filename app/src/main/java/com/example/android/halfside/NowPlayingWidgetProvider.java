package com.example.android.halfside;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.example.android.halfside.models.PerformingArtist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class NowPlayingWidgetProvider extends AppWidgetProvider {

    static String artistName;

    // Firebase instance variables
    private static FirebaseDatabase artistsFirebaseDatabase;
    private static DatabaseReference artistsDatabaseReference;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //Initialize Firebase components
        artistsFirebaseDatabase = FirebaseDatabase.getInstance();
        artistsDatabaseReference = artistsFirebaseDatabase.getReference("artists");

        CharSequence widgetText = context.getString(R.string.appwidget_title);
        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.now_playing_widget_provider);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH");
        //Get the current time and date and format it as above
        String currentDay = dateFormat.format(Calendar.getInstance().getTime());
        String currentTime = timeFormat.format(Calendar.getInstance().getTime());
        String artistName;


        switch (currentDay) {
            case Constants.FIRST_DAY_OF_FESTIVAL:
                artistName = queryFirebaseDatabase("day-1", currentTime + ":00");
                views.setTextViewText(R.id.artist_name, artistName);
                break;
            case Constants.SECOND_DAY_OF_FESTIVAL:
                artistName = queryFirebaseDatabase("day-2", currentTime + ":00");
                views.setTextViewText(R.id.artist_name, artistName);
                break;
            case Constants.THIRD_DAY_OF_FESTIVAL:
                artistName = queryFirebaseDatabase("day-3", currentTime + ":00");
                views.setTextViewText(R.id.artist_name, artistName);
                break;
            default:
                views.setTextViewText(R.id.artist_name, "NO SHOW!!");


        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static String queryFirebaseDatabase(String day, String time) {

        Query myQuery = artistsDatabaseReference
                .child(day)
                .child("stage-1")
                .orderByChild("timeOfPerforming")
                .equalTo(time);

        myQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    PerformingArtist performingArtist = childSnapshot.getValue(PerformingArtist.class);
                    artistName = performingArtist.getArtistName();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO do something
            }
        });

        //If no artist was in Firebase database for the current time the #artistName will be null
        if (artistName != null) {
            return artistName;
        } else {
            return "No one playing!";
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

