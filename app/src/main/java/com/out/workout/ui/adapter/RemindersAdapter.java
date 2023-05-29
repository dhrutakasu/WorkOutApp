package com.out.workout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.out.workout.model.ReminderModel;
import com.out.workout.ui.activity.AddOrEditAlarmActivity;
import com.out.workout.utils.AlarmUtils;
import com.out.workout.R;
import com.out.workout.utils.Constants;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.MyViewHolder> {

    private List<ReminderModel> mReminderModels;
    private String[] mDays;
    private int mAccentColor = -1;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_alarm, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        if(mAccentColor == -1) {
            mAccentColor = ContextCompat.getColor(context, R.color.black);
        }
        if(mDays == null){
            mDays = context.getResources().getStringArray(R.array.days_abbreviated);
        }
        ReminderModel reminderModel = mReminderModels.get(position);
        holder.TvTime.setText(AlarmUtils.getReadableTime(reminderModel.getTime()));
        holder.TvAmPm.setText(AlarmUtils.getAmPm(reminderModel.getTime()));
        holder.TvDays.setText(SelectedDaysValue(reminderModel));

        holder.itemView.setOnClickListener(view -> {
            Context context1 = view.getContext();
            Intent intent = AddOrEditAlarmActivity.buildAddEditAlarmActivityIntent(context1, Constants.EDIT_ALARM);
            intent.putExtra(Constants.ALARM_EXTRA, reminderModel);
            context1.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (mReminderModels == null) ? 0 : mReminderModels.size();
    }

    private Spannable SelectedDaysValue(ReminderModel reminderModel) {
        int numDays = 7;
        SparseBooleanArray modelDays = reminderModel.getDays();
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        ForegroundColorSpan colorSpan;
        int start, end;
        for (int i = 0; i < numDays; i++) {
            start = stringBuilder.length();
            final String dayText = mDays[i];
            stringBuilder.append(dayText);
            stringBuilder.append(" ");
            end = start + dayText.length();
            final boolean isSelected = modelDays.valueAt(i);
            if(isSelected) {
                colorSpan = new ForegroundColorSpan(mAccentColor);
                stringBuilder.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return stringBuilder;
    }

    public void setAlarmItems(List<ReminderModel> reminderModels) {
        mReminderModels = reminderModels;
        notifyDataSetChanged();
    }

    static final class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView TvTime, TvAmPm,  TvDays;
        MyViewHolder(View itemView) {
            super(itemView);

            TvTime = itemView.findViewById(R.id.TvTime);
            TvAmPm = itemView.findViewById(R.id.TvAmPm);
            TvDays = itemView.findViewById(R.id.TvDays);
        }
    }
}
