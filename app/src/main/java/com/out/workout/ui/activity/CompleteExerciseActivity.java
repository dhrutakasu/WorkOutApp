package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.out.workout.R;
import com.out.workout.utils.Constants;

public class CompleteExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle, TvCompletedExerciseName, TvCompletedExerciseSize;
    private int ExerciseCount, ExerciseTime;
    private String WorkoutType;
    private TextView TvCompletedExerciseHour, TvCompletedExerciseMinute, TvCompletedExerciseSecond;
    private LinearLayout LlContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_exercise);
        initViews();
        initIntents();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvCompletedExerciseName = (TextView) findViewById(R.id.TvCompletedExerciseName);
        TvCompletedExerciseSize = (TextView) findViewById(R.id.TvCompletedExerciseSize);
        TvCompletedExerciseHour = (TextView) findViewById(R.id.TvCompletedExerciseHour);
        TvCompletedExerciseMinute = (TextView) findViewById(R.id.TvCompletedExerciseMinute);
        TvCompletedExerciseSecond = (TextView) findViewById(R.id.TvCompletedExerciseSecond);
        LlContinue = (LinearLayout) findViewById(R.id.LlContinue);
    }

    private void initIntents() {
        ExerciseCount = getIntent().getIntExtra(Constants.ExerciseCount, 0);
        WorkoutType = getIntent().getStringExtra(Constants.WorkoutType);
        ExerciseTime = getIntent().getIntExtra(Constants.ExerciseTime, 0);
    }

    private void initListeners() {
        LlContinue.setOnClickListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.complete_exercise));
        TvCompletedExerciseName.setText(WorkoutType);
        TvCompletedExerciseSize.setText(ExerciseCount + "");

        int hours = ExerciseTime / 3600;
        int minutes = (ExerciseTime % 3600) / 60;
        int seconds = ExerciseTime % 60;
        String hr, min, sec;
        hr = String.valueOf(hours);
        min = String.valueOf(minutes);
        sec = String.valueOf(seconds);
        if (hours < 10) {
            hr = "00";
        }
        if (minutes < 10) {
            min = "00";
        }
        if (seconds < 10) {
            sec = "00";
        }
        TvCompletedExerciseHour.setText(hr);
        TvCompletedExerciseMinute.setText(min);
        TvCompletedExerciseSecond.setText(sec);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LlContinue:
            case R.id.IvBack:
                onBackPressed();
                break;
        }
    }
}