package com.out.workout.ui.activity;

import static com.out.workout.ui.activity.AddOrEditAlarmActivity.buildAddEditAlarmActivityIntent;
import static com.out.workout.utils.Constants.ADD_ALARM;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdSize;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.Helper.ExerciseHelper;
import com.out.workout.R;
import com.out.workout.model.ReminderModel;
import com.out.workout.ui.adapter.RemindersAdapter;
import com.out.workout.utils.AlarmUtils;
import com.out.workout.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class DayRemindersActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private ExerciseHelper helper;
    private RecyclerView RvAddReminderList;
    private TextView TvNoReminderFound;
    private ImageView IvAddReminder;
    private List<ReminderModel> ReminderRecordsList = new ArrayList<>();
    private RemindersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_reminders);

        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        RvAddReminderList = (RecyclerView) findViewById(R.id.RvAddReminderList);
        IvAddReminder = (ImageView) findViewById(R.id.IvAddReminder);
        TvNoReminderFound = (TextView) findViewById(R.id.TvNoReminderFound);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        IvAddReminder.setOnClickListener(this);
    }

    private void initActions() {
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        TvTitle.setText(getString(R.string.Day_Planner_Reminder));
        helper = new ExerciseHelper(context);

        RvAddReminderList.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new RemindersAdapter();
        RvAddReminderList.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ReminderRecordsList = helper.getAlarms();
        mAdapter.setAlarmItems(ReminderRecordsList);
        if (ReminderRecordsList.size() > 0) {
            RvAddReminderList.setVisibility(View.VISIBLE);
            TvNoReminderFound.setVisibility(View.GONE);
        } else {
            RvAddReminderList.setVisibility(View.GONE);
            TvNoReminderFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.IvAddReminder:
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU) {
                    Dexter.withActivity(this)
                            .withPermission(Manifest.permission.POST_NOTIFICATIONS)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                    Toast.makeText(context, "The permission is   granted..", Toast.LENGTH_SHORT).show();

                                    AlarmUtils.checkAlarmPermissions(DayRemindersActivity.this);
                                    final Intent i = buildAddEditAlarmActivityIntent(context, ADD_ALARM);
                                    startActivity(i);
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
                }else {
                    AlarmUtils.checkAlarmPermissions(DayRemindersActivity.this);
                    final Intent i = buildAddEditAlarmActivityIntent(context, ADD_ALARM);
                    startActivity(i);
                }
                break;
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