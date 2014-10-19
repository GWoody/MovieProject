package com.example.MovieProject;

import android.provider.BaseColumns;

/**
 * Created by Gareth on 14/10/2014.
 */
public class DatabaseContract {

        public static final int DATABASE_VERSION = 1; // data types, db and commas
        public static final String DATABASE_NAME = "Movie.db";
        public static final String TEXT_TYPE = "TEXT";
        public static final String INT_TYPE = "INT";
        public static final String BLOB_TYPE = "BLOB";
        public static final String COMMA_SEP = ",";

        private DatabaseContract() {};

        public static abstract class ToWatchTable implements BaseColumns{

            public static final String TABLE_NAME = "ToWatchTable"; // the base journey container
            public static final String ID = "ID";
            public static final String TITLE = "TITLE";
            public static final String YEAR = "YEAR";
            public static final String RATING = "RATING";
            public static final String CRITICSCORE = "CRITICSCORE";
            public static final String RUNTIME = "RUNTIME";
            public static final String SYNOPSIS = "SYNOPSIS";
            public static final String THUMBNAIL = "THUMBNAIL";
            public static final String IMAGE = "IMAGE";

            public static final String[] COLUMNS = {ID,TITLE,YEAR,RATING,CRITICSCORE, RUNTIME, SYNOPSIS, THUMBNAIL, IMAGE};

            public static final String CREATE_TOWATCH_TABLE = "CREATE TABLE " + TABLE_NAME +
                    " (" + ID + " " + " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE + " " + TEXT_TYPE + COMMA_SEP +
                    YEAR + " " + TEXT_TYPE + COMMA_SEP +
                    RATING + " " + TEXT_TYPE + COMMA_SEP +
                    CRITICSCORE + " " + TEXT_TYPE + COMMA_SEP +
                    RUNTIME + " " + TEXT_TYPE + COMMA_SEP +
                    SYNOPSIS + " " + TEXT_TYPE + COMMA_SEP +
                    THUMBNAIL + " " + BLOB_TYPE + COMMA_SEP +
                    IMAGE + " " + BLOB_TYPE + " )";

            public static final String  DELETE_TOWATCH_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


        }



    public static abstract class WatchedTable implements BaseColumns{

        public static final String TABLE_NAME = "WatchedTable"; // the base journey container
        public static final String ID = "ID";
        public static final String TITLE = "TITLE";
        public static final String YEAR = "YEAR";
        public static final String RATING = "RATING";
        public static final String CRITICSCORE = "CRITICSCORE";
        public static final String RUNTIME = "RUNTIME";
        public static final String SYNOPSIS = "SYNOPSIS";
        public static final String THUMBNAIL = "THUMBNAIL";
        public static final String IMAGE = "IMAGE";

        public static final String[] COLUMNS = {ID,TITLE,YEAR,RATING,CRITICSCORE, RUNTIME, SYNOPSIS, THUMBNAIL, IMAGE};

        public static final String CREATE_WATCHED_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + ID + " " + " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE + " " + TEXT_TYPE + COMMA_SEP +
                YEAR + " " + TEXT_TYPE + COMMA_SEP +
                RATING + " " + TEXT_TYPE + COMMA_SEP +
                CRITICSCORE + " " + TEXT_TYPE + COMMA_SEP +
                RUNTIME + " " + TEXT_TYPE + COMMA_SEP +
                SYNOPSIS + " " + TEXT_TYPE + COMMA_SEP +
                THUMBNAIL + " " + BLOB_TYPE + COMMA_SEP +
                IMAGE + " " + BLOB_TYPE + " )";

        public static final String  DELETE_WATCHED_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    }


}
