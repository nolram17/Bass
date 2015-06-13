package com.mj_tech.marlon.bass.data.Intefaces;

import com.mj_tech.marlon.bass.models.Song;

import java.util.List;

/**
 * Created by Marlon on 6/9/2015.
 */
public interface IMusicPlayer {


    public  void play_pause();
    public  void next();
    public  void back();
    public  boolean shuffle();
    void setPlaylist(List<Song> playlist, int index);

}
