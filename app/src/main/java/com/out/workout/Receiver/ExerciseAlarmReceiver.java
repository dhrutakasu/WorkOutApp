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
        System.out.println("----- come : :Exercise receiver : ");
        System.out.println("---- alarm : " + intent.getIntExtra("RequestCode", 0));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(BackupWorker.class).addTag("BACKUP_WORKER_TAG").build();
            WorkManager.getInstance(context).enqueue(request);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, AlarmService.class));
        } else {
            context.startService(new Intent(context, AlarmService.class));
        }
//        final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        createNotificationChannel(context);
//
//        String string = context.getString(R.string.app_name);
//        String string2 = context.getString(R.string.str_noti1);
//        NotificationCompat.Builder builder;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
//        } else {
//            builder = new NotificationCompat.Builder(context);
//        }
//        Intent contentIntent = new Intent(context, MainActivity.class);
//        PendingIntent contentPendingIntent = PendingIntent.getActivity
//                (context, intent.getIntExtra("RequestCode",0), contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
////        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setColor(ContextCompat.getColor(context, R.color.white));
//        builder.setContentTitle(string);
//        builder.setContentText(string2);
//        builder.setVibrate(new long[]{1000, 500, 1000, 500, 1000, 500});
//        builder.setContentIntent(contentPendingIntent);
//        builder.setStyle(new NotificationCompat.BigPictureStyle()
//                .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification_banner))
//                .setBigContentTitle(string).setSummaryText(string2)).setAutoCancel(true);
//        builder.setPriority(Notification.PRIORITY_HIGH);
//
//        manager.notify(intent.getIntExtra("RequestCode",0), builder.build());
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
