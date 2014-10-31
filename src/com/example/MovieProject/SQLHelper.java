package com.example.MovieProject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Gareth on 14/10/2014.
 */
public class SQLHelper extends SQLiteOpenHelper {

    public SQLHelper(Context context)
    {
        super(context, DatabaseContract.DATABASE_NAME,null,DatabaseContract.DATABASE_VERSION); // set context and DB name

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DatabaseContract.WatchedTable.CREATE_WATCHED_TABLE); // create the tables
        db.execSQL(DatabaseContract.ToWatchTable.CREATE_TOWATCH_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean checkRecordExistsToWatch(String title, String year)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        boolean isInToWatch = false;

        String selectQuery="";
        {
            selectQuery = "SELECT  * FROM " + DatabaseContract.ToWatchTable.TABLE_NAME + " where " + DatabaseContract.ToWatchTable.TITLE + " " + "='" + title + "' " + "AND " + DatabaseContract.ToWatchTable.YEAR + " " + "='" + year +"'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() > 0){
                isInToWatch = true;
                return isInToWatch;
            }
        }

        return isInToWatch;
    }

    public boolean checkRecordExistsWatched(String title, String year)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        boolean isInWatched = false;

        String selectQuery="";
        {
            selectQuery =  "SELECT  * FROM " + DatabaseContract.WatchedTable.TABLE_NAME + " where " + DatabaseContract.WatchedTable.TITLE + " " + "='" + title + "' " + "AND " + DatabaseContract.WatchedTable.YEAR + " " + "='" + year + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() > 0){
                isInWatched = true;
                return isInWatched;
            }
        }
        return isInWatched;
    }

    public boolean deleteRecordFromListWatched(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "TITLE = ?";

        db.delete(DatabaseContract.WatchedTable.TABLE_NAME,whereClause, new String[] {title});

        return true;

    }

    public boolean deleteRecordFromListToWatch(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "TITLE = ?";

        db.delete(DatabaseContract.ToWatchTable.TABLE_NAME,whereClause, new String[] {title});

        return true;

    }

    public ArrayList<Movie> getAllMoviesinList(boolean whichTable)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Movie> movies = new ArrayList<Movie>();

        String selectQuery = "";

        if(whichTable)
        {
           selectQuery = "SELECT  * FROM " + DatabaseContract.ToWatchTable.TABLE_NAME;
        }
        else
        {
           selectQuery = "SELECT  * FROM " + DatabaseContract.WatchedTable.TABLE_NAME;
        }

        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        String tempTitle = cursor.getString(1);
                        String tempYear = cursor.getString(2);
                        String tempRating = cursor.getString(3);
                        String tempCriticScore = cursor.getString(4);
                        String tempRuntime =  cursor.getString(5);
                        String tempSynopsis = cursor.getString(6);
                        byte[] tempThumb = cursor.getBlob(7);
                        byte[] tempImage = cursor.getBlob(8);

                        Movie obj = new Movie(tempTitle,tempYear,tempRating,tempCriticScore,tempRuntime,tempSynopsis,MyActivity.getImage(tempThumb),MyActivity.getImage(tempImage));

                        movies.add(obj);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return movies;
    }

    public void addMovieToList(Movie movie, boolean whichList)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        byte[] thumbnail = MyActivity.getBytes(movie.getThumbnail());

        byte[] image = MyActivity.getBytes(movie.getImage());

        if(whichList)
        {
            db.insert(DatabaseContract.ToWatchTable.TABLE_NAME,null,createContentValues(movie.getTitle(),movie.getYear(),movie.getRating(),movie.getCriticScore(),movie.getRunTime(),movie.getSynopsis(),thumbnail,image));
        }
        else
        {
            db.insert(DatabaseContract.WatchedTable.TABLE_NAME,null,createContentValues(movie.getTitle(),movie.getYear(),movie.getRating(),movie.getCriticScore(),movie.getRunTime(),movie.getSynopsis(),thumbnail,image));
        }


    }


    private ContentValues createContentValues(String title, String year, String rating, String criticScore, String runtime, String synopsis, byte[] thumbnail, byte[] image) {

        ContentValues cv = new ContentValues(); // prep the variables for insert into photos
        cv.put(DatabaseContract.ToWatchTable.TITLE,title);
        cv.put(DatabaseContract.ToWatchTable.YEAR,year);
        cv.put(DatabaseContract.ToWatchTable.RATING,rating);
        cv.put(DatabaseContract.ToWatchTable.CRITICSCORE,criticScore);
        cv.put(DatabaseContract.ToWatchTable.RUNTIME,runtime);
        cv.put(DatabaseContract.ToWatchTable.SYNOPSIS,synopsis);
        cv.put(DatabaseContract.ToWatchTable.THUMBNAIL,thumbnail);
        cv.put(DatabaseContract.ToWatchTable.IMAGE,image);

        return cv;

    }


}
