package com.example.MovieProject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

/**
 * Created by Aleksander on 18/10/2014.
 */
public class MovieListFragment extends Fragment {
    private static ArrayList<Movie> Movies = new ArrayList<Movie>();
    private static MoviesAdapter adapter;
    private ListView MovieList;
    boolean ifRun = false;
    onListSelected callback;

    public static Movie getMovie(int position) {
        return Movies.get(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movielist, container, false);
        String APIkey = "8h6mbnfwjb2qux8et6k8uq9a";
        String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=" + APIkey;

        MovieList = (ListView) v.findViewById(R.id.movieListYear);

        adapter = new MoviesAdapter(getActivity(), Movies);
        MovieList.setAdapter(adapter);

        if(!ifRun)
        {
            new APIConnect().execute(url);
            ifRun = true;
        }

        MovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movie = Movies.get(position);
                callback.onListElementSelected(position);

            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try{
            callback = (onListSelected) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + "implement on list selected");
        }
    }

    public interface  onListSelected
    {
        public void onListElementSelected(int position);
    }

    public static void AddAMovie(Movie movie){
        Movies.add(movie);
        //update adapter
        Runnable listing = new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged(); // can't update this by itself (needs runnable around it )
            }
        };
    }
}