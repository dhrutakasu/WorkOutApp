package com.out.workout.ui.activity;

import static com.out.workout.utils.Constants.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.out.workout.model.ReminderModel;
import com.out.workout.Receiver.ReminderReceiver;
import com.out.workout.service.AlarmsService;
import com.out.workout.utils.Constants;
import com.out.workout.utils.ViewUtils;
import com.out.workout.Helper.ExerciseHelper;
import com.out.workout.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

public class AddOrEditAlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack, IvDelete, IvSave;
    private TextView TvTitle;
    private ExerciseHelper helper;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EDIT_ALARM, ADD_ALARM, UNKNOWN})
    @interface Mode {
    }
    private TimePicker PickerTime;
    private CheckBox CbMon, CbTues, CbWed, CbThurs, CbFri, CbSat, CbSun;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        IvDelete = (ImageView) findViewById(R.id.IvDelete);
        IvSave = (ImageView) findViewById(R.id.IvSave);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        PickerTime = (TimePicker) findViewById(R.id.PickerTime);
        CbMon = (CheckBox) findViewById(R.id.CbMon);
        CbTues = (CheckBox) findViewById(R.id.CbTues);
        CbWed = (CheckBox) findViewById(R.id.CbWed);
        CbThurs = (CheckBox) findViewById(R.id.CbThurs);
        CbFri = (CheckBox) findViewById(R.id.CbFri);
        CbSat = (CheckBox) findViewById(R.id.CbSat);
        CbSun = (CheckBox) findViewById(R.id.CbSun);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        IvDelete.setOnClickListener(this);
        IvSave.setOnClickListener(this);
    }

    private void initActions() {
        helper=new ExerciseHelper(context);
        TvTitle.setText(getToolbarTitle());
        IvDelete.setVisibility(View.VISIBLE);
        IvSave.setVisibility(View.VISIBLE);

        ReminderModel reminderModel = getAlarm();
        ViewUtils.setTimePickerTime(PickerTime, reminderModel.getTime());
        setDay(reminderModel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.IvSave:
                GotoSave();
                break;
            case R.id.IvDelete:
                GotoDelete();
                break;
        }
    }

    private ReminderModel getAlarm() {
        switch (getMode()) {
            case EDIT_ALARM:
                return getIntent().getParcelableExtra(Constants.ALARM_EXTRA);
            case ADD_ALARM:
//                long id = helper.addAlarm();
//                LoadAlarmsService.launchLoadAlarmsService(context);
                return new ReminderModel(-1);
            case UNKNOWN:
            default:
                throw new IllegalStateException("Mode supplied as intent extra for " +
                        AddOrEditAlarmActivity.class.getSimpleName() + " must match value in " +
                        Mode.class.getSimpleName());
        }
    }

    private void setDay(ReminderModel reminderModel) {
        CbMon.setChecked(reminderModel.getDay(ReminderModel.MON));
        CbTues.setChecked(reminderModel.getDay(ReminderModel.TUES));
        CbWed.setChecked(reminderModel.getDay(ReminderModel.WED));
        CbThurs.setChecked(reminderModel.getDay(ReminderModel.THURS));
        CbFri.setChecked(reminderModel.getDay(ReminderModel.FRI));
        CbSat.setChecked(reminderModel.getDay(ReminderModel.SAT));
        CbSun.setChecked(reminderModel.getDay(ReminderModel.SUN));
    }

    private void GotoSave() {
        final ReminderModel reminderModel = getAlarm();
        final Calendar instance = Calendar.getInstance();
        instance.set(Calendar.MINUTE, ViewUtils.getTimePickerMinute(PickerTime));
        instance.set(Calendar.HOUR_OF_DAY, ViewUtils.getTimePickerHour(PickerTime));
        reminderModel.setTime(instance.getTimeInMillis());

        reminderModel.setDay(ReminderModel.MON, CbMon.isChecked());
        reminderModel.setDay(ReminderModel.TUES, CbTues.isChecked());
        reminderModel.setDay(ReminderModel.WED, CbWed.isChecked());
        reminderModel.setDay(ReminderModel.THURS, CbThurs.isChecked());
        reminderModel.setDay(ReminderModel.FRI, CbFri.isChecked());
        reminderModel.setDay(ReminderModel.SAT, CbSat.isChecked());
        reminderModel.setDay(ReminderModel.SUN, CbSun.isChecked());
        if (getMode() == ADD_ALARM) {
            helper.insertAlarm(reminderModel);
            AlarmsService.launchLoadAlarmsService(context);
        } else {
            final int rowsUpdated = helper.updateAlarm(reminderModel);
            final int messageId = (rowsUpdated == 1) ? R.string.update_complete : R.string.update_failed;
            Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
        }
        ReminderReceiver.setReminderAlarm(context, reminderModel);
        finish();
    }

    private void GotoDelete() {
        final ReminderModel reminderModel = getAlarm();
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.delete_dialog_title);
        builder.setMessage(R.string.delete_dialog_content);
        builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            ReminderReceiver.cancelReminderAlarm(context, reminderModel);
            final int rowsDeleted =helper.deleteAlarm(reminderModel);
            int messageId;
            if (rowsDeleted == 1) {
                messageId = R.string.delete_complete;
                Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
                AlarmsService.launchLoadAlarmsService(context);
                finish();
            } else {
                messageId = R.string.delete_failed;
                Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.show();
    }

    private @Mode int getMode() {
        final @Mode int mode = getIntent().getIntExtra(Constants.MODE_EXTRA, UNKNOWN);
        return mode;
    }

    private String getToolbarTitle() {
        String titleResId;
        switch (getMode()) {
            case EDIT_ALARM:
                titleResId = "Edit Alarm";
                break;
            case ADD_ALARM:
                titleResId = "Add Alarm";
                break;
            case UNKNOWN:
            default:
                throw new IllegalStateException("Mode supplied as intent extra for " +
                        AddOrEditAlarmActivity.class.getSimpleName() + " must match value in " +
                        Mode.class.getSimpleName());
        }
        return titleResId;
    }

    public static Intent buildAddEditAlarmActivityIntent(Context context, @Mode int mode) {
        final Intent intent = new Intent(context, AddOrEditAlarmActivity.class);
        intent.putExtra(Constants.MODE_EXTRA, mode);
        return intent;
    }
}
