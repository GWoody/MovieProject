package com.example.MovieProject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthony on 28/10/2014.
 */
public class movieImageAdapter extends ArrayAdapter<Movie>{

    private Context context;
    public movieImageAdapter(Context context, List<Movie> Movies) {
        super(context, R.layout.movielist, Movies);
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie amovie = this.getItem(position);
        ImageView imageView;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rowgrid, parent, false);
            imageView = (ImageView) convertView.findViewById(R.id.gridimage);
            convertView.setTag(imageView);
        }
        else
        {
            imageView = (ImageView) convertView.getTag();
        }

        imageView.setImageBitmap(amovie.getImage());


        return convertView;
    }
}
