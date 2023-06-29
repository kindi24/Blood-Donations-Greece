package com.example.blooddonations.ui.bloodclubs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperBloodClubs extends SQLiteOpenHelper {
    private final Context context;
    private static final String DATABASE_NAME = "Blood Donator Clubs.db";
    private static final int DATABASE_VERSION = 4;

    private final String TABLE_NAME = "Blood_Donator_Clubs";
    private final String COLUMN_ID = "ID";
    private final String COLUMN_CITY = "City";
    private final String COLUMN_PREFECTURE = "Prefecture";

    // Δημιουργία constructor του DBHelperBottles()
    public DBHelperBloodClubs(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Δημιουργία πίνακα
    @Override
    public void onCreate(SQLiteDatabase dB) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CITY + " TEXT, " + COLUMN_PREFECTURE + " TEXT); ";
        dB.execSQL(query);
    }

    // Αναβάθμιση πίνακα
    @Override
    public void onUpgrade(SQLiteDatabase dB, int i, int i1) {
        dB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(dB);
    }

    Cursor ReadData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase dB = getReadableDatabase();

        Cursor cursor = null;
        if (dB != null){
            cursor = dB.rawQuery(query, null);
        }
        return cursor;
    }
}
