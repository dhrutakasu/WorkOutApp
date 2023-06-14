package com.out.workout.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.android.multistateanimation.MultiStateAnimation;
import com.out.workout.Ads.Ad_Native;
import com.out.workout.Helper.ExerciseHelper;
import com.out.workout.R;
import com.out.workout.utils.Constants;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;

    private int[] ExerciseImage;
    private String ExerciseName, ExerciseDesc;
    private int ExerciseDays;
    private int ExercisePos;
    private int[] ExerciseRotate;
    private ImageView IvAnimatedExercise, IvExerciseDetailsMinus, IvExerciseDetailsPlus;
    private TextView TvExerciseDetailsValue, TvExerciseDescription;
    private ExerciseHelper helper;
    private boolean IsChangeCycles=false;

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
        IvAnimatedExercise = (ImageView) findViewById(R.id.IvAnimatedExercise);
        IvExerciseDetailsMinus = (ImageView) findViewById(R.id.IvExerciseDetailsMinus);
        TvExerciseDetailsValue = (TextView) findViewById(R.id.TvExerciseDetailsValue);
        IvExerciseDetailsPlus = (ImageView) findViewById(R.id.IvExerciseDetailsPlus);
        TvExerciseDescription = (TextView) findViewById(R.id.TvExerciseDescription);
    }

    private void initIntents() {
        Bundle bundle = getIntent().getExtras();
        ExerciseImage = bundle.getIntArray(Constants.ExerciseImage);
        ExerciseName = bundle.getString(Constants.ExerciseName);
        ExerciseDesc = bundle.getString(Constants.ExerciseDesc);
        ExercisePos = bundle.getInt(Constants.ExercisePos);
        ExerciseDays = bundle.getInt(Constants.ExerciseDays);
        ExerciseRotate = bundle.getIntArray(Constants.ExerciseRotate);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        IvExerciseDetailsMinus.setOnClickListener(this);
        IvExerciseDetailsPlus.setOnClickListener(this);
    }

    private void initActions() {
        Ad_Native.getInstance().showNative250(this, findViewById(R.id.FlNative));
        helper = new ExerciseHelper(context);
        TvTitle.setText(Constants.getCapsSentences(ExerciseName));
        MultiStateAnimation.SectionBuilder sectionBuilder = new MultiStateAnimation.SectionBuilder("pending");
        for (int i = 0; i < ExerciseImage.length; i++) {
            sectionBuilder.addFrame(ExerciseImage[i]);
        }
        sectionBuilder.setOneshot(false);
        sectionBuilder.setFrameDuration(800);
        MultiStateAnimation stateAnimation = new MultiStateAnimation.Builder(IvAnimatedExercise).addSection(sectionBuilder).build(context);
        stateAnimation.transitionNow("pending");
        TvExerciseDetailsValue.setText(ExerciseRotate[ExercisePos] + "");
        TvExerciseDescription.setText(ExerciseDesc);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.IvExerciseDetailsMinus:
                GotoMinus();
                break;
            case R.id.IvExerciseDetailsPlus:
                GotoPlus();
                break;
        }
    }

    private void GotoMinus() {
        if (Integer.valueOf(ExerciseRotate[ExercisePos]).intValue() != 5) {
            ExerciseRotate[ExercisePos]--;
            TvExerciseDetailsValue.setText(ExerciseRotate[ExercisePos] + "");
        }
        IsChangeCycles = true;
    }

    private void GotoPlus() {
        if (Integer.valueOf(ExerciseRotate[ExercisePos]).intValue() != 100) {
            ExerciseRotate[ExercisePos]++;
            TvExerciseDetailsValue.setText(ExerciseRotate[ExercisePos] + "");
        }
        IsChangeCycles = true;
    }

    @Override
    public void onBackPressed() {
        if (IsChangeCycles){
            Toast.makeText(getApplicationContext(), R.string.str_exercise_cycles_are_updated, Toast.LENGTH_SHORT).show();
        }
        if (helper.IsExist(ExerciseName)) {
            helper.updateExerciseCycles(helper.getExerciseRecord(ExerciseName),ExerciseName, ExerciseRotate[ExercisePos] + "");
        }
        Intent intent=new Intent();
        intent.putExtra(Constants.ExercisePos,ExercisePos);
        intent.putExtra(Constants.ExerciseRotate,ExerciseRotate[ExercisePos] + "");
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }
}