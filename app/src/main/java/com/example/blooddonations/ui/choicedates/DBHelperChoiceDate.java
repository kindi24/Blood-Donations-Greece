package com.example.blooddonations.ui.choicedates;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelperChoiceDate extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "Blood Donations.db";
    private static final int DATABASE_VERSION = 4;

    private final String TABLE_NAME = "Choice_Date";
    private final String COLUMN_ID = "ID";
    private final String COLUMN_NAME = "Name";
    private final String COLUMN_BLOOD_CLUB = "BloodClub";
    private final String COLUMN_DATE = "Date";

    // Δημιουργία constructor του DBHelperChoiceDate()
    public DBHelperChoiceDate(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Δημιουργία πίνακα
    @Override
    public void onCreate(SQLiteDatabase dB) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, " + COLUMN_BLOOD_CLUB + " TEXT, "
                + COLUMN_DATE + " TEXT); ";
        dB.execSQL(query);
    }

    // Αναβάθμιση πίνακα
    @Override
    public void onUpgrade(SQLiteDatabase dB, int i, int i1) {
        dB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(dB);
    }

    // Δημιουργία καινούριου στοιχείου στο πίνακα Bone_Marrow
    void choiceDate(String name, String club, String date){
        SQLiteDatabase dB = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_BLOOD_CLUB, club);
        cv.put(COLUMN_DATE, date);

        long result = dB.insert(TABLE_NAME, null, cv);

        // Έλεγχος αν εισάχθηκε κάποιο στοιχείο στο πίνακα
        if (result == -1) Toast.makeText(context,
                "Δεν ολοκληρώθηκε η εισαγωγή", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Επιτυχής εισαγωγή στοιχείων", Toast.LENGTH_SHORT).show();

    }
}
