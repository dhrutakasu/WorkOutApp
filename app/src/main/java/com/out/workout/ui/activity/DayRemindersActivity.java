package com.out.workout.ui.activity;

import static com.out.workout.ui.activity.AddOrEditAlarmActivity.buildAddEditAlarmActivityIntent;
import static com.out.workout.utils.Constants.ADD_ALARM;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.out.workout.Helper.ExerciseHelper;
import com.out.workout.R;
import com.out.workout.model.ReminderModel;
import com.out.workout.ui.adapter.RemindersAdapter;
import com.out.workout.utils.AlarmUtils;
import com.out.workout.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class DayRemindersActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private ExerciseHelper helper;
    private RecyclerView RvAddReminderList;
    private TextView TvNoReminderFound, TvAddReminder;
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
        TvAddReminder = (TextView) findViewById(R.id.TvAddReminder);
        TvNoReminderFound = (TextView) findViewById(R.id.TvNoReminderFound);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        TvAddReminder.setOnClickListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.Day_Planner_Reminder));
        helper = new ExerciseHelper(context);

        RvAddReminderList.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new RemindersAdapter();
        RvAddReminderList.setAdapter(mAdapter);
        RvAddReminderList.addItemDecoration(new DividerItemDecoration(context));
        RvAddReminderList.setItemAnimator(new DefaultItemAnimator());
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
            case R.id.TvAddReminder:
                AlarmUtils.checkAlarmPermissions(DayRemindersActivity.this);
                final Intent i = buildAddEditAlarmActivityIntent(context, ADD_ALARM);
                startActivity(i);
                break;
        }
    }
}