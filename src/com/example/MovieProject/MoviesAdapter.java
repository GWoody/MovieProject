package com.example.MovieProject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gareth on 27/10/2014.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    public MoviesAdapter(Context context, ArrayList<Movie> Movies) {
        super(context, R.layout.movielist, Movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Movie amovie = this.getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.moviecell, parent, false);
        }
        // Lookup view for data population
        TextView IDTitle = (TextView) convertView.findViewById(R.id.IDTitleText);
        TextView IDYear = (TextView) convertView.findViewById(R.id.IDYearText);
        ImageView IDThumbnail = (ImageView) convertView.findViewById(R.id.IDImage);

        // Populate the data into the template view using the data object
        IDTitle.setText(amovie.getTitle());
        IDYear.setText(String.valueOf(amovie.getYear()));
        IDThumbnail.setImageBitmap(amovie.getThumbnail());
        // Return the completed view to render on screen
        return convertView;
    }
}
