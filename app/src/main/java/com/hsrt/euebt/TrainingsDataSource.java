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

/**
 * This class offers an interface for the app's database.
 */
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

    /**
     * Adds a new training unit (just done by the user) to the database.
     * @param name The name of the training unit that shall be stored in the database.
     * @return The resulting training unit that was generated and stored in the database.
     */
    public Training addTraining(String name) {
        Training result = new Training(name);
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, result.getName());
        values.put(MySQLiteHelper.COLUMN_TIMESTAMP, result.getTimestamp());
        database.insert(MySQLiteHelper.TABLE_TRAININGS, null, values);
        return result;
    }

    /**
     * Deletes a specific training unit from the database.
     * @param training The training unit that shall be deleted from the database.
     */
    public void deleteTraining(Training training) {
        long timestamp = training.getTimestamp();
        database.delete(MySQLiteHelper.TABLE_TRAININGS, MySQLiteHelper.COLUMN_TIMESTAMP + " = " + timestamp, null);
    }

    /**
     * Returns all names of training units stored in the database.
     * @return A list of all names of training units stored in the database. Each name is contained once.
     */
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

    /**
     * Retrieves a list of all training units belonging to a specific training unit pool.
     * @param name The name of the training units that should be retrieved.
     * @return All training units from the database that have the given name.
     */
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
        if (cursor == null) {
            return null;
        }
        return new Training(cursor.getString(0), cursor.getLong(1));
    }
}