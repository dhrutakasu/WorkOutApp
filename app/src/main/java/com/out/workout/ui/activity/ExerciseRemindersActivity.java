package com.out.workout.ui.activity;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.ads.AdSize;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.R;
import com.out.workout.utils.Constants;
import com.out.workout.utils.Pref;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));
        TvTitle.setText(getString(R.string.str_exercise_time));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PickerSetReminders.setHour(new Pref(context).getInt( Constants.NOTIFICATION_HOUR, IntHr));
            PickerSetReminders.setMinute(new Pref(context).getInt( Constants.NOTIFICATION_MINUTES, IntMin));
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
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                IntHr = PickerSetReminders.getHour();
                                IntMin = PickerSetReminders.getMinute();
                            } else {
                                IntHr = PickerSetReminders.getCurrentHour().intValue();
                                IntMin = PickerSetReminders.getCurrentMinute().intValue();
                            }
                            new Pref(context).putBoolean( Constants.ExerciseSetTime, true);
                            new Pref(context).putInt( Constants.NOTIFICATION_HOUR, IntHr);
                            new Pref(context).putInt( Constants.NOTIFICATION_MINUTES, IntMin);
                            Log.d("ReminderCheck", "Reminder set in ExerciseFragment page");
                            Log.d("ReminderCheck", "Reminder set in " + new Pref(context).getInt( Constants.NOTIFICATION_HOUR, IntHr) + ":" + new Pref(context).getInt( Constants.NOTIFICATION_MINUTES, IntMin) + ":0");
                            Constants.setAlarm(context, new Pref(context).getInt( Constants.NOTIFICATION_HOUR, IntHr), new Pref(context).getInt( Constants.NOTIFICATION_MINUTES, IntMin), 0);
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
            new Pref(context).putBoolean( Constants.ExerciseSetTime, true);
            new Pref(context).putInt( Constants.NOTIFICATION_HOUR, IntHr);
            new Pref(context).putInt( Constants.NOTIFICATION_MINUTES, IntMin);
            Log.d("ReminderCheck", "Reminder set in ExerciseFragment page");
            Log.d("ReminderCheck", "Reminder set in " + new Pref(context).getInt( Constants.NOTIFICATION_HOUR, IntHr) + ":" + new Pref(context).getInt( Constants.NOTIFICATION_MINUTES, IntMin) + ":0");
            Constants.setAlarm(context, new Pref(context).getInt( Constants.NOTIFICATION_HOUR, IntHr), new Pref(context).getInt( Constants.NOTIFICATION_MINUTES, IntMin), 0);
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