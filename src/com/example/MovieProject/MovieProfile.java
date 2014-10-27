package com.example.MovieProject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Gareth on 16/10/2014.
 */
public class MovieProfile extends Fragment {

    private Movie movie;
    private TextView title;
    private TextView year;
    private TextView rating;
    private TextView criticScore;
    private TextView runTime;
    private TextView synopsis;

    private ImageView poster;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movieprofile, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        movie = MovieListFragment.getMovie(MyActivity.getMovieClickedPosition());

        title = (TextView) getActivity().findViewById(R.id.title);
        year = (TextView) getActivity().findViewById(R.id.year);
        poster = (ImageView) getActivity().findViewById(R.id.poster);
        rating = (TextView) getActivity().findViewById(R.id.rating);
        criticScore = (TextView) getActivity().findViewById(R.id.critic);
        runTime = (TextView) getActivity().findViewById(R.id.runtime);
        synopsis = (TextView) getActivity().findViewById(R.id.synopsis);

        title.setText(movie.getTitle());
        year.setText(movie.getYear());
        poster.setImageBitmap(movie.getImage());
        rating.setText(movie.getRating());
        criticScore.setText(movie.getCriticScore());
        runTime.setText(movie.getRunTime());
        synopsis.setText(movie.getSynopsis());




    }
}