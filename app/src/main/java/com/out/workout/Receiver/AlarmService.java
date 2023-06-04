package com.out.workout.Receiver;

import static android.app.NotificationManager.IMPORTANCE_HIGH;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.O;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.out.workout.R;
import com.out.workout.ui.activity.MainActivity;

public class  AlarmService extends Service {

    private static String CHANNEL_ID = "alarm_channel";
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("----- come : :service : ");
        context=this;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground()
    {
        final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel(context);

        String string = context.getString(R.string.app_name);
        String string2 = context.getString(R.string.str_noti1);
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            contentPendingIntent = PendingIntent.getBroadcast(context, 100, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        }else {
            contentPendingIntent = PendingIntent.getBroadcast(context, 100, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
//        PendingIntent contentPendingIntent = PendingIntent.getActivity
//                (context, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setColor(ContextCompat.getColor(context, R.color.white));
        builder.setContentTitle(string);
        builder.setContentText(string2);
        builder.setVibrate(new long[]{1000, 500, 1000, 500, 1000, 500});
        builder.setContentIntent(contentPendingIntent);
        builder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification_banner))
                .setBigContentTitle(string).setSummaryText(string2)).setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_HIGH);

        manager.notify(0, builder.build());
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("----- come : :destroy service : ");
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
