package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.out.workout.R;
import com.out.workout.model.ExerciseModel;
import com.out.workout.utils.Constants;

import java.util.ArrayList;

public class WorkOutExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle, TvWorkoutStart;
    private ArrayList<ExerciseModel> WorkoutExerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_out_exercise);

        initViews();
        initIntents();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
    }

    private void initIntents() {
        Bundle extras = getIntent().getExtras();
        WorkoutExerciseList = (ArrayList) extras.getSerializable(Constants.WorkList);

    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
    }

    private void initActions() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
        }
    }
}