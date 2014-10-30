package com.example.MovieProject;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Aleksander on 30/10/2014.
 */
public class MovieResultsFragment extends Fragment {

    private ArrayList<Movie> searchResults;
    int currentlyDisplayedResult;
    ImageView imageView;
    TextView title;
    Button nextButton;
    Button previousButton;
    Button viewButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movieresults, container, false);

        imageView = (ImageView)v.findViewById(R.id.imageView);
        title = (TextView)v.findViewById(R.id.title);
        nextButton = (Button)v.findViewById(R.id.nextButton);
        previousButton = (Button)v.findViewById(R.id.previousButton);
        viewButton = (Button)v.findViewById(R.id.viewButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((currentlyDisplayedResult + 1) < searchResults.size()) {
                    ++currentlyDisplayedResult;
                    imageView.setImageBitmap(searchResults.get(currentlyDisplayedResult).getImage());
                    title.setText(searchResults.get(currentlyDisplayedResult).getTitle());
                }
                else {
                    currentlyDisplayedResult = 0;
                    imageView.setImageBitmap(searchResults.get(currentlyDisplayedResult).getImage());
                    title.setText(searchResults.get(currentlyDisplayedResult).getTitle());
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((currentlyDisplayedResult - 1) >= 0) {
                    --currentlyDisplayedResult;
                    imageView.setImageBitmap(searchResults.get(currentlyDisplayedResult).getImage());
                    title.setText(searchResults.get(currentlyDisplayedResult).getTitle());
                }
                else {
                    currentlyDisplayedResult = searchResults.size() - 1;
                    imageView.setImageBitmap(searchResults.get(currentlyDisplayedResult).getImage());
                    title.setText(searchResults.get(currentlyDisplayedResult).getTitle());
                }
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyActivity)getActivity()).viewMovie(searchResults.get(currentlyDisplayedResult));
            }
        });

        searchResults = new ArrayList<Movie>();

        Bundle bundle = this.getArguments();

        new DownloadSearchResultsTask().execute(bundle.getString("url"));

        return v;
    }

    private class DownloadSearchResultsTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected Void doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(params[0]);
            try {
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity httpEntity = response.getEntity();
                InputStream inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                }
                inputStream.close();

                String message = stringBuilder.toString();
                JSONReader(message);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return null;
        }


        public void JSONReader(String result) {
            try {
                JSONObject total = new JSONObject(result);
                JSONArray movies = total.getJSONArray("movies");
                for (int i = 0; i < movies.length(); i++) {
                    JSONObject movie = movies.getJSONObject(i);
                    String Title = movie.getString("title");
                    String runTime = movie.getString("runtime");
                    String synopsis = movie.getString("synopsis");
                    String rating = movie.getString("mpaa_rating");
                    JSONObject ratings = movie.getJSONObject("ratings");
                    String criticRating = ratings.getString("critics_score");
                    JSONObject posters = movie.getJSONObject("posters");
                    String Thumbnail = posters.getString("thumbnail");
                    String profilePoster = posters.getString("original");
                    String[] temp = profilePoster.split("_");
                    String newProfilePoster = temp[0] + "_det.jpg";
                    int Year = movie.getInt("year");

                    Bitmap mIcon11 = null;
                    Bitmap mIcon2 = null;
                    try {
                        InputStream in = new java.net.URL(Thumbnail).openStream();
                        mIcon11 = BitmapFactory.decodeStream(in);
                        InputStream in2 = new URL(newProfilePoster).openStream();
                        mIcon2 = BitmapFactory.decodeStream(in2);
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }

                    Movie aMovie = new Movie(Title, String.valueOf(Year), rating, criticRating, runTime, synopsis, mIcon11, mIcon2);

                    searchResults.add(aMovie);

                    if (searchResults.size() > 1) { //If there are actually other movies to scroll through
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                nextButton.setEnabled(true);
                                previousButton.setEnabled(true);
                            }
                        });
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(searchResults.get(0).getImage());
                            title.setText(searchResults.get(0).getTitle());
                            currentlyDisplayedResult = 0;
                        }
                    });

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
