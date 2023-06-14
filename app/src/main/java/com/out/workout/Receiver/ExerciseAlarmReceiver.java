package com.out.workout.Receiver;

import static android.app.NotificationManager.IMPORTANCE_HIGH;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.O;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.out.workout.R;
import com.out.workout.ui.activity.MainActivity;

public class ExerciseAlarmReceiver extends BroadcastReceiver {

    private static String CHANNEL_ID = "alarm_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(BackupWorker.class).addTag("BACKUP_WORKER_TAG").build();
            WorkManager.getInstance(context).enqueue(request);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, AlarmService.class));
        } else {
            context.startService(new Intent(context, AlarmService.class));
        }
    }


    private static void createNotificationChannel(Context ctx) {
        if (SDK_INT < O) return;

        final NotificationManager mgr = ctx.getSystemService(NotificationManager.class);
        if (mgr == null) return;

        final String name = "Reminder Notification";
        if (mgr.getNotificationChannel(name) == null) {
            final NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 500, 1000, 500, 1000, 500});
            channel.setBypassDnd(true);
            mgr.createNotificationChannel(channel);
        }
    }
}
