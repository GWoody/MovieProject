package com.example.MovieProject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public void addMovieToList(Movie movie)
    {

    }

    public void deleteMovieFromList(int id)
    {
        
    }


}
