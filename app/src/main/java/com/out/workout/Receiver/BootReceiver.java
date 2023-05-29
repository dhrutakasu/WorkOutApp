package com.out.workout.Receiver;

import static android.content.Intent.ACTION_BOOT_COMPLETED;
import static com.out.workout.Receiver.ReminderReceiver.setReminderAlarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.out.workout.Helper.ExerciseHelper;
import com.out.workout.model.ReminderModel;

import java.util.List;
import java.util.concurrent.Executors;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Executors.newSingleThreadExecutor().execute(() -> {
                List<ReminderModel> reminderModels = new ExerciseHelper(context).getAlarms();
                setReminderAlarms(context, reminderModels);
            });
        }
    }
}
