package com.example.android.halfside.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.halfside.R;
import com.example.android.halfside.models.PerformingArtist;
import com.google.gson.Gson;

public class ArtistDetailsActivity extends AppCompatActivity {

    private ImageView artistPhoto;
    private TextView artistName;
    private TextView datePerforming;
    private TextView stagePerforming;
    private TextView addToMyLineup;
    //TODO declare button images for external links
    private TextView artistDescription;

    private PerformingArtist performingArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);

        artistPhoto = findViewById(R.id.artist_photo);
        artistName = findViewById(R.id.artist_name);
        datePerforming = findViewById(R.id.date_performing);
        stagePerforming = findViewById(R.id.stage_performing);
        addToMyLineup = findViewById(R.id.add_to_my_lineup_btn);
        artistDescription = findViewById(R.id.artist_description);

        String jsonArtist = getIntent().getStringExtra("performingArtist");
        Gson gson = new Gson();
        PerformingArtist performingArtist = gson.fromJson(jsonArtist, PerformingArtist.class);

        artistPhoto.setImageResource(R.drawable.generic_photo);
        artistName.setText(performingArtist.getArtistName());
        datePerforming.setText(String.valueOf(performingArtist.getDayOfPerforming()));
        stagePerforming.setText(String.valueOf(performingArtist.getStagePerforming()));
        artistDescription.setText(performingArtist.getDescription());
    }
}
