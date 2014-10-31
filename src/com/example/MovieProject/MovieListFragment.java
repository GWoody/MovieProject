package com.example.MovieProject;

import android.app.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Aleksander on 18/10/2014.
 */
public class MovieListFragment extends Fragment {
    private static ArrayList<Movie> movies = new ArrayList<Movie>();
    private static MoviesAdapter adapter;
    private ListView movieList;
    onListSelected callback;
    boolean whichList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movielist, container, false);

        getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        Bundle bundle = this.getArguments();

        whichList = bundle.getBoolean("whichList");

        movieList = (ListView)v.findViewById(R.id.movieList);

        movies = MyActivity.helper.getAllMoviesinList(whichList); //false is seen, true is toWatch

        adapter = new MoviesAdapter(getActivity(), movies);

        movieList.setAdapter(adapter);

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movies.get(position);
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