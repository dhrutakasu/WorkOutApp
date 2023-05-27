package com.out.workout.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.out.workout.Alarm.model.Alarm;
import com.out.workout.Alarm.util.AlarmUtils;
import com.out.workout.model.ExerciseModel;
import com.out.workout.model.ReminderModel;

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

    public static final String REMINDER_TABLE_NAME = "Reminder";
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

    private static final String TABLE_NAME = "alarms";

    public static final String _ID = "_id";
    public static final String COL_TIME = "time";
    public static final String COL_MON = "mon";
    public static final String COL_TUES = "tues";
    public static final String COL_WED = "wed";
    public static final String COL_THURS = "thurs";
    public static final String COL_FRI = "fri";
    public static final String COL_SAT = "sat";
    public static final String COL_SUN = "sun";
    public static final String COL_IS_ENABLED = "is_enabled";

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

        String CREATE_REMINDER_TABLE = "CREATE TABLE " + REMINDER_TABLE_NAME + "("
                + REMINDER_ID + " INTEGER PRIMARY KEY,"
                + REMINDER_TIME + " TEXT,"
                + REMINDER_MONDAY + " TEXT,"
                + REMINDER_TUESDAY + " TEXT,"
                + REMINDER_WEDNESDAY + " TEXT,"
                + REMINDER_THURSDAY + " TEXT,"
                + REMINDER_FRIDAY + " TEXT,"
                + REMINDER_SATURDAY + " TEXT,"
                + REMINDER_SUNDAY + " TEXT,"
                + REMINDER_ON_OFF + " TEXT" + ")";

        db.execSQL(CREATE_REMINDER_TABLE);

        final String CREATE_ALARMS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TIME + " INTEGER NOT NULL, " +
                COL_MON + " INTEGER NOT NULL, " +
                COL_TUES + " INTEGER NOT NULL, " +
                COL_WED + " INTEGER NOT NULL, " +
                COL_THURS + " INTEGER NOT NULL, " +
                COL_FRI + " INTEGER NOT NULL, " +
                COL_SAT + " INTEGER NOT NULL, " +
                COL_SUN + " INTEGER NOT NULL, " +
                COL_IS_ENABLED + " INTEGER NOT NULL" +
                ");";

        db.execSQL(CREATE_ALARMS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + REMINDER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
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
    public long insertReminder(ReminderModel model) {
        System.out.println("--- REMINDER_ID : "+model.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REMINDER_TIME, model.getTime());
        contentValues.put(REMINDER_MONDAY, model.getMon());
        contentValues.put(REMINDER_TUESDAY, model.getTue());
        contentValues.put(REMINDER_WEDNESDAY, model.getWed());
        contentValues.put(REMINDER_THURSDAY, model.getThu());
        contentValues.put(REMINDER_FRIDAY, model.getFri());
        contentValues.put(REMINDER_SATURDAY, model.getSat());
        contentValues.put(REMINDER_SUNDAY, model.getSun());
        contentValues.put(REMINDER_ON_OFF, model.getOnOff());
        return db.insert(REMINDER_TABLE_NAME, null, contentValues);
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

    //todo Reminder update
    public boolean updateReminder(ReminderModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REMINDER_ID, model.getId());
        contentValues.put(REMINDER_TIME, model.getTime());
        contentValues.put(REMINDER_MONDAY, model.getMon());
        contentValues.put(REMINDER_TUESDAY, model.getTue());
        contentValues.put(REMINDER_WEDNESDAY, model.getWed());
        contentValues.put(REMINDER_THURSDAY, model.getThu());
        contentValues.put(REMINDER_FRIDAY, model.getFri());
        contentValues.put(REMINDER_SATURDAY, model.getSat());
        contentValues.put(REMINDER_SUNDAY, model.getSun());
        contentValues.put(REMINDER_ON_OFF, model.getOnOff());
        db.update(REMINDER_TABLE_NAME, contentValues, REMINDER_ID + " = ?", new String[]{String.valueOf(model.getId())});
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

    //todo get record of Reminder
    public ArrayList<ReminderModel> getReminderRecords() {
        ArrayList<ReminderModel> reminderModels = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + REMINDER_TABLE_NAME, null);
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") ReminderModel user = new ReminderModel(cursor.getString(cursor.getColumnIndex(REMINDER_ID))
                        , cursor.getString(cursor.getColumnIndex(REMINDER_MONDAY))
                        , cursor.getString(cursor.getColumnIndex(REMINDER_TUESDAY))
                        , cursor.getString(cursor.getColumnIndex(REMINDER_WEDNESDAY))
                        , cursor.getString(cursor.getColumnIndex(REMINDER_THURSDAY))
                        , cursor.getString(cursor.getColumnIndex(REMINDER_FRIDAY))
                        , cursor.getString(cursor.getColumnIndex(REMINDER_SATURDAY))
                        , cursor.getString(cursor.getColumnIndex(REMINDER_SUNDAY))
                        , cursor.getString(cursor.getColumnIndex(REMINDER_TIME))
                        , cursor.getString(cursor.getColumnIndex(REMINDER_ON_OFF))
                );
                reminderModels.add(user);
            } while (cursor.moveToNext());
        }
        return reminderModels;
    }

    //todo delete reminder
    public void deleteReminder(String id) {
        System.out.println("---- delete : "+id);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(REMINDER_TABLE_NAME, REMINDER_ID + "= ?", new String[]{String.valueOf(id)});
    }





    public long addAlarm() {
        return addAlarm(new Alarm());
    }

    long addAlarm(Alarm alarm) {
        return getWritableDatabase().insert(TABLE_NAME, null, AlarmUtils.toContentValues(alarm));
    }

    public int updateAlarm(Alarm alarm) {
        final String where = _ID + "=?";
        final String[] whereArgs = new String[] { Long.toString(alarm.getId()) };
        return getWritableDatabase()
                .update(TABLE_NAME, AlarmUtils.toContentValues(alarm), where, whereArgs);
    }

    public int deleteAlarm(Alarm alarm) {
        return deleteAlarm(alarm.getId());
    }

    int deleteAlarm(long id) {
        final String where = _ID + "=?";
        final String[] whereArgs = new String[] { Long.toString(id) };
        return getWritableDatabase().delete(TABLE_NAME, where, whereArgs);
    }

    public List<Alarm> getAlarms() {

        Cursor c = null;

        try{
            c = getReadableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
            return AlarmUtils.buildAlarmList(c);
        } finally {
            if (c != null && !c.isClosed()) c.close();
        }

    }
}
