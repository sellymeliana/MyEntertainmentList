package com.dicoding.picodiploma.myentertainmentlist.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_MOVIE = "tb_movie";
    static String TABLE_TVSHOW = "tb_tvshow";

    public static final class MovieColumns implements BaseColumns {
        public static String ID = "id";
        public static String TITLE = "title";
    }

    public static final class TVShowColumns implements BaseColumns {
        public static String ID = "id";
        public static String TITLE = "title";
    }
}