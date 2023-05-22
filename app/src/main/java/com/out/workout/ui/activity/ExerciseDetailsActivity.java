package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.out.workout.R;
import com.out.workout.utils.Constants;

public class ExerciseDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;

    private int[] ExerciseImage;
    private String ExerciseName,ExerciseDesc;
    private int ExerciseDays;
    private int[] ExerciseRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

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
        Bundle bundle = getIntent().getExtras();
        ExerciseImage=bundle.getIntArray(Constants.ExerciseImage);
        ExerciseName=bundle.getString(Constants.ExerciseName);
        ExerciseDesc=bundle.getString(Constants.ExerciseDesc);
        ExerciseDays=bundle.getInt(Constants.ExerciseDays);
        ExerciseRotate=bundle.getIntArray(Constants.ExerciseRotate);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
    }

    private void initActions() {
        TvTitle.setText(ExerciseName);
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