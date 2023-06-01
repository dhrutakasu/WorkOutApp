package com.out.workout.Receiver;

import android.app.AlarmManager;
import android.app.AlarmManager.AlarmClockInfo;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;

import com.out.workout.model.ReminderModel;
import com.out.workout.utils.AlarmUtils;
import com.out.workout.R;
import com.out.workout.ui.activity.MainActivity;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import static android.app.NotificationManager.IMPORTANCE_HIGH;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.O;

public class ReminderReceiver extends BroadcastReceiver {

    private static String TAG = ReminderReceiver.class.getSimpleName();
    private static String CHANNEL_ID = "alarm_channel";

    private static String BUNDLE_EXTRA = "bundle_extra";
    private static String ALARM_KEY = "alarm_key";

    @Override
    public void onReceive(Context context, Intent intent) {

        final ReminderModel reminderModel = intent.getBundleExtra(BUNDLE_EXTRA).getParcelable(ALARM_KEY);
        if (reminderModel == null) {
            Log.e(TAG, "Alarm is null", new NullPointerException());
            return;
        }

        final int id = reminderModel.notificationId();

        final NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel(context);

        String string = context.getString(R.string.app_name);
        String string2 = context.getString(R.string.noti1);
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setColor(ContextCompat.getColor(context, R.color.white));
        builder.setContentTitle(string);
        builder.setContentText(string2);
        builder.setVibrate(new long[]{1000, 500, 1000, 500, 1000, 500});
        builder.setContentIntent(launchAlarmLandingPage(context, reminderModel));
        builder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification_banner))
                .setBigContentTitle(string).setSummaryText(string2)).setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_HIGH);

        manager.notify(id, builder.build());

        setReminderAlarm(context, reminderModel);
    }

    public static void setReminderAlarm(Context context, ReminderModel reminderModel) {

        if (!AlarmUtils.isAlarmActive(reminderModel)) {
            cancelReminderAlarm(context, reminderModel);
            return;
        }
        System.out.println("----- - - - : "+ reminderModel.toString());
        final Calendar nextAlarmTime = getTimeForNextAlarm(reminderModel);
        reminderModel.setTime(nextAlarmTime.getTimeInMillis());

        final Intent intent = new Intent(context, ReminderReceiver.class);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(ALARM_KEY, reminderModel);
        intent.putExtra(BUNDLE_EXTRA, bundle);
        PendingIntent pIntent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pIntent = PendingIntent.getBroadcast(context,
                    reminderModel.notificationId(),
                    intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        }else {
            pIntent = PendingIntent.getActivity(context,
                    reminderModel.notificationId(),
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

        }
//        final PendingIntent pIntent = PendingIntent.getBroadcast(
//                context,
//                reminderModel.notificationId(),
//                intent,
//                FLAG_UPDATE_CURRENT
//        );

        ScheduleAlarm.with(context).schedule(reminderModel, pIntent);
    }

    public static void setReminderAlarms(Context context, List<ReminderModel> reminderModels) {
        for (ReminderModel reminderModel : reminderModels) {
            setReminderAlarm(context, reminderModel);
        }
    }

    private static Calendar getTimeForNextAlarm(ReminderModel reminderModel) {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(reminderModel.getTime());
        final long currentTime = System.currentTimeMillis();
        final int startIndex = getStartIndexFromTime(calendar);
        int count = 0;
        boolean isAlarmSetForDay;
        final SparseBooleanArray daysArray = reminderModel.getDays();
        do {
            final int index = (startIndex + count) % 7;
            isAlarmSetForDay =
                    daysArray.valueAt(index) && (calendar.getTimeInMillis() > currentTime);
            if (!isAlarmSetForDay) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                count++;
            }
        } while (!isAlarmSetForDay && count < 7);
        return calendar;
    }

    public static void cancelReminderAlarm(Context context, ReminderModel reminderModel) {
        final Intent intent = new Intent(context, ReminderReceiver.class);
//        final PendingIntent pIntent = PendingIntent.getBroadcast(
//                context,
//                reminderModel.notificationId(),
//                intent,
//                FLAG_UPDATE_CURRENT
//        );
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(
                    context,
                    reminderModel.notificationId(),
                    intent,
                    FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);

        }else {
            pendingIntent = PendingIntent.getBroadcast(
                    context,
                    reminderModel.notificationId(),
                    intent,
                    FLAG_UPDATE_CURRENT);

        }
        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    private static int getStartIndexFromTime(Calendar c) {
        final int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        int startIndex = 0;
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                startIndex = 0;
                break;
            case Calendar.TUESDAY:
                startIndex = 1;
                break;
            case Calendar.WEDNESDAY:
                startIndex = 2;
                break;
            case Calendar.THURSDAY:
                startIndex = 3;
                break;
            case Calendar.FRIDAY:
                startIndex = 4;
                break;
            case Calendar.SATURDAY:
                startIndex = 5;
                break;
            case Calendar.SUNDAY:
                startIndex = 6;
                break;
        }
        return startIndex;
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

    private static PendingIntent launchAlarmLandingPage(Context ctx, ReminderModel reminderModel) {
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(
                    ctx, reminderModel.notificationId(), launchIntent(ctx), FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);

        }else {
            pendingIntent = PendingIntent.getActivity(
                    ctx, reminderModel.notificationId(), launchIntent(ctx), FLAG_UPDATE_CURRENT);

        }
//        return PendingIntent.getActivity(
//                ctx, reminderModel.notificationId(), launchIntent(ctx), FLAG_UPDATE_CURRENT
//        );
        return pendingIntent;
    }

    private static Intent launchIntent(Context ctx) {
        final Intent i = new Intent(ctx, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    private static class ScheduleAlarm {
        @NonNull
        private final Context ctx;
        @NonNull
        private final AlarmManager am;

        private ScheduleAlarm(@NonNull AlarmManager am, @NonNull Context ctx) {
            this.am = am;
            this.ctx = ctx;
        }

        static ScheduleAlarm with(Context context) {
            final AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (am == null) {
                throw new IllegalStateException("AlarmManager is null");
            }
            return new ScheduleAlarm(am, context);
        }

        void schedule(ReminderModel reminderModel, PendingIntent pi) {
            if (SDK_INT > LOLLIPOP) {
                am.setAlarmClock(new AlarmClockInfo(reminderModel.getTime(), launchAlarmLandingPage(ctx, reminderModel)), pi);
            } else if (SDK_INT > KITKAT) {
                am.setExact(AlarmManager.RTC_WAKEUP, reminderModel.getTime(), pi);
            } else {
                am.set(AlarmManager.RTC_WAKEUP, reminderModel.getTime(), pi);
            }
        }
    }
}
