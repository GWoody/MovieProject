package com.example.MovieProject;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

/**
 * Created by Aleksander on 18/10/2014.
 */
public class SearchFragment extends Fragment {
    EditText searchTitleText;
    Button searchButton;
    final String apiKey = "ntj2qcmag38gg8v67pbs7egw";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.searchscreen, container, false);

        searchTitleText = (EditText)v.findViewById(R.id.searchTitleText);
        searchButton = (Button)v.findViewById(R.id.searchbutton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleQuery = "";
                try {
                    titleQuery = URLEncoder.encode(searchTitleText.getText().toString(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String searchURL = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" + apiKey + "&q=" + titleQuery + "&page_limit=10";
                ((MyActivity)getActivity()).showSearchResults(searchURL);
            }
        });

        return v;
    }
}