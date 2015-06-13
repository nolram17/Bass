package com.mj_tech.marlon.bass.models;

/**
 * Created by Marlon on 6/7/2015.
 */
public class Album {

    public String title;
    public Artist artist;
    public String artPath;
    public long albumId;

    public Album(){

        this.artist=new Artist();
    }
}
