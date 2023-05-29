package com.out.workout.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.util.SparseBooleanArray;


import com.out.workout.model.ReminderModel;
import com.out.workout.Helper.ExerciseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import androidx.core.app.ActivityCompat;
import static com.out.workout.Helper.ExerciseHelper.*;

public final class AlarmUtils {

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("h:mm", Locale.getDefault());
    private static final SimpleDateFormat AM_PM_FORMAT = new SimpleDateFormat("a", Locale.getDefault());

    private static final int REQUEST_ALARM = 1;
    private static final String[] PERMISSIONS_ALARM = {
            Manifest.permission.VIBRATE
    };

    private AlarmUtils() { throw new AssertionError(); }

    public static void checkAlarmPermissions(Activity activity) {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        final int permission = ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.VIBRATE
        );

        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_ALARM,
                    REQUEST_ALARM
            );
        }
    }

    public static ContentValues toContentValues(ReminderModel reminderModel) {
        final ContentValues cv = new ContentValues(10);
        cv.put(REMINDER_TIME, reminderModel.getTime());
        final SparseBooleanArray days = reminderModel.getDays();
        cv.put(REMINDER_MONDAY, days.get(ReminderModel.MON) ? 1 : 0);
        cv.put(REMINDER_TUESDAY, days.get(ReminderModel.TUES) ? 1 : 0);
        cv.put(REMINDER_WEDNESDAY, days.get(ReminderModel.WED) ? 1 : 0);
        cv.put(REMINDER_THURSDAY, days.get(ReminderModel.THURS) ? 1 : 0);
        cv.put(REMINDER_FRIDAY, days.get(ReminderModel.FRI) ? 1 : 0);
        cv.put(REMINDER_SATURDAY, days.get(ReminderModel.SAT) ? 1 : 0);
        cv.put(REMINDER_SUNDAY, days.get(ReminderModel.SUN) ? 1 : 0);
        cv.put(ExerciseHelper.REMINDER_ON_OFF, reminderModel.isEnabled());
        return cv;
    }

    public static ArrayList<ReminderModel> buildAlarmList(Cursor cursor) {
        if (cursor == null) return new ArrayList<>();
        final int size = cursor.getCount();
        final ArrayList<ReminderModel> reminderModels = new ArrayList<>(size);
        if (cursor.moveToFirst()){
            do {
                final long id = cursor.getLong(cursor.getColumnIndex(REMINDER_ID));
                final long time = cursor.getLong(cursor.getColumnIndex(REMINDER_TIME));
                final boolean mon = cursor.getInt(cursor.getColumnIndex(REMINDER_MONDAY)) == 1;
                final boolean tues = cursor.getInt(cursor.getColumnIndex(REMINDER_TUESDAY)) == 1;
                final boolean wed = cursor.getInt(cursor.getColumnIndex(REMINDER_WEDNESDAY)) == 1;
                final boolean thurs = cursor.getInt(cursor.getColumnIndex(REMINDER_THURSDAY)) == 1;
                final boolean fri = cursor.getInt(cursor.getColumnIndex(REMINDER_FRIDAY)) == 1;
                final boolean sat = cursor.getInt(cursor.getColumnIndex(REMINDER_SATURDAY)) == 1;
                final boolean sun = cursor.getInt(cursor.getColumnIndex(REMINDER_SUNDAY)) == 1;
                final boolean isEnabled = cursor.getInt(cursor.getColumnIndex(REMINDER_ON_OFF)) == 1;

                final ReminderModel reminderModel = new ReminderModel(id, time);
                reminderModel.setDay(ReminderModel.MON, mon);
                reminderModel.setDay(ReminderModel.TUES, tues);
                reminderModel.setDay(ReminderModel.WED, wed);
                reminderModel.setDay(ReminderModel.THURS, thurs);
                reminderModel.setDay(ReminderModel.FRI, fri);
                reminderModel.setDay(ReminderModel.SAT, sat);
                reminderModel.setDay(ReminderModel.SUN, sun);

                reminderModel.setIsEnabled(isEnabled);
                reminderModels.add(reminderModel);
            } while (cursor.moveToNext());
        }
        return reminderModels;
    }

    public static String getReadableTime(long time) {
        return TIME_FORMAT.format(time);
    }
    public static String getAmPm(long time) {
        return AM_PM_FORMAT.format(time);
    }
    public static boolean isAlarmActive(ReminderModel reminderModel) {
        final SparseBooleanArray days = reminderModel.getDays();
        boolean isActive = false;
        int count = 0;
        while (count < days.size() && !isActive) {
            isActive = days.valueAt(count);
            count++;
        }
        return isActive;
    }
}
