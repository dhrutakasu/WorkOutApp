package com.out.workout.Receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

public class AlarmHelper {

    static final String PREFERENCE_LAST_REQUEST_CODE = "PREFERENCE_LAST_REQUEST_CODE";
    static final int REQUEST_CODE = 1234;
    static final String TAG = "AlarmMainActivity";
    AlarmManager alarmManager;
    Context context;
    SharedPreferences sharedPreferences;

    public AlarmHelper(Context co) {
        context = co;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private int getNextRequestCode() {
        int i = sharedPreferences.getInt(PREFERENCE_LAST_REQUEST_CODE, 0) + 1;
        int i2 = i != Integer.MAX_VALUE ? i : 0;
        sharedPreferences.edit().putInt(PREFERENCE_LAST_REQUEST_CODE, i2).apply();
        return i2;
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(context, NotificationPublisher.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getBroadcast(context, getNextRequestCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public PendingIntent existAlarm(int i) {
        Intent intent = new Intent(context, NotificationPublisher.class);
        return PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public boolean isAlarmScheduled() {
        Intent intent = new Intent(context, NotificationPublisher.class);
        return PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT) != null;
    }

    public void schedulePendingIntent(int hr, int min, int am_pm) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hr);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.AM_PM, am_pm);
        schedulePendingIntent(calendar.getTimeInMillis(), getPendingIntent());
    }

    public void schedulePendingIntent(int i, int i2, int i3, long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, i);
        calendar.set(Calendar.MINUTE, i2);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.AM_PM, i3);
        schedulePendingIntent(calendar.getTimeInMillis(), getPendingIntent(), j);
    }

    public void schedulePendingIntent(long j, PendingIntent pendingIntent) {
        Log.d(TAG, "schedulePendingIntent: " + j + "/" + pendingIntent);
      /*  if(SDK_INT > LOLLIPOP) {
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(j, launchAlarmLandingPage(ctx, alarm)), pi);
        } else if(SDK_INT > KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, j, pi);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, j, pi);
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d(TAG, "setExactAndAllowWhileIdle");
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, j, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.d(TAG, "setExact");
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, j, pendingIntent);
        } else {
            Log.d(TAG, "set");
            alarmManager.set(AlarmManager.RTC_WAKEUP, j, pendingIntent);
        }
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, FemaleFitnessBootReceiver.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    private static PendingIntent launchAlarmLandingPage(Context ctx, long alarm) {
        return PendingIntent.getActivity(
                ctx, (int) alarm, launchIntent(ctx), FLAG_UPDATE_CURRENT
        );
    }

    private static Intent launchIntent(Context ctx) {
        Intent intent = new Intent(ctx, NotificationPublisher.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public void schedulePendingIntent(long j, PendingIntent pendingIntent, long j2) {
        Log.d(TAG, "schedulePendingIntent: " + j + "/" + pendingIntent);
/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d(TAG, "setExactAndAllowWhileIdle");
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, j + j2, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.d(TAG, "setExact");
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, j + j2, pendingIntent);
        } else {
            Log.d(TAG, "set");
            alarmManager.set(AlarmManager.RTC_WAKEUP, j + j2, pendingIntent);
        }
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, FemaleFitnessBootReceiver.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

  */
        if (SDK_INT > LOLLIPOP) {
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(j, launchAlarmLandingPage(context, j2)), pendingIntent);
        } else if (SDK_INT > KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, j, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, j, pendingIntent);
        }
    }

    public void unschedulePendingIntent() {
        PendingIntent pendingIntent = getPendingIntent();
        pendingIntent.cancel();
        alarmManager.cancel(pendingIntent);
    }
}
