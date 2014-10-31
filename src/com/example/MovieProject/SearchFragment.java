package com.example.MovieProject;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Aleksander on 18/10/2014.
 */
public class SearchFragment extends Fragment {
    EditText searchTitleText;
    EditText searchYearText;
    EditText searchScoreText;
    Button searchButton;
    final String apiKey = "ntj2qcmag38gg8v67pbs7egw";
    int year = -1;
    int score = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.searchscreen, container, false);

        searchTitleText = (EditText)v.findViewById(R.id.searchTitleText);
        searchScoreText = (EditText)v.findViewById(R.id.searchScoreText);
        searchYearText = (EditText)v.findViewById(R.id.searchYearText);
        searchButton = (Button)v.findViewById(R.id.searchbutton);

        searchTitleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (searchTitleText.length() > 0) {
                    searchButton.setEnabled(true);
                    searchYearText.setEnabled(true);
                    searchScoreText.setEnabled(true);
                }
                else {
                    searchButton.setEnabled(false);
                    searchYearText.setEnabled(false);
                    searchScoreText.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
                if (searchYearText.length() > 0) {
                    year = Integer.parseInt(searchYearText.getText().toString());
                }
                if (searchScoreText.length() > 0) {
                    score = Integer.parseInt(searchScoreText.getText().toString());
                }
                ((MyActivity)getActivity()).showSearchResults(searchURL, year, score);
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (searchTitleText.length() > 0) {
            searchScoreText.setEnabled(true);
            searchYearText.setEnabled(true);
        }
    }
}