package com.example.MovieProject;

import android.app.Activity;
import android.os.Bundle;

public class MyActivity extends Activity {

    static SQLHelper dbHelper;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dbHelper = new SQLHelper(this);
        dbHelper.getWritableDatabase();
    }
}
