package com.example.lab5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String STUDENT_TABLE = "Student_Table";
    public static final String COLUMN_STUDENT_NAME = "STUDENT_NAME";
    public static final String COLUMN_STUDENT_AGE = "STUDENT_AGE";

    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + STUDENT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_STUDENT_NAME + " TEXT, " + COLUMN_STUDENT_AGE + " INT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean addOne (StudentMod studentMod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STUDENT_NAME, studentMod.getName());
        cv.put(COLUMN_STUDENT_AGE, studentMod.getAge());

        long insert = db.insert(STUDENT_TABLE, null, cv);
        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }

    public List<StudentMod> getEveryone() {
        List<StudentMod> returnList = new ArrayList<>();
        String queryString = "Select * from "+ STUDENT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int SID = cursor.getInt(0); // student ID
                String SName = cursor.getString(1);
                int SAge = cursor.getInt(2);

                StudentMod newStudent = new StudentMod(SID, SName, SAge);
                returnList.add(newStudent);
            }while (cursor.moveToNext());
        } else{
            // nothing happens. no one is added.
        }
        cursor.close();
        db.close();
        return returnList;
    }
}
