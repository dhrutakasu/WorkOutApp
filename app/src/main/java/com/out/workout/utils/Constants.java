package com.out.workout.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.out.workout.Receiver.AlarmReceiver;
import com.out.workout.model.WorkoutExerciseModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
    public static String LANGUAGE = "Language";
    public static String NOTIFICATION_ID = "set_reminder_notification";

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
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.getIntExtra("RequestCode", 100);
        alarmManager.cancel(PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        Log.d("Tag", "previous alarm canceled");
        Log.d("Tag", "new alarm sets");
        PendingIntent broadcast = PendingIntent.getBroadcast(context.getApplicationContext(), 100, new Intent(context.getApplicationContext(), AlarmReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 86400000L, broadcast);
        }
    }

    public static void updateLocale(Context context, String string) {
        Locale locale = new Locale(string);
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }
}
