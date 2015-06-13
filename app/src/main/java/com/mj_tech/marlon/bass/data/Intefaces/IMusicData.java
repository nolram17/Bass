package com.mj_tech.marlon.bass.data.Intefaces;

import android.database.Cursor;

import com.mj_tech.marlon.bass.models.Album;
import com.mj_tech.marlon.bass.models.Artist;
import com.mj_tech.marlon.bass.models.Song;

import java.util.List;

/**
 * Created by Marlon on 6/7/2015.
 */
public interface IMusicData {

    List<Song> GetAllSongs();
    List<Song>GetSongsByArtist(long artistId);
    List<Song>GetSongsByAlbum(long albumId);
    List<Album> GetAllAlbums();
    List<Album>GetAlbumsByArtist(long artistId);
    List<Artist> GetAllArtist();

}
