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
    onListSelected callback;
    boolean isCreated = false;
    boolean whichList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movielist, container, false);

        TabHost tabs = (TabHost)v.findViewById(R.id.tabHost);
        tabs.setup();

        // Calculator
        TabHost.TabSpec yearList = tabs.newTabSpec("Year");
        //yearList.setContent(R.id.calculator);
        yearList.setIndicator("Year");
        tabs.addTab(yearList);

        // Home
        //tabs.addTab(tabs.newTabSpec("Alphabetical").setIndicator("Alphabetical"),AlphabeticalFragment.class, null);


        if(!isCreated)
        {
            MovieList = (ListView) v.findViewById(R.id.movieListYear);

            Bundle bundle = this.getArguments();
           whichList =  bundle.getBoolean("whichList");
            Movies = MyActivity.helper.getAllMoviesinList(whichList);

            adapter = new MoviesAdapter(getActivity(), Movies);
            MovieList.setAdapter(adapter);
        }
        else
        {
            adapter.notifyDataSetChanged(); // update set
        }



        MovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movie = Movies.get(position);
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
            callback = (onListSelected) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + "implement on list selected");
        }
    }

    public interface onListSelected
    {
        public void onListElementSelected(Movie movie);
    }
}