package com.out.workout.Receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.out.workout.Helper.ExerciseHelper;
import com.out.workout.model.ReminderModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class FemaleFitnessBootReceiver extends BroadcastReceiver {
    String TAG = "FemaleFitnessBootReceiver";
    AlarmHelper alarmHelper;
    Context context;

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(context, NotificationPublisher.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getBroadcast(context, 1234, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String str = TAG;
        Log.d(str, "onReceive - Intent Action: " + intent.getAction());
        this.context = context;
        setAlarm(context);
    }

    public void setAlarm(Context context) {
        ExerciseHelper exerciseHelper = new ExerciseHelper(context);
        ArrayList<ReminderModel> list = exerciseHelper.getReminderRecords();
        if (list != null) {
            alarmHelper = new AlarmHelper(context);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            for (int i = 0; i < list.size(); i++) {
                try {
                    calendar.setTime(simpleDateFormat.parse(((ReminderModel) list.get(i)).getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String str = TAG;
                Log.d(str, "setAlarm: hr===" + calendar.get(Calendar.HOUR) + "min==" + calendar.get(Calendar.MINUTE));
                int IntHr,IntMin,AM_PM;
                Log.d(str, "hr" + Integer.parseInt(new SimpleDateFormat("hh").format(calendar.getTime())));
                Log.d(str, "min" + Integer.parseInt(new SimpleDateFormat("mm").format(calendar.getTime())));
                if (new SimpleDateFormat("hh:mm a").format(calendar.getTime()).endsWith("AM")) {
                    Log.d(str, "am");
                    IntHr = Integer.parseInt(new SimpleDateFormat("hh").format(calendar.getTime()));
                    IntMin = Integer.parseInt(new SimpleDateFormat("mm").format(calendar.getTime()));
                    AM_PM = 0;
                } else {
                    Log.d(str, "pm");
                    IntHr = Integer.parseInt(new SimpleDateFormat("hh").format(calendar.getTime()));
                    IntMin = Integer.parseInt(new SimpleDateFormat("mm").format(calendar.getTime()));
                    AM_PM = 1;
                }
                Calendar instance = Calendar.getInstance();
                instance.set(Calendar.HOUR_OF_DAY, IntHr);
                instance.set(Calendar.MINUTE, IntMin);
                instance.set(Calendar.SECOND, 0);
                instance.set(Calendar.MILLISECOND, 0);
                instance.set(Calendar.AM_PM, AM_PM);
                Log.d(str, "scheduling_PendingIntent: " + instance.getTimeInMillis() + "/" + getPendingIntent());
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d(str, "setExactAndAllowWhileIdle");
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), getPendingIntent());
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.d(str, "setExact");
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), getPendingIntent());
                } else {
                    Log.d(str, "set");
                    alarmManager.set(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), getPendingIntent());
                }
                context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, FemaleFitnessBootReceiver.class), i, PackageManager.DONT_KILL_APP);
            }
        }
    }
}
