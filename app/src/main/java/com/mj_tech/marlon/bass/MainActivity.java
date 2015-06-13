package com.mj_tech.marlon.bass;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import com.mj_tech.marlon.bass.data.Intefaces.IMusicData;
import com.mj_tech.marlon.bass.data.Intefaces.IMusicReciever;
import com.mj_tech.marlon.bass.data.MusicRepository;
import com.mj_tech.marlon.bass.data.MusicService.MusicBinder;
import com.mj_tech.marlon.bass.data.MusicService;
import com.mj_tech.marlon.bass.fragments.AlbumFragment;
import com.mj_tech.marlon.bass.fragments.ArtistFragment;
import com.mj_tech.marlon.bass.fragments.SongFragment;
import com.mj_tech.marlon.bass.models.Album;
import com.mj_tech.marlon.bass.models.Artist;
import com.mj_tech.marlon.bass.models.Song;
import com.mj_tech.marlon.bass.utility.Page;
import com.mj_tech.marlon.bass.utility.SelectionAdapter;
import com.mj_tech.marlon.bass.utility.SongHolder;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements OnClickListener ,IMusicReciever {
    public MusicService musicService;
    public IMusicData musicRepository;

    private Intent musicIntent;
    private boolean serviceBound;
    Button testButton ,play,next,back, music;
    ViewFlipper viewSwitcher;
    ViewPager selection;
    FrameLayout target;
    AlbumFragment albumFragment;
    ArtistFragment artistFragment;
    SongFragment songFragment;
    SelectionAdapter adapter;


    private ServiceConnection connection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder= (MusicBinder)service;
            musicService= binder.getService();
            serviceBound=true;
            Toast.makeText(getApplication(),"Connected",Toast.LENGTH_LONG).show();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
           serviceBound=false;
           Toast.makeText(getApplication(),"Nope",Toast.LENGTH_LONG).show();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SongHolder.art= (ImageView)findViewById(R.id.player_art);
        SongHolder.album= (TextView)findViewById(R.id.player_album);
        SongHolder.artist=(TextView)findViewById(R.id.player_artist);
        SongHolder.title= (TextView)findViewById(R.id.player_title);
        SongHolder.seekBar=(SeekBar)findViewById(R.id.seekBar);
        SongHolder.elapsed= (TextView)findViewById(R.id.elapsed);
        SongHolder.duration= (TextView)findViewById(R.id.duration);
        SongHolder.contentResolver=getContentResolver();

        play= (Button)findViewById(R.id.play_pause);
        next= (Button)findViewById(R.id.next);
        back=(Button)findViewById(R.id.back);
        music=(Button)findViewById(R.id.open_playlist);


        testButton= (Button)findViewById(R.id.change);
        testButton.setOnClickListener(this);


        viewSwitcher= (ViewFlipper)findViewById(R.id.baseSwitch);
        selection= (ViewPager)findViewById(R.id.selection);


        target= (FrameLayout)findViewById(R.id.target);
        albumFragment= new AlbumFragment();
        songFragment= new SongFragment();
        artistFragment= new ArtistFragment();

        musicRepository= new MusicRepository(getContentResolver());

        List<Fragment>fragments= new ArrayList<>();
        fragments.add(artistFragment);
        fragments.add(albumFragment);
        fragments.add(songFragment);
        adapter= new SelectionAdapter(getFragmentManager(),fragments);
        selection.setAdapter(adapter);

        musicIntent= new Intent(this,MusicService.class);
        this.bindService(musicIntent,connection,BIND_AUTO_CREATE);



        play.setOnClickListener(this);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
        music.setOnClickListener(this);



    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Album> albumList= musicRepository.GetAllAlbums();
        List <Artist>artistList= musicRepository.GetAllArtist();
        List<Song>songList= musicRepository.GetAllSongs();
        artistFragment.setList(artistList);
        albumFragment.setList(albumList);
        songFragment.setList(songList);


        if(serviceBound){
        }
    }
    //


    @Override
    public void onBackPressed() {

        if(viewSwitcher.getDisplayedChild()!= Page.Selection.getValue()){
            viewSwitcher.setDisplayedChild(Page.Selection.getValue());
        }else{

            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {

        stopService(musicIntent);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.play_pause:
                musicService.play_pause();
                break;
            case  R.id.next:
                musicService.next();
                break;
            case R.id.back:
                musicService.back();
                break;
            case R.id.open_playlist:
                viewSwitcher.setDisplayedChild(Page.Selection.getValue());
                break;
            default:

                break;
        }


    }



    @Override
    public List<Song> GetSongsByArtist(long artistId) {
        List songs= musicRepository.GetSongsByArtist(artistId);
        songFragment.setList(songs);
        return null;
    }

    @Override
    public List<Song> GetSongsByAlbum(long albumId) {

        List songs= musicRepository.GetSongsByAlbum(albumId);
        SongFragment fragment= new SongFragment();
        fragment.setList(songs);
        getFragmentManager().beginTransaction().replace(R.id.target,fragment).commit();

        viewSwitcher.setDisplayedChild(Page.SubSelection.getValue());

        return null;
    }



    @Override
    public List<Album> GetAlbumsByArtist(long artistId) {
        List albums= musicRepository.GetAlbumsByArtist(artistId);
        AlbumFragment fragment =  new AlbumFragment();
        fragment.setList(albums);
        getFragmentManager().beginTransaction().replace(R.id.target,fragment).commit();

        viewSwitcher.setDisplayedChild(Page.SubSelection.getValue());

        return null;
    }

    @Override
    public void setPlaylist(List<Song>playlist,int index) {

        viewSwitcher.setDisplayedChild(Page.Player.getValue());

        musicService.setPlaylist(playlist,index);
    }


}
