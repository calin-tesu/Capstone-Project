package com.example.android.halfside.models;

/**
 * Created by Calin Tesu on 2/2/2019.
 */
public class ArtistUrls {

    private String photoFirebaseUrl;
    private String webUrl;
    private String facebookUrl;
    private String youtubeUrl;

    public ArtistUrls(String photoFirebaseUrl, String webUrl, String facebookUrl, String youtubeUrl) {
        this.photoFirebaseUrl = photoFirebaseUrl;
        this.webUrl = webUrl;
        this.facebookUrl = facebookUrl;
        this.youtubeUrl = youtubeUrl;
    }

    public String getPhotoFirebaseUrl() {
        return photoFirebaseUrl;
    }

    public void setPhotoFirebaseUrl(String photoFirebaseUrl) {
        this.photoFirebaseUrl = photoFirebaseUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }
}
