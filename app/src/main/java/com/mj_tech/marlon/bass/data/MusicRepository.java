package com.mj_tech.marlon.bass.data;

import android.content.ContentResolver;
import android.database.Cursor;

import com.mj_tech.marlon.bass.data.Intefaces.IMusicData;
import com.mj_tech.marlon.bass.models.Album;
import com.mj_tech.marlon.bass.models.Artist;
import com.mj_tech.marlon.bass.models.Song;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.CursorJoiner;
import android.provider.MediaStore.Audio;
import android.util.Log;
import android.widget.SeekBar;

/**
 * Created by Marlon on 6/7/2015.
 */
public class MusicRepository implements IMusicData {
    ContentResolver contentResolver;
    HashMap<Long,String> albumCovers;

    public  MusicRepository(ContentResolver contentResolver){
        this.contentResolver=contentResolver;
        albumCovers= this.GetArt();
    }

    @Override
    public List<Song> GetAllSongs() {
        final Cursor cursor= contentResolver
                .query(Audio.Media.EXTERNAL_CONTENT_URI,
                        null,
                        null,
                        null,
                        Audio.Media.TITLE);
        List<Song> results= new ArrayList<>();


        while (cursor.moveToNext()){
            results.add(new Song(){{
                sondId= cursor.getLong(cursor.getColumnIndex(Audio.Media._ID));
                title= cursor.getString(cursor.getColumnIndex(Audio.Media.TITLE));
                artist.name=cursor.getString(cursor.getColumnIndex(Audio.Media.ARTIST));
                artist.artistId=cursor.getLong(cursor.getColumnIndex(Audio.Media.ARTIST_ID));
                album.albumId= cursor.getLong(cursor.getColumnIndex(Audio.Media.ALBUM_ID));
                album.artPath= albumCovers.get(cursor.getLong(cursor.getColumnIndex(Audio.Media.ALBUM_ID)));
                album.title= cursor.getString(cursor.getColumnIndex(Audio.Media.ALBUM));
                duration=cursor.getLong(cursor.getColumnIndex(Audio.Media.DURATION));
                path=cursor.getString(cursor.getColumnIndex(Audio.Media.DATA));
            }});
        }
        return results;
    }

    @Override
    public List<Song> GetSongsByArtist(long artistId) {
        String whereClause= String.format("%s=?",Audio.Media.ARTIST_ID);
        String[] whereArgs = new String[] {
              Long.toString(artistId)
        };
        final Cursor cursor= contentResolver
                .query(Audio.Media.EXTERNAL_CONTENT_URI,
                        null,
                        whereClause,
                        whereArgs,
                        Audio.Media.TITLE);
        List<Song> results= new ArrayList<>();

        while (cursor.moveToNext()){
            results.add(new Song(){{
                sondId= cursor.getLong(cursor.getColumnIndex(Audio.Media._ID));
                title= cursor.getString(cursor.getColumnIndex(Audio.Media.TITLE));
                artist.name=cursor.getString(cursor.getColumnIndex(Audio.Media.ARTIST));
                artist.artistId=cursor.getLong(cursor.getColumnIndex(Audio.Media.ARTIST_ID));
                album.albumId= cursor.getLong(cursor.getColumnIndex(Audio.Media.ALBUM_ID));
                album.title= cursor.getString(cursor.getColumnIndex(Audio.Media.ALBUM));
                duration=cursor.getLong(cursor.getColumnIndex(Audio.Media.DURATION));
                path=cursor.getString(cursor.getColumnIndex(Audio.Media.DATA));
            }});

        }

        return results;
    }

    @Override
    public List<Song> GetSongsByAlbum(long albumId) {
        String whereClause= String.format("%s=?",Audio.Media.ALBUM_ID);
        String[] whereArgs = new String[] {
                Long.toString(albumId)
        };
        final Cursor cursor= contentResolver
                .query(Audio.Media.EXTERNAL_CONTENT_URI,
                        null,
                        whereClause,
                        whereArgs,
                        Audio.Media.TITLE);
        List<Song> results= new ArrayList<>();

        while (cursor.moveToNext()){
            results.add(new Song(){{
                sondId= cursor.getLong(cursor.getColumnIndex(Audio.Media._ID));
                title= cursor.getString(cursor.getColumnIndex(Audio.Media.TITLE));
                artist.name=cursor.getString(cursor.getColumnIndex(Audio.Media.ARTIST));
                artist.artistId=cursor.getLong(cursor.getColumnIndex(Audio.Media.ARTIST_ID));
                album.albumId= cursor.getLong(cursor.getColumnIndex(Audio.Media.ALBUM_ID));
                album.artPath= albumCovers.get(cursor.getLong(cursor.getColumnIndex(Audio.Media.ALBUM_ID)));
                album.title= cursor.getString(cursor.getColumnIndex(Audio.Media.ALBUM));
                duration=cursor.getLong(cursor.getColumnIndex(Audio.Media.DURATION));
                path=cursor.getString(cursor.getColumnIndex(Audio.Media.DATA));
            }});

        }

        return results;
    }

    @Override
    public List<Album> GetAllAlbums() {
        final Cursor cursor= contentResolver
                .query(Audio.Albums.EXTERNAL_CONTENT_URI,
                        null,
                        null,
                        null,
                        Audio.Albums.ALBUM);
        List<Album> results= new ArrayList<>();

        while (cursor.moveToNext()){
            results.add(new Album(){{
                albumId= cursor.getLong(cursor.getColumnIndex(Audio.Albums._ID));
                title= cursor.getString(cursor.getColumnIndex(Audio.Albums.ALBUM));
                artPath= cursor.getString(cursor.getColumnIndex(Audio.Albums.ALBUM_ART));
                artist.name=cursor.getString(cursor.getColumnIndex(Audio.Albums.ARTIST));
            }});

        }

        return results;
    }

    @Override
    public List<Album> GetAlbumsByArtist(long artistId) {
        String whereClause= String.format("%s=?",Audio.Media.ARTIST_ID);
        String[] whereArgs = new String[] {
                Long.toString(artistId)
        };
        final Cursor cursor= contentResolver
                .query(Audio.Albums.EXTERNAL_CONTENT_URI,
                        null,
                        whereClause,
                        whereArgs,
                        Audio.Albums.ALBUM);
        List<Album> results= new ArrayList<>();

        while (cursor.moveToNext()){
            results.add(new Album(){{
                albumId= cursor.getLong(cursor.getColumnIndex(Audio.Albums._ID));
                title= cursor.getString(cursor.getColumnIndex(Audio.Albums.ALBUM));
                artPath= cursor.getString(cursor.getColumnIndex(Audio.Albums.ALBUM_ART));
                artist.name=cursor.getString(cursor.getColumnIndex(Audio.Albums.ARTIST));
            }});

        }

        return results;
    }


    private HashMap<Long,String> GetArt() {
       HashMap<Long,String>paths= new HashMap<>();
        Cursor cursor= contentResolver
                .query(Audio.Albums.EXTERNAL_CONTENT_URI,
                        new String[]{Audio.Albums._ID,Audio.Albums.ALBUM_ART},
                        null,
                        null,
                        null);
        while (cursor.moveToNext()){
            Long key= cursor.getLong(0);
            String value= cursor.getString(1);
            paths.put(key,value);
        }
        return paths;
    }

    @Override
    public List<Artist> GetAllArtist() {
        final Cursor cursor= contentResolver
                .query(Audio.Artists.EXTERNAL_CONTENT_URI,
                        null,
                        null,
                        null,
                        Audio.Artists.ARTIST);
        List<Artist> results= new ArrayList<>();

        while (cursor.moveToNext()){
            results.add(new Artist(){{
                artistId= cursor.getLong(cursor.getColumnIndex(Audio.Artists._ID));
                name= cursor.getString(cursor.getColumnIndex(Audio.Artists.ARTIST));




            }});
        }
        return results;
    }
}
