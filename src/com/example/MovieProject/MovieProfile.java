package com.example.MovieProject;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button toWatch;
    private Button Seen;
    private boolean whichList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movieprofile, container, false);

        title = (TextView)v.findViewById(R.id.title);
        year = (TextView)v.findViewById(R.id.year);
        poster = (ImageView)v.findViewById(R.id.poster);
        rating = (TextView)v.findViewById(R.id.rating);
        criticScore = (TextView)v.findViewById(R.id.critic);
        runTime = (TextView)v.findViewById(R.id.runtime);
        synopsis = (TextView)v.findViewById(R.id.synopsis);
        toWatch = (Button) v.findViewById(R.id.watched);
        Seen = (Button) v.findViewById(R.id.seen);

        Bundle bundle = this.getArguments();
        movie = (Movie)bundle.getParcelable("movie");

        title.setText(movie.getTitle());
        year.setText(movie.getYear());
        poster.setImageBitmap(movie.getImage());
        rating.setText("Rating: " + movie.getRating());
        criticScore.setText("Critic Score: " + movie.getCriticScore());
        runTime.setText("Minutes: " + movie.getRunTime());
        synopsis.setText(movie.getSynopsis());

        synopsis.setMovementMethod(new ScrollingMovementMethod());

        if(MyActivity.helper.checkRecordExistsToWatch(movie.getTitle(),movie.getYear()))
        {
            toWatch.setText("Delete from Wanted");
        }

        if(MyActivity.helper.checkRecordExistsWatched(movie.getTitle(),movie.getYear()))
        {
            Seen.setText("Delete from Seen");
        }



        toWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(toWatch.getText().equals("Wanted"))
                {
                    whichList = true;
                    MyActivity.helper.addMovieToList(movie,whichList);
                }


                if(toWatch.getText().equals("Delete from Wanted"))
                {
                    Boolean result = MyActivity.helper.deleteRecordFromListToWatch(movie.getTitle());
                    if(result)
                    {
                        Context context = getActivity().getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;

                        Toast.makeText(context,"Successful deletion from Wanted", duration).show();

                        toWatch.setText("Wanted");
                    }
                }
                if(MyActivity.helper.checkRecordExistsToWatch(movie.getTitle(),movie.getYear()))
                {
                    toWatch.setText("Delete from Wanted");
                }

            }
        });

        Seen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Seen.getText().equals("Seen"))
                {
                    whichList = false;
                    MyActivity.helper.addMovieToList(movie,whichList);
                }
                if(Seen.getText().equals("Delete from Seen"))
                {
                    Boolean result = MyActivity.helper.deleteRecordFromListWatched(movie.getTitle());
                    if(result)
                    {
                        Context context = getActivity().getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;

                        Toast.makeText(context,"Successful deletion from Seen", duration).show();

                        Seen.setText("Seen");

                    }
                }
                if(MyActivity.helper.checkRecordExistsWatched(movie.getTitle(), movie.getYear()))
                {
                    Seen.setText("Delete from Seen");
                }

            }
        });





        return v;
    }
}