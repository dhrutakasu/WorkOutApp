package com.out.workout.ui.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.out.workout.Helper.ExerciseHelper;
import com.out.workout.R;
import com.out.workout.Receiver.AlarmHelper;
import com.out.workout.model.ReminderModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<ReminderModel> reminderRecords;
    private final AlarmHelper alarmHelper;
    private final ExerciseHelper helper;
    private long mLastClickTime = 0;

    public ReminderAdapter(Context context, ArrayList<ReminderModel> reminderRecords, AlarmHelper alarmHelper) {
        this.context = context;
        this.reminderRecords = reminderRecords;
        this.alarmHelper = alarmHelper;
        helper = new ExerciseHelper(context);
    }

    @NonNull
    @Override
    public ReminderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.MyViewHolder holder, int position) {
        ReminderModel reminderModel = reminderRecords.get(position);
        holder.TvTime.setText(reminderModel.getTime());
        holder.TvTime.setOnClickListener(view -> {
            ReminderModel model = reminderRecords.get(position);
            showTimePickerDialog(model, position);
        });
        holder.TvMon.setVisibility(View.VISIBLE);
        holder.TvTue.setVisibility(View.VISIBLE);
        holder.TvWed.setVisibility(View.VISIBLE);
        holder.TvThu.setVisibility(View.VISIBLE);
        holder.TvFri.setVisibility(View.VISIBLE);
        holder.TvSat.setVisibility(View.VISIBLE);
        holder.TvSun.setVisibility(View.VISIBLE);
        if (reminderModel.getMon().equalsIgnoreCase("true")) {
            holder.TvMon.setText(context.getResources().getString(R.string.monday));
        } else {
            holder.TvMon.setVisibility(View.GONE);
        }
        if (reminderModel.getTue().equalsIgnoreCase("true")) {
            holder.TvTue.setText(context.getResources().getString(R.string.tu));
        } else {
            holder.TvTue.setVisibility(View.GONE);
        }
        if (reminderModel.getWed().equalsIgnoreCase("true")) {
            holder.TvWed.setText(context.getResources().getString(R.string.wed));
        } else {
            holder.TvWed.setVisibility(View.GONE);
        }
        if (reminderModel.getThu().equalsIgnoreCase("true")) {
            holder.TvThu.setText(context.getResources().getString(R.string.thursday));
        } else {
            holder.TvThu.setVisibility(View.GONE);
        }
        if (reminderModel.getFri().equalsIgnoreCase("true")) {
            holder.TvFri.setText(context.getResources().getString(R.string.fri));
        } else {
            holder.TvFri.setVisibility(View.GONE);
        }
        if (reminderModel.getSat().equalsIgnoreCase("true")) {
            holder.TvSat.setText(context.getResources().getString(R.string.sat));
        } else {
            holder.TvSat.setVisibility(View.GONE);
        }
        if (reminderModel.getSun().equalsIgnoreCase("true")) {
            holder.TvSun.setText(context.getResources().getString(R.string.sun));
        } else {
            holder.TvSun.setVisibility(View.GONE);
        }
        holder.SwitchTime.setChecked(reminderModel.getOnOff().equalsIgnoreCase("true"));
        holder.SwitchTime.setOnCheckedChangeListener((compoundButton, z) -> {
            ReminderModel reminderModel1 = reminderRecords.get(position);
            reminderModel1.setOnOff(String.valueOf(z));
            helper.updateReminder(reminderModel1);
        });
        holder.IvTimeDelete.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime >= 1000) {
                mLastClickTime = SystemClock.elapsedRealtime();
                ReminderModel model = reminderRecords.get(position);
                helper.deleteReminder(model.getId());
                reminderRecords.remove(position);
                notifyItemRangeChanged(position, reminderRecords.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminderRecords.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TvMon;
        TextView TvTue;
        TextView TvWed;
        TextView TvThu;
        TextView TvFri;
        TextView TvTime;
        TextView TvSat;
        TextView TvSun;
        ImageView IvTimeDelete;
        Switch SwitchTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvTime = (TextView) itemView.findViewById(R.id.TvTime);
            TvMon = (TextView) itemView.findViewById(R.id.TvMon);
            TvTue = (TextView) itemView.findViewById(R.id.TvTue);
            TvWed = (TextView) itemView.findViewById(R.id.TvWed);
            TvThu = (TextView) itemView.findViewById(R.id.TvThu);
            TvFri = (TextView) itemView.findViewById(R.id.TvFri);
            TvSat = (TextView) itemView.findViewById(R.id.TvSat);
            TvSun = (TextView) itemView.findViewById(R.id.TvSun);
            IvTimeDelete = (ImageView) itemView.findViewById(R.id.IvTimeDelete);
            SwitchTime = (Switch) itemView.findViewById(R.id.SwitchTime);
        }
    }

    public void showTimePickerDialog(final ReminderModel model, int i) {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(this.context, (timePicker, i2, i3) -> {
            if (timePicker.isShown()) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR_OF_DAY, i);
                calendar1.set(Calendar.MINUTE, i2);
                showDaysNameDialog(calendar1, model, i);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    public void showDaysNameDialog(final Calendar calendar, final ReminderModel model, final int value) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle(this.context.getResources().getString(R.string.days));
        final ArrayList list = new ArrayList();
        builder.setMultiChoiceItems(this.context.getResources().getStringArray(R.array.day_list), (boolean[]) null, (dialogInterface, i2, Bool) -> {
            if (Bool) {
                list.add(Integer.valueOf(value));
            } else {
                list.remove(Integer.valueOf(value));
            }
        });
        builder.setPositiveButton(this.context.getString(R.string.ok), (dialogInterface, i2) -> {
            if (list.size() > 0) {
                dialogInterface.dismiss();
                model.setTime(new SimpleDateFormat("hh:mm a").format(calendar.getTime()));
                model.setMon("false");
                model.setTue("false");
                model.setWed("false");
                model.setThu("false");
                model.setFri("false");
                model.setSat("false");
                model.setSun("false");
                for (int i3 = 0; i3 < list.size(); i3++) {
                    if (list.get(i3).equals(0)) {
                        model.setMon("true");
                    } else if (list.get(i3).equals(1)) {
                        model.setTue("true");
                    } else if (list.get(i3).equals(2)) {
                        model.setWed("true");
                    } else if (list.get(i3).equals(3)) {
                        model.setThu("true");
                    } else if (list.get(i3).equals(4)) {
                        model.setFri("true");
                    } else if (list.get(i3).equals(5)) {
                        model.setSat("true");
                    } else if (list.get(i3).equals(6)) {
                        model.setSun("true");
                    }
                }
                model.setOnOff("true");
                int IntHr;
                int IntMin;
                int AMPM;
                if (new SimpleDateFormat("hh:mm a").format(calendar.getTime()).endsWith("AM")) {
                    IntHr = Integer.parseInt(new SimpleDateFormat("hh").format(calendar.getTime()));
                    IntMin = Integer.parseInt(new SimpleDateFormat("mm").format(calendar.getTime()));
                    AMPM = 0;
                } else {
                    IntHr = Integer.parseInt(new SimpleDateFormat("hh").format(calendar.getTime()));
                    IntMin = Integer.parseInt(new SimpleDateFormat("mm").format(calendar.getTime()));
                    AMPM = 1;
                }
                alarmHelper.schedulePendingIntent(IntHr, IntMin, AMPM);
                helper.updateReminder(model);
                notifyItemChanged(AMPM);
                notifyItemRangeChanged(AMPM, reminderRecords.size());
                return;
            }
            Toast.makeText(context, context.getResources().getString(R.string.please_select_at_least_one_day), Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton(this.context.getString(R.string.cancel), (dialogInterface, i2) -> dialogInterface.dismiss());
        builder.create().show();
    }
}
