package com.example.MovieProject;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
    private ScrollableImageView scrollableImageView;
    int numberOfResults;
    int year;
    int score;
    TextView title;
    TextView resultsNumber;
    Button viewButton;
    ViewPager viewPager;
    DownloadSearchResultsTask downloadSearchResultsTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movieresults, container, false);

        searchResults = new ArrayList<Movie>();

        Bundle bundle = this.getArguments();
        year = bundle.getInt("year");
        score = bundle.getInt("score");

        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelableArrayList("searchResults") != null) {
                searchResults = savedInstanceState.getParcelableArrayList("searchResults");
            }
            else {
                downloadSearchResultsTask = new DownloadSearchResultsTask();

                downloadSearchResultsTask.execute(bundle.getString("url"));
            }
        }
        else {
            downloadSearchResultsTask = new DownloadSearchResultsTask();

            downloadSearchResultsTask.execute(bundle.getString("url"));
        }

        resultsNumber = (TextView) v.findViewById(R.id.resultsNumber);
        title = (TextView) v.findViewById(R.id.title);
        viewButton = (Button) v.findViewById(R.id.viewButton);
        viewPager = (ViewPager) v.findViewById(R.id.moviePicture);
        scrollableImageView = new ScrollableImageView(searchResults, getActivity());
        viewPager.setAdapter(scrollableImageView);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                title.setText(searchResults.get(i).getTitle());
                resultsNumber.setText("Result " + (i + 1) + "/" + numberOfResults);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyActivity) getActivity()).viewMovie(searchResults.get(viewPager.getCurrentItem()));
            }
        });

        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        downloadSearchResultsTask.cancel(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("searchResults", searchResults);
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
                final JSONArray movies = total.getJSONArray("movies");
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
                    int aMovieYear = Integer.parseInt(aMovie.getYear());
                    int aMovieScore = Integer.parseInt(aMovie.getCriticScore());
                    if ((year == -1 || year == aMovieYear) && (score == -1 || score <= aMovieScore)) {
                        searchResults.add(aMovie);
                        ++numberOfResults;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            scrollableImageView.notifyDataSetChanged();
                            if (searchResults.size() > 0) {
                                title.setText(searchResults.get(viewPager.getCurrentItem()).getTitle());
                                resultsNumber.setText("Result " + (viewPager.getCurrentItem() + 1) + "/" + numberOfResults);
                                viewButton.setEnabled(true);
                            }
                        }
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (searchResults.size() == 0) {
                title.setText("No Results Found");
                resultsNumber.setText("0/0");
            }
        }
    }

    private class ScrollableImageView extends PagerAdapter {

        ArrayList<Movie> movies;
        Context context;

        ScrollableImageView(ArrayList<Movie> movies, Context context) {
            this.movies = movies;
            this.context = context;
        }

        @Override
        public int getCount() {
            return movies.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == ((ImageView)o);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            Bitmap bitmap = movies.get(position).getImage();
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder, options);
            }
            imageView.setImageBitmap(bitmap);

            ((ViewPager)container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView((ImageView)object);
        }

        public void setMovies(ArrayList<Movie> movies) {
            this.movies = movies;
        }
    }
}