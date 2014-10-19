package com.example.MovieProject;

import java.util.ArrayList;

/**
 * Created by Gareth on 14/10/2014.
 */
public class UserList {

    private ArrayList<Movie> movies;

    public UserList() {
        movies = new ArrayList<Movie>();

    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public Movie getMovieAtLocation(int location)
    {
        Movie movie = movies.get(location);

        return movie;
    }
}
