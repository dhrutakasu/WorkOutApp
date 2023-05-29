package com.out.workout.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.out.workout.R;
import com.out.workout.ui.activity.DayRemindersActivity;
import com.out.workout.ui.activity.ExerciseRemindersActivity;
import com.out.workout.utils.Constants;
import com.out.workout.utils.SharePreference;

public class ProfileFragment extends Fragment implements View.OnClickListener {


    private View ProfileView;
    private TextView TvExerciseReminders, TvDayReminders, TvExerciseSounds, TvCountDownTime, TvRestTime, TvExerciseRemindersTime;
    private CheckBox CbSound;
    private ImageView IvCountDownMinus, IvCountDownPlus, IvRestMinus, IvRestPlus;
    private int IsCounter, IsRest;
    private boolean IsSound;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProfileView = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews();
        initListeners();
        initActions();
        return ProfileView;
    }

    private void initViews() {
        if (ProfileView != null) {
            TvExerciseReminders = (TextView) ProfileView.findViewById(R.id.TvExerciseReminders);
            TvDayReminders = (TextView) ProfileView.findViewById(R.id.TvDayReminders);
            CbSound = (CheckBox) ProfileView.findViewById(R.id.CbSound);
            TvExerciseSounds = (TextView) ProfileView.findViewById(R.id.TvExerciseSounds);
            IvCountDownMinus = (ImageView) ProfileView.findViewById(R.id.IvCountDownMinus);
            IvCountDownPlus = (ImageView) ProfileView.findViewById(R.id.IvCountDownPlus);
            TvCountDownTime = (TextView) ProfileView.findViewById(R.id.TvCountDownTime);
            IvRestMinus = (ImageView) ProfileView.findViewById(R.id.IvRestMinus);
            IvRestPlus = (ImageView) ProfileView.findViewById(R.id.IvRestPlus);
            TvRestTime = (TextView) ProfileView.findViewById(R.id.TvRestTime);
            TvExerciseRemindersTime = (TextView) ProfileView.findViewById(R.id.TvExerciseRemindersTime);
        }
    }

    private void initListeners() {
        TvExerciseReminders.setOnClickListener(this);
        TvDayReminders.setOnClickListener(this);
        TvExerciseSounds.setOnClickListener(this);
        IvCountDownMinus.setOnClickListener(this);
        IvCountDownPlus.setOnClickListener(this);
        IvRestMinus.setOnClickListener(this);
        IvRestPlus.setOnClickListener(this);
        CbSound.setOnClickListener(this);
    }

    private void initActions() {
        IsCounter = SharePreference.getInt(getContext(), SharePreference.COUNT_TIMER, 10);
        IsRest = SharePreference.getInt(getContext(), SharePreference.REST_TIMER, 25);
        IsSound = SharePreference.getBoolean(getContext(), SharePreference.IS_SOUND, true);
        CbSound.setChecked(IsSound);
        TvCountDownTime.setText(String.valueOf(IsCounter));
        TvRestTime.setText(String.valueOf(IsRest).toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.TvExerciseReminders:
                GotoReminders();
                break;
            case R.id.TvDayReminders:
                GotoDayReminders();
                break;
            case R.id.TvExerciseSounds:
            case R.id.CbSound:
                GotoSound();
                break;
            case R.id.IvCountDownMinus:
                GotoCountDownMinus();
                break;
            case R.id.IvCountDownPlus:
                GotoCountDownPlus();
                break;
            case R.id.IvRestMinus:
                GotoRestMinus();
                break;
            case R.id.IvRestPlus:
                GotoRestPlus();
                break;
        }
    }

    private void GotoReminders() {
        startActivity(new Intent(getContext(), ExerciseRemindersActivity.class));
    }

    private void GotoDayReminders() {
        startActivity(new Intent(getContext(), DayRemindersActivity.class));
    }

    private void GotoSound() {
        if (IsSound) {
            IsSound = false;
        } else {
            IsSound = true;
        }
        SharePreference.SetBoolean(getContext(), SharePreference.IS_SOUND, IsSound);
        CbSound.setChecked(IsSound);
    }

    private void GotoCountDownMinus() {
        if (IsCounter > 5) {
            IsCounter--;
            TvCountDownTime.setText(String.valueOf(IsCounter));
            SharePreference.SetInt(getContext(), SharePreference.COUNT_TIMER, IsCounter);
        }
    }

    private void GotoCountDownPlus() {
        if (IsCounter < 65) {
            IsCounter++;
            TvCountDownTime.setText(String.valueOf(IsCounter));
            SharePreference.SetInt(getContext(), SharePreference.COUNT_TIMER, IsCounter);
        }
    }

    private void GotoRestMinus() {
        if (IsRest > 5) {
            IsRest--;
            TvRestTime.setText(String.valueOf(IsRest).toString());
            SharePreference.SetInt(getContext(), SharePreference.REST_TIMER, IsRest);
        }
    }

    private void GotoRestPlus() {
        if (IsRest < 65) {
            IsRest++;
            TvRestTime.setText(String.valueOf(IsRest));
            SharePreference.SetInt(getContext(), SharePreference.REST_TIMER, IsRest);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        System.out.println("---- : HR " + SharePreference.getInt(getContext(), Constants.NOTIFICATION_HOUR, 12));
        System.out.println("---- : MIN " + SharePreference.getInt(getContext(), Constants.NOTIFICATION_MINUTES, 0));
        TvExerciseRemindersTime.setText(SharePreference.getInt(getContext(), Constants.NOTIFICATION_HOUR, 12) + ":" + SharePreference.getInt(getContext(), Constants.NOTIFICATION_MINUTES, 00));
    }
}