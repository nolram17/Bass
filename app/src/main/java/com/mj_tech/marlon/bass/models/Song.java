package com.mj_tech.marlon.bass.models;

/**
 * Created by Marlon on 6/7/2015.
 */
public class Song {
    public long sondId;
    public Artist artist;
    public Album album;
    public String title;
    public long duration;
    public String path;

    public Song(){

        artist = new Artist();
        album = new Album();
    }

}
