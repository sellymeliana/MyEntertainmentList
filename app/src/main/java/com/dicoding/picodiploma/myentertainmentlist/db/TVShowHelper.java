package com.dicoding.picodiploma.myentertainmentlist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.dicoding.picodiploma.myentertainmentlist.db.DatabaseContract.TVShowColumns.ID;
import static com.dicoding.picodiploma.myentertainmentlist.db.DatabaseContract.TABLE_TVSHOW;

public class TVShowHelper {
    private static final String DATABASE_TABLE = TABLE_TVSHOW;
    private static DatabaseHelper dataBaseHelper;
    private static TVShowHelper INSTANCE;
    private static SQLiteDatabase database;

    private TVShowHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static TVShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TVShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                ID + " ASC");
    }

    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, ID + " = ?", new String[]{id});
    }


}
