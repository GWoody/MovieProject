package com.example.MovieProject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
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
 * Created by Anthony on 27/10/2014.
 */
public class APIConnect extends AsyncTask<String, Void, String> {
    private ArrayList<Movie> Movies = new ArrayList<Movie>();

    public ArrayList<Movie> getMovies() {
        return Movies;
    }

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

                MovieListFragment.AddAMovie(amovie);            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }
}
