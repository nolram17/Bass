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
import android.widget.Toast;

import com.mj_tech.marlon.bass.R;
import com.mj_tech.marlon.bass.data.Intefaces.IMusicData;
import com.mj_tech.marlon.bass.data.Intefaces.IMusicReciever;
import com.mj_tech.marlon.bass.models.Artist;

import java.util.List;

/**
 * Created by Marlon on 6/9/2015.
 */
public class ArtistFragment extends MusicFragment {
    ListView artistList;
    List<Artist> artists;
    View view;
    ArtistAdapter adapter;
    IMusicReciever controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_artist,container,false);
        artistList= (ListView)view.findViewById(R.id.artistList);
        adapter= new ArtistAdapter(getActivity(), R.layout.item_artist,artists);
        artistList.setAdapter(adapter);
        artistList.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void setList(List items) {
        try {
            artists=items;

        }catch (Exception e){

            Toast.makeText(getActivity(), "list failed to populate", Toast.LENGTH_LONG);
        }

        if(adapter!=null){

            adapter.clear();
            adapter.addAll(artists);
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
       Artist artist= artists.get(position);
       controller.GetAlbumsByArtist(artist.artistId);


    }

    class  ArtistAdapter extends ArrayAdapter<Artist> {

        private  int resource;
        private ViewHolder viewHolder;
        public ArtistAdapter(Context context, int resource, List<Artist> artists) {
            super(context, resource, artists);
            this.resource=resource;
        }

        private class ViewHolder{
            TextView artistName;

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Artist artist= getItem(position);
            if(convertView==null)
            {
                viewHolder= new ViewHolder();
                LayoutInflater inflater= getActivity().getLayoutInflater();
                convertView= inflater.inflate(resource,parent,false);

                viewHolder.artistName= (TextView)convertView.findViewById(R.id.artist_name);

                convertView.setTag(viewHolder);

            }
            else{

                viewHolder= (ViewHolder)convertView.getTag();
            }
            viewHolder.artistName.setText(artist.name);

            return convertView;
        }
    }
}
