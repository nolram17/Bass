package com.mj_tech.marlon.bass.utility;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mj_tech.marlon.bass.models.Song;

/**
 * Created by Marlon on 6/7/2015.
 */
public abstract class SongHolder {

    public  static ImageView art;
    public  static TextView artist,album,title,elapsed,duration;
    public static SeekBar seekBar;
    public static ContentResolver contentResolver;

    public static void  mapSong(Song song){
        if (song!=null){


            title.setText(song.title);
            artist.setText(song.artist.name);
            album.setText(song.album.title);
            elapsed.setText(String.format("%02d:%02d",0,0));
            duration.setText(Utility.GetTime(song.duration));
            seekBar.setProgress(0);
            seekBar.setMax((int)song.duration);


            BitmapDrawable img=new BitmapDrawable(song.album.artPath);

            art.setImageDrawable(img);

        }
    }





}
