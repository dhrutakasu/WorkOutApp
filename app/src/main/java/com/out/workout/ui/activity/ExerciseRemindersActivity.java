package com.out.workout.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.out.workout.Ads.Ad_Native;
import com.out.workout.Helper.ExerciseHelper;
import com.out.workout.R;
import com.out.workout.utils.Constants;
import com.out.workout.utils.SharePreference;

public class ExerciseRemindersActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private TimePicker PickerSetReminders;
    private LinearLayout LlSetReminders;
    private int IntHr = 12;
    private int IntMin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_reminders);

        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        PickerSetReminders = (TimePicker) findViewById(R.id.PickerSetReminders);
        LlSetReminders = (LinearLayout) findViewById(R.id.LlSetReminders);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        LlSetReminders.setOnClickListener(this);
    }

    private void initActions() {
        Ad_Native.getInstance().showNative250(this, findViewById(R.id.FlNative));
        TvTitle.setText(getString(R.string.exercise_time));

        System.out.println("---- : HR " + SharePreference.getInt(context, Constants.NOTIFICATION_HOUR, IntHr));
        System.out.println("---- : MIN " + SharePreference.getInt(context, Constants.NOTIFICATION_MINUTES, IntMin));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PickerSetReminders.setHour(SharePreference.getInt(context, Constants.NOTIFICATION_HOUR, IntHr));
            PickerSetReminders.setMinute(SharePreference.getInt(context, Constants.NOTIFICATION_MINUTES, IntMin));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.LlSetReminders:
                GotoSetReminders();
                break;
        }
    }

    private void GotoSetReminders() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.POST_NOTIFICATIONS)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Toast.makeText(context, "The permission is   granted..", Toast.LENGTH_SHORT).show();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                IntHr = PickerSetReminders.getHour();
                                IntMin = PickerSetReminders.getMinute();
                            } else {
                                IntHr = PickerSetReminders.getCurrentHour().intValue();
                                IntMin = PickerSetReminders.getCurrentMinute().intValue();
                            }
                            Toast.makeText(context, getResources().getString(R.string.time_saved), Toast.LENGTH_SHORT).show();
                            SharePreference.SetBoolean(context, Constants.ExerciseSetTime, true);
                            SharePreference.SetInt(context, Constants.NOTIFICATION_HOUR, IntHr);
                            SharePreference.SetInt(context, Constants.NOTIFICATION_MINUTES, IntMin);
                            Log.d("ReminderCheck", "Reminder set in ExerciseFragment page");
                            Log.d("ReminderCheck", "Reminder set in " + SharePreference.getInt(context, Constants.NOTIFICATION_HOUR, IntHr) + ":" + SharePreference.getInt(context, Constants.NOTIFICATION_MINUTES, IntMin) + ":0");
                            Constants.setAlarm(context, SharePreference.getInt(context, Constants.NOTIFICATION_HOUR, IntHr), SharePreference.getInt(context, Constants.NOTIFICATION_MINUTES, IntMin), 0);
                            onBackPressed();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            showSettingsDialog();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).withErrorListener(error -> {
                    }).onSameThread().check();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                IntHr = PickerSetReminders.getHour();
                IntMin = PickerSetReminders.getMinute();
            } else {
                IntHr = PickerSetReminders.getCurrentHour().intValue();
                IntMin = PickerSetReminders.getCurrentMinute().intValue();
            }
            Toast.makeText(context, getResources().getString(R.string.time_saved), Toast.LENGTH_SHORT).show();
            SharePreference.SetBoolean(context, Constants.ExerciseSetTime, true);
            SharePreference.SetInt(context, Constants.NOTIFICATION_HOUR, IntHr);
            SharePreference.SetInt(context, Constants.NOTIFICATION_MINUTES, IntMin);
            Log.d("ReminderCheck", "Reminder set in ExerciseFragment page");
            Log.d("ReminderCheck", "Reminder set in " + SharePreference.getInt(context, Constants.NOTIFICATION_HOUR, IntHr) + ":" + SharePreference.getInt(context, Constants.NOTIFICATION_MINUTES, IntMin) + ":0");
            Constants.setAlarm(context, SharePreference.getInt(context, Constants.NOTIFICATION_HOUR, IntHr), SharePreference.getInt(context, Constants.NOTIFICATION_MINUTES, IntMin), 0);
            onBackPressed();
        }
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });
        builder.show();
    }
}