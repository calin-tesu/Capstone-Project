package com.example.android.halfside.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
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

public class ArtistDetailsActivity extends AppCompatActivity {

    private PerformingArtist performingArtist;

    private ImageView artistPhoto;
    private TextView artistName;
    private TextView datePerforming;
    private TextView stagePerforming;
    private TextView addToMyLineup;
    //TODO declare button images for external links
    private TextView artistDescription;

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
        addToMyLineup = findViewById(R.id.add_to_my_lineup_btn);
        artistDescription = findViewById(R.id.artist_description);

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

    //Download the artist photo from Firebase Storage
    public class DownloadArtistPhoto extends AsyncTask<URL, Void, Bitmap> {

        URL downloadUrl;
        Bitmap bitmap;

        DownloadArtistPhoto(URL url) {
            this.downloadUrl = url;
        }

        @Override
        protected Bitmap doInBackground(URL... urls) {
            //String url = downloadUrl;

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
            artistPhoto.setImageBitmap(bitmap);
            artistName.setText(performingArtist.getArtistName());
            datePerforming.setText(String.valueOf(performingArtist.getDayOfPerforming()));
            stagePerforming.setText(String.valueOf(performingArtist.getStagePerforming()));
            artistDescription.setText(performingArtist.getDescription());
        }
    }
}
