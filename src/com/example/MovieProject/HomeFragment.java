package com.example.MovieProject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksander on 17/10/2014.
 */
public class HomeFragment extends Fragment {

    int movieAmount = 0;
    static List<Movie> movieList = new ArrayList<Movie>();
    static movieImageAdapter adapter;
    boolean ifRun = false;
    MovieListFragment.onListSelected callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.newmovies, container, false);

        String APIkey = "8h6mbnfwjb2qux8et6k8uq9a";
        String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=" + APIkey;

        GridView gridView = (GridView) v.findViewById(R.id.popularMovieGrid);
        adapter = new movieImageAdapter(getActivity(),movieList);
        gridView.setAdapter(adapter);

        if(!ifRun)
        {
            new APIConnect().execute(url);
            ifRun = true;
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movieList.get(position);
                callback.onListElementSelected(movie);
            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try{
            callback = (MovieListFragment.onListSelected) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + "implement on list selected");
        }
    }



    public static void AddAMovie(Movie movie){
        movieList.add(movie);
        //update adapter
        Runnable listing = new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged(); // can't update this by itself (needs runnable around it )
            }
        };
    }
}