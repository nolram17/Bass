package com.mj_tech.marlon.bass.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.StackView;
import android.widget.TextView;
import android.widget.Toast;

import com.mj_tech.marlon.bass.MainActivity;
import com.mj_tech.marlon.bass.R;
import com.mj_tech.marlon.bass.data.Intefaces.IMusicData;
import com.mj_tech.marlon.bass.data.Intefaces.IMusicReciever;
import com.mj_tech.marlon.bass.models.Album;

import java.util.List;

/**
 * Created by Marlon on 6/7/2015.
 */
public class AlbumFragment extends MusicFragment {

    private List<Album> albums;
    private GridView albumGrid;
    private View view;
    private AlbumAdapter adapter;
    private IMusicReciever controller;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_album,container,false);
        albumGrid = (GridView)view.findViewById(R.id.albumGrid);
        adapter=new AlbumAdapter(getActivity(),R.layout.item_album,albums);
        albumGrid.setAdapter(adapter);
        albumGrid.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void setList(List items) {
        try {
            albums=items;

        }catch (Exception e){

            Toast.makeText(getActivity(),"list failed to populate",Toast.LENGTH_LONG);
        }

        if(adapter!=null){

            adapter.clear();
            adapter.addAll(albums);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

            controller=(IMusicReciever)activity;
        }
        catch (ClassCastException e){


        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Album album= adapter.getItem(position);
            controller.GetSongsByAlbum(album.albumId);
    }


    class AlbumAdapter extends ArrayAdapter<Album>
    {
        private  Context context;
        private  int resource;

        private  class ViewHolder{
            TextView title;
            RelativeLayout art;
            Drawable img;
            ImageView imageView;


        }

        public AlbumAdapter(Context context, int resource, List<Album> albums) {
            super(context, resource, albums);
            this.context=context;
            this.resource=resource;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Album album= getItem(position);
            ViewHolder viewHolder;

            if(convertView==null){
                viewHolder=new ViewHolder();
                LayoutInflater inflater= getActivity().getLayoutInflater();
                convertView= inflater.inflate(resource,parent,false);
                viewHolder.art= (RelativeLayout)convertView.findViewById(R.id.album_art);
                viewHolder.title= (TextView)convertView.findViewById(R.id.album_name);
                viewHolder.imageView=(ImageView)convertView.findViewById(R.id.album_image);
                convertView.setTag(viewHolder);
            }else {

                viewHolder= (ViewHolder)convertView.getTag();
            }

            if(album!=null){
                BitmapDrawable img=new BitmapDrawable(album.artPath);

                viewHolder.title.setText(album.title);
               // viewHolder.art.setBackground(img);
                viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                viewHolder.imageView.setImageDrawable(img);
            }


            return convertView;
        }
    }


}
