package com.example.android.halfside.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.halfside.R;
import com.example.android.halfside.models.PerformingArtist;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ArtistDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private PerformingArtist performingArtist;

    private ImageView artistPhoto;
    private TextView artistName;
    private TextView datePerforming;
    private TextView stagePerforming;
    private TextView artistDescription;

    private ImageView webpageBtn;
    private ImageView facebookBtn;
    private ImageView youtubeBtn;
    private TextView addToMyLineup;

    private ProgressBar loadingIndicator;


    // Firebase instance variables
    private FirebaseStorage artistsPhotoFirebaseStorage;
    private StorageReference artistPhotoStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);

        artistPhoto = findViewById(R.id.artist_photo);
        artistName = findViewById(R.id.artist_name);
        datePerforming = findViewById(R.id.date_performing);
        stagePerforming = findViewById(R.id.stage_performing);
        artistDescription = findViewById(R.id.artist_description);

        webpageBtn = findViewById(R.id.webpage_btn);
        facebookBtn = findViewById(R.id.facebook_btn);
        youtubeBtn = findViewById(R.id.youtube_btn);
        addToMyLineup = findViewById(R.id.add_to_my_lineup_btn);

        webpageBtn.setOnClickListener(this);
        facebookBtn.setOnClickListener(this);
        youtubeBtn.setOnClickListener(this);
        addToMyLineup.setOnClickListener(this);

        loadingIndicator = findViewById(R.id.loading_indicator);

        //Initialize Firebase components
        artistsPhotoFirebaseStorage = FirebaseStorage.getInstance();
        artistPhotoStorageReference = artistsPhotoFirebaseStorage.getReference("artists_photo");

        String jsonArtist = getIntent().getStringExtra("performingArtist");
        Gson gson = new Gson();
        performingArtist = gson.fromJson(jsonArtist, PerformingArtist.class);

        String photoFirebaseUrl = performingArtist.getArtistUrls().getPhotoFirebaseUrl() + ".jpg";

        //Get the Url of artist photo from Firebase Storage
        artistPhotoStorageReference.child(photoFirebaseUrl).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        try {
                            URL downloadUrl = new URL(uri.toString());
                            new DownloadArtistPhoto(downloadUrl).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.webpage_btn:
                lunchExternalLinks(performingArtist.getArtistUrls().getWebUrl());
                break;
            case R.id.facebook_btn:
                lunchExternalLinks(performingArtist.getArtistUrls().getFacebookUrl());

                //TODO lunch Facebook in app not webpage
                //https://stackoverflow.com/a/24547437/9209228
                //https://stackoverflow.com/a/34564284/9209228
                break;
            case R.id.youtube_btn:
                lunchExternalLinks(performingArtist.getArtistUrls().getYoutubeUrl());
                break;
            case R.id.add_to_my_lineup_btn:
                //TODO fix Add to my lineup button
        }
    }

    private void lunchExternalLinks(String artistUrl) {
        Uri uri = Uri.parse(artistUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        // Verify that the intent will resolve to an activity
        if (intent.resolveActivity(getApplication().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

   /*
   * Download the artist photo from Firebase Storage using an
   * AsyncTask to meet the requirements for the Capstone Project
   * */
    public class DownloadArtistPhoto extends AsyncTask<URL, Void, Bitmap> {

        URL downloadUrl;
        Bitmap bitmap;

        DownloadArtistPhoto(URL url) {
            this.downloadUrl = url;
        }

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           loadingIndicator.setVisibility(View.VISIBLE);
       }

       @Override
        protected Bitmap doInBackground(URL... urls) {

            try {

                  URLConnection urlConnection = downloadUrl.openConnection();
                  bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            loadingIndicator.setVisibility(View.INVISIBLE);

            artistPhoto.setImageBitmap(bitmap);
            artistName.setText(performingArtist.getArtistName());
            datePerforming.setText("Day " + String.valueOf(performingArtist.getDayOfPerforming()));
            stagePerforming.setText("Stage " + String.valueOf(performingArtist.getStagePerforming()));
            artistDescription.setText(performingArtist.getDescription());
        }
    }
}
