package com.hsrt.euebt;

/**
 * Created by Johannes on 11.12.2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * A helper class for managing the database's structure.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_TRAININGS = "trainings";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_LOCATION = "location";

    private static final String DATABASE_NAME = "euebt.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table " + TABLE_TRAININGS + "( " + COLUMN_NAME + " text not null, " + COLUMN_TIMESTAMP + " integer primary key autoincrement, " + COLUMN_LOCATION + " text not null );";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data.");
        db.execSQL("drop table if exists " + TABLE_TRAININGS);
        onCreate(db);
    }
}