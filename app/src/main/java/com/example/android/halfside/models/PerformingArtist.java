package com.example.android.halfside.models;

/**
 * Created by Calin Tesu on 2/2/2019.
 */
public class PerformingArtist {

    private String name;
    private String description;

    //Represents the first day (1) or the second day(2) etc, of the festival
    private int dayOfPerforming;

    //Time format HH:mm
    private String timeOfPerforming;

    private int stagePerforming;
    private ArtistUrls artistUrls;

    public PerformingArtist(String name, String description, int dayOfPerforming, String timeOfPerforming, int stagePerforming, ArtistUrls artistUrls) {
        this.name = name;
        this.description = description;
        this.dayOfPerforming = dayOfPerforming;
        this.timeOfPerforming = timeOfPerforming;
        this.stagePerforming = stagePerforming;
        this.artistUrls = artistUrls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDayOfPerforming() {
        return dayOfPerforming;
    }

    public void setDayOfPerforming(int dayOfPerforming) {
        this.dayOfPerforming = dayOfPerforming;
    }

    public String getTimeOfPerforming() {
        return timeOfPerforming;
    }

    public void setTimeOfPerforming(String timeOfPerforming) {
        this.timeOfPerforming = timeOfPerforming;
    }

    public int getStagePerforming() {
        return stagePerforming;
    }

    public void setStagePerforming(int stagePerforming) {
        this.stagePerforming = stagePerforming;
    }

    public ArtistUrls getArtistUrls() {
        return artistUrls;
    }

    public void setArtistUrls(ArtistUrls artistUrls) {
        this.artistUrls = artistUrls;
    }
}
