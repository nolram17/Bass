package com.mj_tech.marlon.bass.data;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.widget.SeekBar;
import android.widget.Toast;

import com.mj_tech.marlon.bass.data.Intefaces.IMusicPlayer;
import com.mj_tech.marlon.bass.models.Song;
import com.mj_tech.marlon.bass.utility.SongHolder;
import com.mj_tech.marlon.bass.utility.Utility;

import java.io.IOException;
import java.util.List;


public class MusicService extends Service implements IMusicPlayer, SeekBar.OnSeekBarChangeListener,MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayer;
   // private MusicRepository repository;
    private final IBinder localBinder= new MusicBinder();
    public List<Song> playlist;
    private int currentIndex;
    //for seekbar
    boolean wasPlaying;

    @Override
    public IBinder onBind(Intent intent) {
        this.mediaPlayer= new MediaPlayer();
        return localBinder;
    }

    @Override
    public void play_pause() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else {
            mediaPlayer.start();
        }
    }

    @Override
    public void next() {
        currentIndex++;
        prepare(playlist.get(currentIndex));
    }

    @Override
    public void back() {
        currentIndex--;
        prepare(playlist.get(currentIndex));

    }

    @Override
    public boolean shuffle() {
        return false;
    }

    @Override
    public void setPlaylist(List<Song> playlist,int index) {
        this.playlist= playlist;
        this.currentIndex=index;
        prepare(playlist.get(index));
        SongHolder.seekBar.setOnSeekBarChangeListener(this);
        new SeekbarTask().execute();
    }

    private void prepare(Song song){

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(song.path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SongHolder.mapSong(song);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        String time= Utility.GetTime(mediaPlayer.getCurrentPosition());
        mediaPlayer.seekTo(progress);
        SongHolder.elapsed.setText(time);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
         wasPlaying=mediaPlayer.isPlaying();
         mediaPlayer.pause();

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(wasPlaying){
            mediaPlayer.start();
        }
    }

    private  void updateSeekbar(){
        SongHolder.seekBar.setProgress(mediaPlayer.getCurrentPosition());
        SongHolder.elapsed.setText(Utility.GetTime(mediaPlayer.getCurrentPosition()));
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mediaPlayer.getCurrentPosition()>0){
            next();
        }
    }


    public class SeekbarTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            while (!this.isCancelled()){
                publishProgress();
                try{
                    Thread.sleep(1000);
                }
                catch (InterruptedException e){

                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
             updateSeekbar();
        }
    }


    public class MusicBinder extends Binder {
        public MusicService getService(){

            return  MusicService.this;
        }

    }
}
