package com.out.workout.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.out.workout.model.ExerciseModel;

import java.util.ArrayList;

public class ExerciseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Exercise.db";
    public static final String EXERCISE_TABLE_NAME = "Exercise";
    public static final String EXERCISE_ID = "ExerciseId";
    public static final String EXERCISE_TYPE = "ExerciseType";
    public static final String EXERCISE_NAME = "ExerciseName";
    public static final String EXERCISE_DESC = "ExerciseDesc";
    public static final String EXERCISE_IMG = "ExerciseImg";
    public static final String EXERCISE_CYCLES = "ExerciseCycles";

    public ExerciseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TAGS_TABLE = "CREATE TABLE " + EXERCISE_TABLE_NAME + "("
                + EXERCISE_ID + " INTEGER PRIMARY KEY,"
                + EXERCISE_TYPE + " TEXT,"
                + EXERCISE_NAME + " TEXT,"
                + EXERCISE_DESC + " TEXT,"
                + EXERCISE_IMG + " VARCHAR,"
                + EXERCISE_CYCLES + " TEXT" + ")";

        db.execSQL(CREATE_TAGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE_NAME);
        onCreate(db);
    }

    //todo insert ExerciseCycles
    public void insertExerciseCycles(String ExerciseType, String ExerciseName, String ExerciseDesc, int[] ExerciseImg, String ExerciseCycles) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXERCISE_TYPE, ExerciseType);
        contentValues.put(EXERCISE_NAME, ExerciseName);
        contentValues.put(EXERCISE_DESC, ExerciseDesc);
        contentValues.put(EXERCISE_IMG, String.valueOf(ExerciseImg));
        contentValues.put(EXERCISE_CYCLES, ExerciseCycles);
        db.insert(EXERCISE_TABLE_NAME, null, contentValues);
    }

    //todo get count Exercise record
    public int getExerciseCount() {
        String countQuery = "SELECT  * FROM " + EXERCISE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //todo isExist Exercise
    public boolean IsExist(String ExerciseName) {
        SQLiteDatabase objDatabase;
        objDatabase = getReadableDatabase();
        Cursor cursor = objDatabase.rawQuery("SELECT * FROM " + EXERCISE_TABLE_NAME + " WHERE " + EXERCISE_NAME + "=? ", new String[]{ExerciseName});
        if (cursor.moveToFirst()) {
            Log.d("Record  Already Exists", "Table is:" + EXERCISE_TABLE_NAME + " ColumnName:" + EXERCISE_NAME);
            return true;
        }
        Log.d("New Record  ", "Table is:" + EXERCISE_TABLE_NAME + " ColumnName:" + EXERCISE_NAME + " Column Value:" + ExerciseName);
        return false;
    }

    //todo isExist ExerciseType
    public boolean IsExistType(String ExerciseType) {
        SQLiteDatabase objDatabase;
        objDatabase = getReadableDatabase();
        Cursor cursor = objDatabase.rawQuery("SELECT * FROM " + EXERCISE_TABLE_NAME + " WHERE " + EXERCISE_TYPE + "=? ", new String[]{ExerciseType});
        if (cursor.moveToFirst()) {
            Log.d("Record  Already Exists", "Table is:" + EXERCISE_TABLE_NAME + " ColumnName:" + EXERCISE_TYPE);
            return true;
        }
        Log.d("New Record  ", "Table is:" + EXERCISE_TABLE_NAME + " ColumnName:" + EXERCISE_TYPE + " Column Value:" + ExerciseType);
        return false;
    }

    //todo Exercise update
    public boolean updateExerciseCycles(int id, String ExerciseName, String ExerciseCycles) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXERCISE_NAME, ExerciseName);
        contentValues.put(EXERCISE_CYCLES, ExerciseCycles);
        db.update(EXERCISE_TABLE_NAME, contentValues, EXERCISE_ID + " = ?", new String[]{String.valueOf(id)});
        return true;
    }

    //todo get record of Exercise
    @SuppressLint("Range")
    public int getExerciseRecord(String ExerciseName) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EXERCISE_TABLE_NAME + " WHERE " + EXERCISE_NAME + "=? ", new String[]{String.valueOf(ExerciseName)});
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex(EXERCISE_ID));
    }

    public ArrayList<ExerciseModel> getExerciseRecords(String workoutType) {
        ArrayList<ExerciseModel> tagsArrayList = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EXERCISE_TABLE_NAME + " WHERE " + EXERCISE_TYPE + "=? ", new String[]{String.valueOf(workoutType)});
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            do {
                String[] StrImg = cursor.getString(cursor.getColumnIndex(EXERCISE_IMG)).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

                int[] IntImg = new int[StrImg.length];
                for (int i = 0; i < StrImg.length; i++) {
                    try {
                        IntImg[i] = Integer.parseInt(StrImg[i]);
                    } catch (NumberFormatException nfe) {
                    }
                }
                String[] StrCycles = cursor.getString(cursor.getColumnIndex(EXERCISE_CYCLES)).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

                int[] IntCycles = new int[StrCycles.length];
                for (int i = 0; i < StrCycles.length; i++) {
                    try {
                        IntCycles[i] = Integer.parseInt(StrCycles[i]);
                    } catch (NumberFormatException nfe) {
                    }
                }
                @SuppressLint("Range") ExerciseModel user = new ExerciseModel(cursor.getInt(cursor.getColumnIndex(EXERCISE_ID))
                        , cursor.getString(cursor.getColumnIndex(EXERCISE_NAME))
                        , cursor.getString(cursor.getColumnIndex(EXERCISE_DESC))
                        , cursor.getString(cursor.getColumnIndex(EXERCISE_TYPE))
                        , IntImg
                        , IntCycles
                       );
                tagsArrayList.add(user);
            } while (cursor.moveToNext());
        }
        return tagsArrayList;
    }
}