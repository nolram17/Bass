package com.mj_tech.marlon.bass.data.Intefaces;

import com.mj_tech.marlon.bass.models.Album;
import com.mj_tech.marlon.bass.models.Artist;
import com.mj_tech.marlon.bass.models.Song;

import java.util.List;

/**
 * Created by Marlon on 6/10/2015.
 */
public interface IMusicReciever {


    List<Song>GetSongsByArtist(long artistId);
    List<Song>GetSongsByAlbum(long albumId);
    List<Album>GetAlbumsByArtist(long artistId);
    void setPlaylist(List<Song>playlist, int index);


}
