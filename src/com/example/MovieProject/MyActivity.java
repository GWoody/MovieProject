package com.example.MovieProject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyActivity extends Activity implements MovieListFragment.onListSelected {

    private enum CurrentFragment //Keep track of current Fragment displayed so Navigation Drawer won't double up on it
    {
        Home, Search, WatchList, Watched
    };

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CurrentFragment currentFragment;
    private MovieProfile profileFragment;
    static SQLHelper helper; // helper for the database

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment fragment;
        String tag;

        if (savedInstanceState != null) {
            if (getFragmentManager().findFragmentByTag("SearchResultsFragment") != null) {
                fragment = getFragmentManager().findFragmentByTag("SearchResultsFragment");
                tag = "SearchResultsFragment";
            }
            /*
            String previousFragment = savedInstanceState.getString("CurrentFragment");

            if (previousFragment.equals("Watched")) {
                fragment = new MovieListFragment();
                currentFragment = CurrentFragment.Watched;
            }

            else if (previousFragment.equals("Search")) {
                if (savedInstanceState.getParcelableArrayList("searchResults") != null) {
                    fragment = new MovieResultsFragment();
                }
                else {
                    fragment = new SearchFragment();
                }
                currentFragment = CurrentFragment.Search;
            }

            else if (previousFragment.equals("Watched")) {
                fragment = new MovieListFragment();
                currentFragment = CurrentFragment.Watched;
            }
            */
            else {
                fragment = new HomeFragment();
                currentFragment = CurrentFragment.Home;
                tag = "HomeFragment";
            }

        }

        else {
            fragment = new HomeFragment();
            currentFragment = CurrentFragment.Home;
            tag = "HomeFragment";
        }

        setContentView(R.layout.main);

        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, tag).commit();

        String[] mDrawerTitles = {"Home", "Search Movies", "Watch List", "Watched"};

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mDrawerTitles));

        helper = new SQLHelper(this); // set the helper to the db


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        mDrawerLayout.setFocusableInTouchMode(false);
    }


/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        switch (currentFragment) {
            case Home:
                outState.putString("CurrentFragment", "Home");
                break;
            case Search:
                outState.putString("CurrentFragment", "Search");
                break;
            case WatchList:
                outState.putString("CurrentFragment", "WatchList");
                break;
            case Watched:
                outState.putString("CurrentFragment", "Watched");
                break;
            default:
                outState.putString("CurrentFragment", "Home");
        }
    }


*/



    public void onListElementSelected(Movie movie) // if a list element is selected
    {
        viewMovie(movie);
    }

    public void viewMovie(Movie movie) {
        FragmentTransaction newTran;
        FragmentManager manager = getFragmentManager();
        newTran = manager.beginTransaction();
        profileFragment = new MovieProfile();

        Bundle bundle = new Bundle();

        bundle.putParcelable("movie", movie);

        profileFragment.setArguments(bundle);

        newTran.replace(R.id.content_frame, profileFragment);
        newTran.addToBackStack("Profile");
        newTran.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public static byte[] getBytes(Bitmap image)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream(); // get the byte form of the image
        image.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image)
    {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    } // get the bitmap for the image

    private void selectItem(int position) {
        switch (position) {
            case 0: //Case 0 is Home
                if (currentFragment != CurrentFragment.Home) {
                    Fragment fragment = new HomeFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Home").commit();
                    currentFragment = CurrentFragment.Home;
                }
                break;
            case 1: //Case 1 is Search Movies
                if (currentFragment != CurrentFragment.Search) { //If not already on Search
                    Fragment fragment = new SearchFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Search").commit();
                    currentFragment = CurrentFragment.Search;

                }
                break;
            case 2: //Case 2 is WatchList
                if (currentFragment != CurrentFragment.WatchList) {
                    Fragment fragment = new MovieListFragment();
                    Bundle bundle = new Bundle();
                    boolean value = true;
                    bundle.putBoolean("whichList",value);
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Watch").commit();
                    currentFragment = CurrentFragment.WatchList;
                }
                break;
            case 3: //Case 3 is Watched
                if (currentFragment != CurrentFragment.Watched) {
                    Fragment fragment = new MovieListFragment();
                    Bundle bundle = new Bundle();
                    boolean value = false;
                    bundle.putBoolean("whichList",value);
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Seen").commit();
                    currentFragment = CurrentFragment.Watched;
                }
                break;
        }

        mDrawerLayout.closeDrawer(mDrawerList);
    }

    //URL is query to be performed in new Fragment
    public void showSearchResults(String url, int year, int score) {
        Fragment searchFragment = new MovieResultsFragment();

        Bundle bundle = new Bundle();

        bundle.putString("url", url);
        bundle.putInt("year", year);
        bundle.putInt("score", score);

        searchFragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, searchFragment, "SearchResultsFragment").addToBackStack(null).commit();
    }
}

