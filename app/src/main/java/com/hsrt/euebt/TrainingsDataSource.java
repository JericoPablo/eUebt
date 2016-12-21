package com.hsrt.euebt;

/**
 * Created by Johannes on 11.12.2016.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TrainingsDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_TIMESTAMP };

    public TrainingsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Training addTraining(String name) {
        Training result = new Training(name);
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, result.getName());
        values.put(MySQLiteHelper.COLUMN_TIMESTAMP, result.getTimestamp());
        database.insert(MySQLiteHelper.TABLE_TRAININGS, null, values);
        return result;
    }

    public void deleteTraining(Training training) {
        long timestamp = training.getTimestamp();
        database.delete(MySQLiteHelper.TABLE_TRAININGS, MySQLiteHelper.COLUMN_TIMESTAMP + " = " + timestamp, null);
    }

    public List<String> getAllNames() {
        List<String> names = new ArrayList<String>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TRAININGS, new String[] { allColumns[0] }, null, new String[] { "distinct" }, MySQLiteHelper.COLUMN_NAME, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            names.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return names;
    }

    public List<Training> getAllTrainingsWithName(String name) {
        List<Training> trainings = new ArrayList<Training>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TRAININGS, allColumns, MySQLiteHelper.COLUMN_TIMESTAMP + " = '" + name + "'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Training training = cursorToTraining(cursor);
            trainings.add(training);
            cursor.moveToNext();
        }
        cursor.close();
        return trainings;
    }

    private Training cursorToTraining(Cursor cursor) {
        return new Training(cursor.getString(0), cursor.getLong(1));
    }
}