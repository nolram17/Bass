package com.mj_tech.marlon.bass.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mj_tech.marlon.bass.R;
import com.mj_tech.marlon.bass.data.Intefaces.IMusicData;
import com.mj_tech.marlon.bass.data.Intefaces.IMusicReciever;
import com.mj_tech.marlon.bass.models.Song;

import java.util.List;

/**
 * Created by Marlon on 6/9/2015.
 */
public class SongFragment extends MusicFragment{
    ListView songList;
    List<Song> songs;
    View view;
    SongAdapter adapter;
    IMusicReciever controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_song,container,false);
        songList=(ListView)view.findViewById(R.id.songList);
        adapter= new SongAdapter(getActivity(),R.layout.item_song,songs);
        songList.setAdapter(adapter);
        songList.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void setList(List items) {
            try{
                songs= items;
            }catch (Exception e){

            }
        if(adapter!=null){

            adapter.clear();
            adapter.addAll(songs);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            controller=(IMusicReciever)activity;
        }
        catch (Exception e){

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Song song= songs.get(position);
            controller.setPlaylist(songs,position);

    }



    class SongAdapter extends ArrayAdapter<Song>{

        private  int resource;
        ViewHolder viewHolder;

        public SongAdapter(Context context, int resource, List<Song>songs ) {
            super(context, resource, songs);
            this.resource=resource;
        }
        private class ViewHolder{
            TextView title;
            TextView artist;
            TextView duration;

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Song song= getItem(position);
            if (convertView==null){
                viewHolder= new ViewHolder();
                LayoutInflater inflater= getActivity().getLayoutInflater();
                convertView= inflater.inflate(resource,parent,false);

                viewHolder.title= (TextView)convertView.findViewById(R.id.song_title);
                viewHolder.artist= (TextView)convertView.findViewById(R.id.song_artist);
                viewHolder.duration =(TextView)convertView.findViewById(R.id.duration);

                convertView.setTag(viewHolder);

            }
            else{
                viewHolder=(ViewHolder)convertView.getTag();
            }

            int seconds = (int) (song.duration / 1000) % 60 ;
            int minutes = (int) ((song.duration / (1000*60)) % 60);
            int hours   = (int) ((song.duration / (1000*60*60)) % 24);

            viewHolder.title.setText(song.title);
            viewHolder.artist.setText(song.artist.name);
            viewHolder.duration.setText(String.format("%d:%02d:%02d",hours,minutes,seconds));

            return convertView;
        }
    }
}
