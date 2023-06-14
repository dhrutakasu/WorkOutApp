package com.out.workout.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.out.workout.model.ReminderModel;
import com.out.workout.Helper.ExerciseHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class AlarmsService extends IntentService {

    private static String TAG = AlarmsService.class.getSimpleName();
    public static String ACTION_COMPLETE = TAG + ".ACTION_COMPLETE";
    public static String ALARMS_EXTRA = "alarms_extra";

    @SuppressWarnings("unused")
    public AlarmsService() {
        this(TAG);
    }

    public AlarmsService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<ReminderModel> reminderModels = new ExerciseHelper(this).getAlarms();
        Intent i = new Intent(ACTION_COMPLETE);
        i.putParcelableArrayListExtra(ALARMS_EXTRA, new ArrayList<>(reminderModels));
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    public static void launchLoadAlarmsService(Context context) {
        Intent launchLoadAlarmsServiceIntent = new Intent(context, AlarmsService.class);
            context.startService(launchLoadAlarmsServiceIntent);
    }
}
