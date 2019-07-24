package com.example.jonas.qrfitness_v12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static java.sql.Types.INTEGER;
import static org.xmlpull.v1.XmlPullParser.TEXT;

/**
 * Created by Jonas on 6/30/2017.
 *
 * This is the data interface helper class
 *
 */


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "workout_history_1.db";
    public static final String TABLE_NAME = "routines_table_1";
    public static final String ID = "Id";
    public static final String DATE = "Date";
    public static final String ROUTINE = "Routine";
    public static final String SETNUM = "SetNum";
    public static final String REPS = "Reps";
    public static final String WEIGHT = "Weight";


    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +" (Id INTEGER PRIMARY KEY AUTOINCREMENT,Date TEXT,Routine TEXT,SetNum INTEGER,Reps INTEGER,Weight INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    // Used to insert new routine set data into SQLite database
    // Called when either save and rest or save and exit are selected in routines activity
    // Passed variables to this function are the current date, current routine name, current set number, current set number of reps,
    // and weight for current set.
    public boolean insertTableData(String date,String routine,String setNum,String reps,String weight){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, date);
        contentValues.put(ROUTINE, routine);
        contentValues.put(SETNUM, setNum);
        contentValues.put(REPS, reps);
        contentValues.put(WEIGHT, weight);
        // Insert data and see if it was successful. If successful insert function return a 1. Otherwise,
        // returns -1.
        long result = sqLiteDatabase.insert(TABLE_NAME,null ,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return result;
    }
}
