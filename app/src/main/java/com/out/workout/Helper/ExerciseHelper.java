package com.out.workout.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.out.workout.model.ReminderModel;
import com.out.workout.utils.AlarmUtils;
import com.out.workout.model.ExerciseModel;

import java.util.ArrayList;
import java.util.List;

public class ExerciseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Exercise.db";
    public static final String EXERCISE_TABLE_NAME = "Exercise";
    public static final String EXERCISE_ID = "ExerciseId";
    public static final String EXERCISE_TYPE = "ExerciseType";
    public static final String EXERCISE_NAME = "ExerciseName";
    public static final String EXERCISE_DESC = "ExerciseDesc";
    public static final String EXERCISE_IMG = "ExerciseImg";
    public static final String EXERCISE_CYCLES = "ExerciseCycles";
    private static final String REMINDER_TABLE_NAME = "Reminder";

    public static final String REMINDER_ID = "ReminderId";
    public static final String REMINDER_TIME = "ReminderTime";
    public static final String REMINDER_MONDAY = "ReminderMon";
    public static final String REMINDER_TUESDAY = "ReminderTue";
    public static final String REMINDER_WEDNESDAY = "ReminderWed";
    public static final String REMINDER_THURSDAY = "ReminderThu";
    public static final String REMINDER_FRIDAY = "ReminderFri";
    public static final String REMINDER_SATURDAY = "ReminderSat";
    public static final String REMINDER_SUNDAY = "ReminderSun";
    public static final String REMINDER_ON_OFF = "ReminderOnOff";

    public ExerciseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXERCISE_TABLE = "CREATE TABLE " + EXERCISE_TABLE_NAME + "("
                + EXERCISE_ID + " INTEGER PRIMARY KEY,"
                + EXERCISE_TYPE + " TEXT,"
                + EXERCISE_NAME + " TEXT,"
                + EXERCISE_DESC + " TEXT,"
                + EXERCISE_IMG + " VARCHAR,"
                + EXERCISE_CYCLES + " TEXT" + ")";

        db.execSQL(CREATE_EXERCISE_TABLE);

        final String CREATE_ALARMS_TABLE = "CREATE TABLE " + REMINDER_TABLE_NAME + " (" +
                REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                REMINDER_TIME + " INTEGER NOT NULL, " +
                REMINDER_MONDAY + " INTEGER NOT NULL, " +
                REMINDER_TUESDAY + " INTEGER NOT NULL, " +
                REMINDER_WEDNESDAY + " INTEGER NOT NULL, " +
                REMINDER_THURSDAY + " INTEGER NOT NULL, " +
                REMINDER_FRIDAY + " INTEGER NOT NULL, " +
                REMINDER_SATURDAY + " INTEGER NOT NULL, " +
                REMINDER_SUNDAY + " INTEGER NOT NULL, " +
                REMINDER_ON_OFF + " INTEGER NOT NULL" + ");";

        db.execSQL(CREATE_ALARMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + REMINDER_TABLE_NAME);
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

    //todo insert Reminder
    public long insertAlarm(ReminderModel reminderModel) {
        return getWritableDatabase().insert(REMINDER_TABLE_NAME, null, AlarmUtils.toContentValues(reminderModel));
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

    //todo get record of Exercise
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

    //todo Reminder update
    public int updateAlarm(ReminderModel reminderModel) {
        final String where = REMINDER_ID + "=?";
        final String[] whereArgs = new String[] { Long.toString(reminderModel.getId()) };
        return getWritableDatabase().update(REMINDER_TABLE_NAME, AlarmUtils.toContentValues(reminderModel), where, whereArgs);
    }
    //todo delete reminder

    public int deleteAlarm(ReminderModel reminderModel) {
        return deleteAlarm(reminderModel.getId());
    }

    //todo delete reminder
    int deleteAlarm(long id) {
        final String where = REMINDER_ID + "=?";
        final String[] whereArgs = new String[] { Long.toString(id) };
        return getWritableDatabase().delete(REMINDER_TABLE_NAME, where, whereArgs);
    }

    //todo get record of Reminder
    public List<ReminderModel> getAlarms() {
        Cursor cursor = null;
        try{
            cursor = getReadableDatabase().query(REMINDER_TABLE_NAME, null, null, null, null, null, null);
            return AlarmUtils.buildAlarmList(cursor);
        } finally {
            if (cursor != null && !cursor.isClosed()) cursor.close();
        }
    }

    //todo get count Exercise record
    public int getReminderCount() {
        String countQuery = "SELECT  * FROM " + REMINDER_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
