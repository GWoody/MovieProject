package com.example.MovieProject;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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
import java.util.List;

/**
 * Created by Aleksander on 17/10/2014.
 */
public class HomeFragment extends Fragment {

    static List<Movie> movieList = new ArrayList<Movie>();
    static movieImageAdapter adapter;
    APIConnect apiConnect;
    MovieListFragment.onListSelected callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.newmovies, container, false);

        String APIkey = "8h6mbnfwjb2qux8et6k8uq9a";
        String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=" + APIkey;

        GridView gridView = (GridView) v.findViewById(R.id.popularMovieGrid);
        adapter = new movieImageAdapter(getActivity(),movieList);
        gridView.setAdapter(adapter);

        if(movieList.size() >= 10)
        {
            apiConnect = new APIConnect();
            apiConnect.execute(url);
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
    public void onStop() {
        super.onStop();
        apiConnect.cancel(false);
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

    private class APIConnect extends AsyncTask<String, Void, String> {
        private ArrayList<Movie> Movies = new ArrayList<Movie>();

        @Override
        protected String doInBackground(String... params) {
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
                Log.d("%s", stringBuilder.toString());
                inputStream.close();

                String message = stringBuilder.toString();
                JSONReader(message);

                return stringBuilder.toString();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return null;
        }


        public void JSONReader(String result){
            try {
                JSONObject total = new JSONObject(result);
                JSONArray movies = total.getJSONArray("movies");
                for(int i = 0; i < movies.length(); i++){
                    JSONObject movie = movies.getJSONObject(i);
                    String Title = movie.getString("title");
                    String runTime = movie.getString("runtime");
                    String synopsis = movie.getString("synopsis");
                    String rating = movie.getString("mpaa_rating");
                    JSONObject ratings = movie.getJSONObject("ratings");
                    String criticRating = ratings.getString("critics_score");
                    JSONObject posters = movie.getJSONObject("posters");
                    String Thumbnail = posters.getString("thumbnail");
                    String profilePoster = posters.getString("detailed");
                    String[] temp = profilePoster.split("_");
                    String newProfilePoster = temp[0] + "_det.jpg";
                    int Year = movie.getInt("year");
                    Log.d("%s", "Result: Title:" + Title + " Year: " + Year);

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

                    Movie amovie = new Movie(Title,String.valueOf(Year),rating,criticRating,runTime,synopsis,mIcon11,mIcon2);
                    movieList.add(amovie);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged(); // can't update this by itself (needs runnable around it )
                        }
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}