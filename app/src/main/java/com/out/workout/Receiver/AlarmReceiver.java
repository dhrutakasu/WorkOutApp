package com.out.workout.Receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.out.workout.R;
import com.out.workout.ui.activity.MainActivity;
import com.out.workout.utils.Constants;
import com.out.workout.utils.SharePreference;

public class AlarmReceiver extends BroadcastReceiver {
    Context context;

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(Constants.NOTIFICATION_ID, "Reminder Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Include all the notifications");
            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(notificationChannel);
        }
    }

    private void setLocalNotification() {
        String string = context.getString(R.string.noti2);
        String string2 = context.getString(R.string.noti1);
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(100,
                        new NotificationCompat.Builder(context, Constants.NOTIFICATION_ID)
                                .setContentIntent(PendingIntent.getActivity(context, 100, new Intent(context, MainActivity.class),
                                        PendingIntent.FLAG_UPDATE_CURRENT))
                                .setSmallIcon(R.drawable.small_noti_icon)
                                .setContentTitle(string)
                                .setContentText(string2)
                                .setVibrate(new long[]{0, 1000, 500, 1000})
                                .setStyle(new NotificationCompat.BigPictureStyle()
                                        .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.notification_banner))
                                        .setBigContentTitle(string).setSummaryText(string2)).setAutoCancel(true)
                                .build());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String string = SharePreference.getString(context, Constants.LANGUAGE, "en");
        Constants.updateLocale(context, string);
        createNotificationChannel(context);
        setLocalNotification();
    }
}

