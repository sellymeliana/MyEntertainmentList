package com.dicoding.picodiploma.myentertainmentlist.helper;

import android.database.Cursor;
import android.util.Log;

import com.dicoding.picodiploma.myentertainmentlist.db.DatabaseContract;
import com.dicoding.picodiploma.myentertainmentlist.entity.Movie;
import com.dicoding.picodiploma.myentertainmentlist.entity.TVShow;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Movie> mapMovieCursorToArrayList(Cursor moviesCursor) {
        ArrayList<Movie> moviesList = new ArrayList<>();
        while (moviesCursor.moveToNext()) {
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContract.MovieColumns.ID)));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE));
            Log.d("mapping",title);
            Movie movie = new Movie();
            movie.setMovie_id(id);
            movie.setTitle(title);
            moviesList.add(movie);
        }
        return moviesList;
    }

    public static ArrayList<TVShow> mapTVShowCursorToArrayList(Cursor tvshowsCursor) {
        ArrayList<TVShow> tvshowsList = new ArrayList<>();
        while (tvshowsCursor.moveToNext()) {
            int id = tvshowsCursor.getInt(tvshowsCursor.getColumnIndexOrThrow(DatabaseContract.TVShowColumns.ID));
            String title = tvshowsCursor.getString(tvshowsCursor.getColumnIndexOrThrow(DatabaseContract.TVShowColumns.TITLE));

            TVShow tvshow = new TVShow();
            tvshow.setTVShow_id(id);
            tvshow.setTitle(title);
            tvshowsList.add(tvshow);
        }
        return tvshowsList;
    }
}