package com.dicoding.picodiploma.myentertainmentlist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbmovieapp";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TB_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_MOVIE,
            DatabaseContract.MovieColumns.ID,
            DatabaseContract.MovieColumns.TITLE
    );

    private static final String SQL_CREATE_TABLE_TB_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_TVSHOW,
            DatabaseContract.TVShowColumns.ID,
            DatabaseContract.TVShowColumns.TITLE
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TB_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TB_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TVSHOW);
        onCreate(db);
    }
}
