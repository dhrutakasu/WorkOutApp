package com.out.workout.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.WindowManager;

import com.out.workout.Receiver.ReminderReceiver;
import com.out.workout.model.WorkoutExerciseModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class Constants {
    public static String WorkoutType = "WorkoutType";
    public static String ChartType = "ChartType";
    public static String ExerciseImage = "ExerciseImage";
    public static String ExerciseName = "ExerciseName";
    public static String ExerciseDesc = "ExerciseDesc";
    public static String ExercisePos = "ExercisePos";
    public static String ExerciseDays = "ExerciseDays";
    public static String ExerciseRotate = "ExerciseRotate";
    public static String WorkList = "WorkList";
    public static String Count = "Count";
    public static ArrayList<WorkoutExerciseModel> WorkExerciseList = new ArrayList<>();
    public static String ExerciseCount = "ExerciseCount";
    public static String ExerciseTime = "ExerciseTime";
    public static String NOTIFICATION_HOUR = "NotificationHour";
    public static String NOTIFICATION_MINUTES = "NotificationMinutes";
    public static String ExerciseSetTime;
    public static Calendar calendar = Calendar.getInstance();
    public static AlarmManager alarmManager;

    public static final String ALARM_EXTRA = "alarm_extra";
    public static final String MODE_EXTRA = "mode_extra";
    public static final String DIET_NAME = "DietName";
    public static final String DIET_IMG = "DietImg";
    public static final String DIET_SLUG = "DietSlug";

    public static final int EDIT_ALARM = 1;
    public static final int ADD_ALARM = 2;
    public static final int UNKNOWN = 0;
    public static String BMR="BMR";

    public static String getCapsSentences(String tagName) {
        String[] splits = tagName.toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < splits.length; i++) {
            String eachWord = splits[i];
            if (i > 0 && eachWord.length() > 0) {
                sb.append(" ");
            }
            String cap = eachWord.substring(0, 1).toUpperCase()
                    + eachWord.substring(1);
            sb.append(cap);
        }
        return sb.toString();
    }

    public static void setAlarm(Context context, int i, int i2, int i3) {
        Log.d("TimeSet", "Time" + i + ":" + i2 + ":" + i3);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        calendar.set(Calendar.HOUR_OF_DAY, i);
        calendar.set(Calendar.MINUTE, i2);
        calendar.set(Calendar.SECOND, i3);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.getIntExtra("RequestCode", 100);
        alarmManager.cancel(PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        Log.d("Tag", "previous alarm canceled");
        Log.d("Tag", "new alarm sets");
        PendingIntent broadcast = PendingIntent.getBroadcast(context.getApplicationContext(), 100, new Intent(context.getApplicationContext(), ReminderReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 86400000L, broadcast);
        }
    }

    public static final int getScreenWidth(Context context) {
        WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) systemService).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static final int dpToPx(Context context, int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }


    public static final float toCm(int i) {
        try {
            String format = String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(i * 2.54d)}, 1));
            return (float) Double.parseDouble(format);
        } catch (NumberFormatException unused) {
            return 1.0f;
        }
    }

    public static final Pair<Integer, Integer> toFeetAndInches(float f) {
        double inches = toInches(f);
        int i = (int) (inches / 12.0d);
        int roundToInt = Math.toIntExact(Math.round(inches % 12.0d));
        if (roundToInt == 12) {
            roundToInt = 0;
            i++;
        }
        return new Pair<>(Integer.valueOf(i), Integer.valueOf(roundToInt));
    }

    public static final double toInches(float f) {
        return f / 2.54d;
    }
    public static final String toFormattedCm(float f) {
        String format = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(f)}, 1));
        return format;
    }

    public static final float toKg(float f) {
        double d;
        try {
            String format = String.format("%.2f", Arrays.copyOf(new Object[]{Double.valueOf(f / 2.205d)}, 1));
            d = Double.parseDouble(format);
        } catch (NumberFormatException unused) {
            d = 1.0d;
        }
        return (float) d;
    }

    public static final float toLb(float f) {
        double d;
        try {
            String format = String.format("%.2f", Arrays.copyOf(new Object[]{Double.valueOf(f * 2.205d)}, 1));
            d = Double.parseDouble(format);
        } catch (NumberFormatException unused) {
            d = 2.0d;
        }
        return (float) d;
    }

}
