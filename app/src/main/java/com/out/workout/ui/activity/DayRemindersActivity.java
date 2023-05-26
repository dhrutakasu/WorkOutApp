package com.out.workout.ui.activity;

import static com.out.workout.utils.Constants.calendar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.out.workout.Helper.ExerciseHelper;
import com.out.workout.R;
import com.out.workout.Receiver.AlarmHelper;
import com.out.workout.model.ReminderModel;
import com.out.workout.ui.adapter.ReminderAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class DayRemindersActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private ExerciseHelper helper;
    private RecyclerView RvAddReminderList;
    private TextView TvNoReminderFound, TvAddReminder;
    private ArrayList<ReminderModel> ReminderRecordsList = new ArrayList<>();
    private AlarmHelper alarmHelper;

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
        alarmHelper = new AlarmHelper(context);
        ReminderRecordsList = helper.getReminderRecords();
        RvAddReminderList.setLayoutManager(new LinearLayoutManager(context));

        ReminderAdapter reminderAdapter = new ReminderAdapter(context, helper.getReminderRecords(), alarmHelper);
        RvAddReminderList.setAdapter(reminderAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                GotoAddReminder();
                break;
        }
    }

    private void GotoAddReminder() {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(context, (timePicker, i, i2) -> {
            if (timePicker.isShown()) {
                Calendar instance = Calendar.getInstance();
                instance.set(Calendar.HOUR_OF_DAY, i);
                instance.set(Calendar.MINUTE, i2);
                Log.d("TAG", "onTimeSet: " + TimeFormat().format(instance.getTime()));
                showDialogPicker(instance);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    private void showDialogPicker(Calendar calendar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.days));
        final ArrayList<Integer> list = new ArrayList();
        builder.setMultiChoiceItems(getResources().getStringArray(R.array.day_list), (boolean[]) null, (dialogInterface, valueOf, z) -> {
            if (z) {
                list.add(Integer.valueOf(valueOf));
            } else if (list.contains(Integer.valueOf(valueOf))) {
                list.remove(Integer.valueOf(valueOf));
            }
        });
        builder.setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> SelectionDaysList(list, calendar, dialogInterface));
        builder.setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }

    private void SelectionDaysList(ArrayList arrayList, Calendar calendar, DialogInterface anInterface) {
        if (arrayList.size() > 0) {
            anInterface.dismiss();
            ReminderModel reminderModel = new ReminderModel();
            reminderModel.setTime(TimeFormat().format(calendar.getTime()));
            System.out.println("---- - -Arrays " + Arrays.toString(arrayList.toArray()));
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).equals(0)) {
                    reminderModel.setMon("true");
                } else if (arrayList.get(i).equals(1)) {
                    reminderModel.setTue("true");
                } else if (arrayList.get(i).equals(2)) {
                    reminderModel.setWed("true");
                } else if (arrayList.get(i).equals(3)) {
                    reminderModel.setThu("true");
                } else if ( arrayList.get(i).equals(4)) {
                    reminderModel.setFri("true");
                } else if (arrayList.get(i).equals(5)) {
                    reminderModel.setSat("true");
                } else if (arrayList.get(i).equals(6)) {
                    reminderModel.setSun("true");
                }
            }
            SetAlarmRepeat(alarmHelper, calendar);
            reminderModel.setOnOff("true");

            helper.insertReminder(reminderModel);
            RvAddReminderList.setVisibility(View.VISIBLE);
            ReminderAdapter reminderAdapter = new ReminderAdapter(context, helper.getReminderRecords(), alarmHelper);
            RvAddReminderList.setAdapter(reminderAdapter);
            TvNoReminderFound.setVisibility(View.GONE);
            return;
        }
        Toast.makeText(context, getResources().getString(R.string.please_select_at_least_one_day), Toast.LENGTH_SHORT).show();
    }

    private void SetAlarmRepeat(AlarmHelper alarmHelper, Calendar calendar) {
        int IntHr;
        int IntMin;
        int AM_PM;
        if (TimeFormat().format(calendar.getTime()).endsWith("AM")) {
            IntHr = Integer.parseInt(new SimpleDateFormat("hh").format(calendar.getTime()));
            IntMin = Integer.parseInt(new SimpleDateFormat("mm").format(calendar.getTime()));
            AM_PM = 0;
        } else {
            IntHr = Integer.parseInt(new SimpleDateFormat("hh").format(calendar.getTime()));
            IntMin = Integer.parseInt(new SimpleDateFormat("mm").format(calendar.getTime()));
            AM_PM = 1;
        }
        alarmHelper.schedulePendingIntent(IntHr, IntMin, AM_PM);
    }

    public SimpleDateFormat TimeFormat() {
        return new SimpleDateFormat("hh:mm a");
    }
}