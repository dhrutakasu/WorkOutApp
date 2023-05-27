package com.out.workout.Receiver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.out.workout.Helper.ExerciseHelper;
import com.out.workout.R;
import com.out.workout.model.ReminderModel;
import com.out.workout.ui.activity.MainActivity;
import com.out.workout.utils.Constants;
import com.out.workout.utils.SharePreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class NotificationPublisher extends BroadcastReceiver {
    static final String PREFERENCE_LAST_NOTIF_ID = "PREFERENCE_LAST_NOTIF_ID";
    String TAG = "NotificationPublisher";
    AlarmHelper alarmHelper;
    Context context;
    List<ReminderModel> models;
    SimpleDateFormat startTimeFormat;

    private int getNextNotifId() {
        int i = SharePreference.getInt(context, PREFERENCE_LAST_NOTIF_ID, 0) + 1;
        int i2 = i != Integer.MAX_VALUE ? i : 0;
        SharePreference.SetInt(context, PREFERENCE_LAST_NOTIF_ID, i2);
        return i2;
    }

    @SuppressLint("RestrictedApi")
    private void startNotification(Context context) {
        Constants.updateLocale(context, SharePreference.getString(context, Constants.LANGUAGE, "en"));
        PendingIntent existAlarm = alarmHelper.existAlarm(SharePreference.getInt(context, PREFERENCE_LAST_NOTIF_ID, 0));
        if (existAlarm != null) {
            existAlarm.cancel();
        }
        if (Build.VERSION.SDK_INT < 26) {
            String string = context.getString(R.string.noti3);
            String string2 = context.getString(R.string.noti4);
            Intent intent = new Intent(context, MainActivity.class);
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(getNextNotifId(), new Notification.Builder(context).setContentIntent(PendingIntent.getActivity(context, getNextNotifId(), intent, PendingIntent.FLAG_UPDATE_CURRENT)).setSmallIcon(R.drawable.small_noti_icon).setAutoCancel(true).setWhen(System.currentTimeMillis()).setContentTitle(string).setContentText(string2).setDefaults(1).build());
            return;
        }
        String string3 = context.getString(R.string.noti3);
        String string4 = context.getString(R.string.noti4);
        Intent intent2 = new Intent(context, MainActivity.class);
        intent2.setAction("android.intent.action.MAIN");
        intent2.addCategory("android.intent.category.LAUNCHER");
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel("my_channel_id_01", "My Notifications", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription("Channel description");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(SupportMenu.CATEGORY_MASK);
        notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
        notificationChannel.enableVibration(true);
        notificationManager.createNotificationChannel(notificationChannel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "my_channel_id_01");
        builder.setAutoCancel(true).setContentIntent(PendingIntent.getActivity(context, getNextNotifId(), intent2, PendingIntent.FLAG_UPDATE_CURRENT)).setDefaults(-1).setAutoCancel(true).setWhen(System.currentTimeMillis()).setSmallIcon(R.drawable.small_noti_icon).setContentTitle(string3).setContentText(string4).setDefaults(1);
        notificationManager.notify(getNextNotifId(), builder.build());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive ==========" + intent.getAction());
        this.context = context;
        models = new ExerciseHelper(context).getReminderRecords();
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.HOUR_OF_DAY);
        calendar.get(Calendar.MINUTE);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        List<ReminderModel> list = models;
        if (list == null || list.size() <= 0) {
            return;
        }
        alarmHelper = new AlarmHelper(context);
        for (int i2 = 0; i2 < models.size(); i2++) {
            System.out.println("===== models : " + models.get(i2).toString() + " bool : " + i);
            if (models.get(i2).getTime().equals(startTimeFormat().format(calendar.getTime())) &&
                    models.get(i2).getOnOff().equalsIgnoreCase("true")) {
                if ((models.get(i2).getSun().equalsIgnoreCase("true") && i == 1) ||
                        ((models.get(i2).getMon().equalsIgnoreCase("true") && i == 2) ||
                                ((models.get(i2).getTue().equalsIgnoreCase("true") && i == 3) ||
                                        ((models.get(i2).getWed().equalsIgnoreCase("true") && i == 4) ||
                                                ((models.get(i2).getThu().equalsIgnoreCase("true") && i == 5) ||
                                                        ((models.get(i2).getFri().equalsIgnoreCase("true") && i == 6) ||
                                                                (models.get(i2).getSat().equalsIgnoreCase("true") && i == 7))))))) {
                    startNotification(context);
                }
                AlarmHelper alarmHelper = new AlarmHelper(context);
                int IntHr, IntMin, AMPM;
                if (startTimeFormat().format(calendar.getTime()).endsWith("AM")) {
                    IntHr = Integer.parseInt(new SimpleDateFormat("hh", Locale.ENGLISH).format(calendar.getTime()));
                    IntMin = Integer.parseInt(new SimpleDateFormat("mm", Locale.ENGLISH).format(calendar.getTime()));
                    AMPM = 0;
                } else if (!startTimeFormat().format(calendar.getTime()).equalsIgnoreCase("PM")) {
                    return;
                } else {
                    IntHr = Integer.parseInt(new SimpleDateFormat("hh", Locale.ENGLISH).format(calendar.getTime()));
                    IntMin = Integer.parseInt(new SimpleDateFormat("mm", Locale.ENGLISH).format(calendar.getTime()));
                    AMPM = 1;
                }
                alarmHelper.schedulePendingIntent(IntHr, IntMin, AMPM, 86400000L);
            }
        }
        String action = intent.getAction();
        Objects.requireNonNull(action);
        if (action.equals("android.intent.action.TIME_SET")) {
            for (int k = 0; k < models.size(); k++) {
                Log.d(TAG, "onReceive: +++++++++++++" + models.get(k).getTime());
                if (models.get(k).getTime().toUpperCase().endsWith("AM") || models.get(k).getTime().toLowerCase().endsWith("am") || models.get(k).getTime().toUpperCase().endsWith("a.m.") || models.get(k).getTime().toLowerCase().endsWith("a.m.")) {
                    alarmHelper.schedulePendingIntent(Integer.parseInt(models.get(k).getTime().substring(0, 2)), Integer.parseInt(models.get(k).getTime().substring(3, 5)), 0);
                } else if (models.get(k).getTime().toUpperCase().endsWith("PM") || models.get(k).getTime().toUpperCase().endsWith("pm") || models.get(k).getTime().toUpperCase().endsWith("p.m.") || models.get(k).getTime().toLowerCase().endsWith("p.m.")) {
                    alarmHelper.schedulePendingIntent(Integer.parseInt(models.get(k).getTime().substring(0, 2)), Integer.parseInt(models.get(k).getTime().substring(3, 5)), 1);
                }
            }
        }
    }

    public SimpleDateFormat startTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        startTimeFormat = simpleDateFormat;
        return simpleDateFormat;
    }
}
