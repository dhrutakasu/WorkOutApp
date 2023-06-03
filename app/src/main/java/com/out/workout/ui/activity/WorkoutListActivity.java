package com.out.workout.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.Ads.Ad_Interstitial;
import com.out.workout.Helper.ExerciseHelper;
import com.out.workout.R;
import com.out.workout.model.WorkoutExerciseModel;
import com.out.workout.ui.adapter.WorkoutListAdapter;
import com.out.workout.utils.Constants;

import java.util.ArrayList;

public class WorkoutListActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int ExerciseCode = 111;
    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private ImageView TvWorkoutStart;
    private RecyclerView RvWorkoutList;
    private String WorkoutType;
    private ArrayList<WorkoutExerciseModel> workoutExerciseModels;
    private String CountDays;
    private ExerciseHelper helper;
    private WorkoutListAdapter workoutListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        initViews();
        initIntents();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        TvWorkoutStart = (ImageView) findViewById(R.id.TvWorkoutStart);
        RvWorkoutList = (RecyclerView) findViewById(R.id.RvWorkoutList);
    }

    private void initIntents() {
        WorkoutType = getIntent().getStringExtra(Constants.WorkoutType);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        TvWorkoutStart.setOnClickListener(this);
    }

    private void initActions() {
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        helper = new ExerciseHelper(context);
        TvTitle.setText(WorkoutType);
        workoutExerciseModels = new ArrayList<>();
        RvWorkoutList.setLayoutManager(new LinearLayoutManager(context));
        workoutListAdapter = new WorkoutListAdapter(context, CountDays, getAllWorkoutDatas(), WorkoutType, new WorkoutListAdapter.WorkoutListInterface() {
            @Override
            public void setWorkoutList(WorkoutExerciseModel exerciseModel, int position, String countDays) {
                int[] getInts = new int[exerciseModel.getExerciseImg().length()];
                for (int i = 0; i < exerciseModel.getExerciseImg().length(); i++) {
                    getInts[i] = exerciseModel.getExerciseImg().getResourceId(i, 0);
                }
                Intent ExerciseIntent = new Intent(context, ExerciseDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putIntArray(Constants.ExerciseImage, getInts);
                bundle.putString(Constants.ExerciseName, exerciseModel.getExerciseName());
                bundle.putString(Constants.ExerciseDesc, exerciseModel.getExerciseDesc());
                bundle.putString(Constants.ExerciseDays, countDays);
                bundle.putInt(Constants.ExercisePos, position);
                bundle.putIntArray(Constants.ExerciseRotate, exerciseModel.getExerciseType());
                ExerciseIntent.putExtras(bundle);
                startActivityForResult(ExerciseIntent, ExerciseCode);
            }
        });
        RvWorkoutList.setAdapter(workoutListAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.TvWorkoutStart:
                GotoStart();
                break;
        }
    }

    private void GotoStart() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Load Ad....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Ad_Interstitial.getInstance().showInter(WorkoutListActivity.this, new Ad_Interstitial.MyCallback() {
                    @Override
                    public void callbackCall() {
                        Intent intentStart = new Intent(context, WorkOutExerciseActivity.class);
                        Constants.WorkExerciseList = new ArrayList<>();
                        Constants.WorkExerciseList.addAll(workoutExerciseModels);
                        intentStart.putExtra(Constants.WorkoutType, WorkoutType);
                        startActivity(intentStart);
                    }
                });
            }
        }, 3000L);
    }

    private ArrayList<WorkoutExerciseModel> getAllWorkoutDatas() {
        System.out.println("------- Work : "+WorkoutType);
        if (WorkoutType.contains("Arm")) {
            CountDays = "Day 1";
            WorkoutExerciseModel exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm), getResources().getString(R.string.str_desc_triceps_dips), getResources().obtainTypedArray(R.array.triceps_dips), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm1), getResources().getString(R.string.str_desc_pushups), getResources().obtainTypedArray(R.array.pushups), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm2), getResources().getString(R.string.str_desc_decline_pushups), getResources().obtainTypedArray(R.array.decline_pushups), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm3), getResources().getString(R.string.str_desc_incline_pushups), getResources().obtainTypedArray(R.array.incline_pushups), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm4), getResources().getString(R.string.str_desc_army_pushups), getResources().obtainTypedArray(R.array.army_pushups), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm5), getResources().getString(R.string.str_desc_arm_scissors), getResources().obtainTypedArray(R.array.arm_scissors), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm6), getResources().getString(R.string.str_desc_triceps_stretch_left), getResources().obtainTypedArray(R.array.triceps_stretch_left), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm7), getResources().getString(R.string.str_desc_triceps_stretch_right), getResources().obtainTypedArray(R.array.triceps_stretch_right), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm8), getResources().getString(R.string.str_desc_standing_biceps_stretch_left), getResources().obtainTypedArray(R.array.standing_biceps_stretch_left), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm9), getResources().getString(R.string.str_desc_standing_biceps_stretch_right), getResources().obtainTypedArray(R.array.standing_biceps_stretch_right), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm10), getResources().getString(R.string.str_desc_elbow_back), getResources().obtainTypedArray(R.array.elbow_back), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm11), getResources().getString(R.string.str_desc_arm_crunches_left), getResources().obtainTypedArray(R.array.arm_crunches_left), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm12), getResources().getString(R.string.str_desc_arm_crunches_right), getResources().obtainTypedArray(R.array.arm_crunches_right), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm13), getResources().getString(R.string.str_desc_wall_pushups), getResources().obtainTypedArray(R.array.wall_pushups), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm14), getResources().getString(R.string.str_desc_jumping_jacks), getResources().obtainTypedArray(R.array.jumping_jacks), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm15), getResources().getString(R.string.str_desc_plank_taps), getResources().obtainTypedArray(R.array.plank_taps), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm16), getResources().getString(R.string.str_str_desc_punches), getResources().obtainTypedArray(R.array.punches), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm17), getResources().getString(R.string.str_desc_knee_pushups), getResources().obtainTypedArray(R.array.knee_pushups), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm18), getResources().getString(R.string.str_desc_pushup_low_hold), getResources().obtainTypedArray(R.array.pushup_low_hold), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm19), getResources().getString(R.string.str_desc_wide_arm_pushups), getResources().obtainTypedArray(R.array.wide_arm_pushups), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm20), getResources().getString(R.string.str_desc_diamond_pushups), getResources().obtainTypedArray(R.array.diamond_pushups), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm21), getResources().getString(R.string.str_desc_plank), getResources().obtainTypedArray(R.array.plank), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm22), getResources().getString(R.string.str_desc_shoulder_gators), getResources().obtainTypedArray(R.array.shoulder_gators), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm23), getResources().getString(R.string.str_desc_skip_the_rope), getResources().obtainTypedArray(R.array.skip_the_rope), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm24), getResources().getString(R.string.str_desc_plank_alternate_reach), getResources().obtainTypedArray(R.array.plank_alternate_reach), getResources().getIntArray(R.array.buttock_cycles));
            workoutExerciseModels.add(exerciseModel);
            if (helper.getExerciseCount() > 0) {
                if (!helper.IsExistType(WorkoutType)) {
                    for (int i = 0; i < workoutExerciseModels.size(); i++) {
                        int[] getInts = new int[workoutExerciseModels.get(i).getExerciseImg().length()];
                        for (int j = 0; j < workoutExerciseModels.get(i).getExerciseImg().length(); j++) {
                            getInts[j] = workoutExerciseModels.get(i).getExerciseImg().getResourceId(j, 0);
                        }
                        helper.insertExerciseCycles(WorkoutType, workoutExerciseModels.get(i).getExerciseName(), workoutExerciseModels.get(i).getExerciseDesc(), getInts, workoutExerciseModels.get(i).getExerciseType()[i] + "");
                    }
                }
            } else {
                for (int i = 0; i < workoutExerciseModels.size(); i++) {
                    int[] getInts = new int[workoutExerciseModels.get(i).getExerciseImg().length()];
                    for (int j = 0; j < workoutExerciseModels.get(i).getExerciseImg().length(); j++) {
                        getInts[j] = workoutExerciseModels.get(i).getExerciseImg().getResourceId(j, 0);
                    }
                    helper.insertExerciseCycles(WorkoutType, workoutExerciseModels.get(i).getExerciseName(), workoutExerciseModels.get(i).getExerciseDesc(), getInts, workoutExerciseModels.get(i).getExerciseType()[i] + "");
                }
            }
        } else if (WorkoutType.contains("Weight Lose")) {

            WorkoutExerciseModel exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose), getResources().getString(R.string.str_desc_vertical_leg_crunches), getResources().obtainTypedArray(R.array.vertical_leg_crunches), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose1), getResources().getString(R.string.str_desc_v_crunches), getResources().obtainTypedArray(R.array.v_crunches), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose2), getResources().getString(R.string.str_desc_lunge), getResources().obtainTypedArray(R.array.lunge), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose3), getResources().getString(R.string.str_desc_squats), getResources().obtainTypedArray(R.array.squats), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose4), getResources().getString(R.string.str_desc_pushups), getResources().obtainTypedArray(R.array.pushups), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose5), getResources().getString(R.string.str_desc_leg_up_crunches), getResources().obtainTypedArray(R.array.leg_up_crunches), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose6), getResources().getString(R.string.str_desc_jumping_jacks), getResources().obtainTypedArray(R.array.jumping_jacks), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose7), getResources().getString(R.string.str_desc_plank_with_leg_lift), getResources().obtainTypedArray(R.array.plank_with_leg_lift), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose8), getResources().getString(R.string.str_desc_high_knees), getResources().obtainTypedArray(R.array.high_knees), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose9), getResources().getString(R.string.str_desc_basic_crunches), getResources().obtainTypedArray(R.array.basic_crunches), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose10), getResources().getString(R.string.str_desc_alternater_heeltouch), getResources().obtainTypedArray(R.array.alternater_heeltouch), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose11), getResources().getString(R.string.str_desc_bicycle_crunches), getResources().obtainTypedArray(R.array.bicycle_crunches), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose12), getResources().getString(R.string.str_desc_flutter_kicks), getResources().obtainTypedArray(R.array.flutter_kicks), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose13), getResources().getString(R.string.str_desc_arm_crunches), getResources().obtainTypedArray(R.array.arm_crunches), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose14), getResources().getString(R.string.str_desc_bench_dips), getResources().obtainTypedArray(R.array.bench_dips), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_lose15), getResources().getString(R.string.str_desc_leg_raise), getResources().obtainTypedArray(R.array.leg_raise), getResources().getIntArray(R.array.weightloss_cycles));
            workoutExerciseModels.add(exerciseModel);
            CountDays = "Day 2";
            if (helper.getExerciseCount() > 0) {
                if (!helper.IsExistType(WorkoutType)) {
                    for (int i = 0; i < workoutExerciseModels.size(); i++) {
                        int[] getInts = new int[workoutExerciseModels.get(i).getExerciseImg().length()];
                        for (int j = 0; j < workoutExerciseModels.get(i).getExerciseImg().length(); j++) {
                            getInts[j] = workoutExerciseModels.get(i).getExerciseImg().getResourceId(j, 0);
                        }
                        helper.insertExerciseCycles(WorkoutType, workoutExerciseModels.get(i).getExerciseName(), workoutExerciseModels.get(i).getExerciseDesc(), getInts, workoutExerciseModels.get(i).getExerciseType()[i] + "");
                    }
                }
            } else {
                for (int i = 0; i < workoutExerciseModels.size(); i++) {
                    int[] getInts = new int[workoutExerciseModels.get(i).getExerciseImg().length()];
                    for (int j = 0; j < workoutExerciseModels.get(i).getExerciseImg().length(); j++) {
                        getInts[j] = workoutExerciseModels.get(i).getExerciseImg().getResourceId(j, 0);
                    }
                    helper.insertExerciseCycles(WorkoutType, workoutExerciseModels.get(i).getExerciseName(), workoutExerciseModels.get(i).getExerciseDesc(), getInts, workoutExerciseModels.get(i).getExerciseType()[i] + "");
                }
            }
        } else if (WorkoutType.contains("Abs")) {

            WorkoutExerciseModel exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs), getResources().getString(R.string.str_v_crunch_desc), getResources().obtainTypedArray(R.array.abs_v_crunch), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs1), getResources().getString(R.string.str_clapping_crunches_desc), getResources().obtainTypedArray(R.array.abs_clapping_crunches), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs2), getResources().getString(R.string.str_side_crunches_right_desc), getResources().obtainTypedArray(R.array.abs_flutter_kicks), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs3), getResources().getString(R.string.str_side_crunches_left_desc), getResources().obtainTypedArray(R.array.abs_plank), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs4), getResources().getString(R.string.str_flutter_kicks_desc), getResources().obtainTypedArray(R.array.abs_reverse_crunches), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs5), getResources().getString(R.string.str_plank_desc), getResources().obtainTypedArray(R.array.abs_bent_leg_twist), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs6), getResources().getString(R.string.str_reverse_crunches_desc), getResources().obtainTypedArray(R.array.abs_bicycle_crunches), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs7), getResources().getString(R.string.str_bent_leg_twist_desc), getResources().obtainTypedArray(R.array.abs_cross_arm_crunches), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs8), getResources().getString(R.string.str_bicycle_crunches_desc), getResources().obtainTypedArray(R.array.abs_x_man_crunch), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs9), getResources().getString(R.string.str_cross_arm_crunches_desc), getResources().obtainTypedArray(R.array.abs_dumbbell_paddle_boats), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs10), getResources().getString(R.string.str_x_man_crunch_desc), getResources().obtainTypedArray(R.array.abs_dumbbell_crunches), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs11), getResources().getString(R.string.str_dumbbell_paddle_boats_desc), getResources().obtainTypedArray(R.array.abs_dumbbell_crunch_and_punches), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs12), getResources().getString(R.string.str_dumbbell_crunches_desc), getResources().obtainTypedArray(R.array.abs_side_leg_rise_left), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs13), getResources().getString(R.string.str_dumbbell_crunch_and_punches_desc), getResources().obtainTypedArray(R.array.abs_side_leg_rise_right), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs14), getResources().getString(R.string.str_side_leg_rise_left_desc), getResources().obtainTypedArray(R.array.abs_lying_twist_stretch_right), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs15), getResources().getString(R.string.str_side_leg_rise_right_desc), getResources().obtainTypedArray(R.array.abs_one_down_two_ups), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs16), getResources().getString(R.string.str_lying_twist_stretch_right_desc), getResources().obtainTypedArray(R.array.abs_dumbbell_toe_touch_crunch_right), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs17), getResources().getString(R.string.str_one_down_two_ups_desc), getResources().obtainTypedArray(R.array.abs_dumbbell_toe_touch_crunch_left), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs18), getResources().getString(R.string.str_dumbbell_toe_touch_crunch_right_desc), getResources().obtainTypedArray(R.array.abs_v_hold), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs19), getResources().getString(R.string.str_dumbbell_toe_touch_crunch_left_desc), getResources().obtainTypedArray(R.array.abs_dumbbell_bicycle_passes), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs20), getResources().getString(R.string.str_v_hold_desc), getResources().obtainTypedArray(R.array.pushups), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs21), getResources().getString(R.string.str_dumbbell_bicycle_passes_desc), getResources().obtainTypedArray(R.array.abs_seated_abs_counterclockwise), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs22), getResources().getString(R.string.str_dumbbell_torture_tucks_desc), getResources().obtainTypedArray(R.array.abs_seated_abs_clockwise), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs23), getResources().getString(R.string.str_seated_abs_counterclockwise_desc), getResources().obtainTypedArray(R.array.abs_ninty_crunch), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs24), getResources().getString(R.string.str_seated_abs_clockwise_desc), getResources().obtainTypedArray(R.array.abs_dumbbell_side_bend_right), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs25), getResources().getString(R.string.str_ninty_crunch_desc), getResources().obtainTypedArray(R.array.abs_dumbbell_side_bend_left), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs26), getResources().getString(R.string.str_dumbbell_side_bend_right_desc), getResources().obtainTypedArray(R.array.abs_cross_knee_plank), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs27), getResources().getString(R.string.str_dumbbell_side_bend_left_desc), getResources().obtainTypedArray(R.array.abs_oblique_v_ups_left), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs28), getResources().getString(R.string.str_cross_knee_plank_desc), getResources().obtainTypedArray(R.array.abs_oblique_v_ups_right), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs29), getResources().getString(R.string.str_oblique_v_ups_left_desc), getResources().obtainTypedArray(R.array.abs_crunches_with_legs_raised), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs30), getResources().getString(R.string.oblique_v_ups_right_desc), getResources().obtainTypedArray(R.array.abs_alt_v_up), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs31), getResources().getString(R.string.str_crunches_with_legs_raised_desc), getResources().obtainTypedArray(R.array.abs_lying_twist_stretch_left), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs32), getResources().getString(R.string.str_alt_v_up_desc), getResources().obtainTypedArray(R.array.abs_childs_pose), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs33), getResources().getString(R.string.str_lying_twist_stretch_left_desc), getResources().obtainTypedArray(R.array.abs_cobra_stretch), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs34), getResources().getString(R.string.str_childs_pose_desc), getResources().obtainTypedArray(R.array.abs_side_plank_left), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs35), getResources().getString(R.string.str_cobra_stretch_desc), getResources().obtainTypedArray(R.array.abs_side_plank_right), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs36), getResources().getString(R.string.str_side_plank_left_desc), getResources().obtainTypedArray(R.array.abs_dumbbell_russian_twist), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs37), getResources().getString(R.string.str_side_plank_right_desc), getResources().obtainTypedArray(R.array.abs_v_up), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs38), getResources().getString(R.string.str_sit_up_twist_desc), getResources().obtainTypedArray(R.array.abs_side_crunches_right), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs39), getResources().getString(R.string.str_dumbbell_russian_twist_desc), getResources().obtainTypedArray(R.array.abs_side_crunches_left), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs40), getResources().getString(R.string.str_heels_to_the_heaven_desc), getResources().obtainTypedArray(R.array.abs_sit_up_twist), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs41), getResources().getString(R.string.str_v_up_desc), getResources().obtainTypedArray(R.array.abs_heels_to_the_heaven), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_abs42), getResources().getString(R.string.str_heel_touch_desc), getResources().obtainTypedArray(R.array.abs_heel_touch), getResources().getIntArray(R.array.abs_cycles));
            workoutExerciseModels.add(exerciseModel);
            CountDays = "Day 3";
            if (helper.getExerciseCount() > 0) {
                if (!helper.IsExistType(WorkoutType)) {
                    for (int i = 0; i < workoutExerciseModels.size(); i++) {
                        int[] getInts = new int[workoutExerciseModels.get(i).getExerciseImg().length()];
                        for (int j = 0; j < workoutExerciseModels.get(i).getExerciseImg().length(); j++) {
                            getInts[j] = workoutExerciseModels.get(i).getExerciseImg().getResourceId(j, 0);
                        }
                        helper.insertExerciseCycles(WorkoutType, workoutExerciseModels.get(i).getExerciseName(), workoutExerciseModels.get(i).getExerciseDesc(), getInts, workoutExerciseModels.get(i).getExerciseType()[i] + "");
                    }
                }
            } else {
                for (int i = 0; i < workoutExerciseModels.size(); i++) {
                    int[] getInts = new int[workoutExerciseModels.get(i).getExerciseImg().length()];
                    for (int j = 0; j < workoutExerciseModels.get(i).getExerciseImg().length(); j++) {
                        getInts[j] = workoutExerciseModels.get(i).getExerciseImg().getResourceId(j, 0);
                    }
                    helper.insertExerciseCycles(WorkoutType, workoutExerciseModels.get(i).getExerciseName(), workoutExerciseModels.get(i).getExerciseDesc(), getInts, workoutExerciseModels.get(i).getExerciseType()[i] + "");
                }
            }
        } else if (WorkoutType.contains("Chest")) {

            WorkoutExerciseModel exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest), getResources().getString(R.string.str_atomic_desc), getResources().obtainTypedArray(R.array.atomic), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest1), getResources().getString(R.string.str_clap_desc), getResources().obtainTypedArray(R.array.clap), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest2), getResources().getString(R.string.str_elevated_hands_desc), getResources().obtainTypedArray(R.array.elevated_hands), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest3), getResources().getString(R.string.str_eccentric_desc), getResources().obtainTypedArray(R.array.eccentric), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest4), getResources().getString(R.string.str_feet_elevated_desc), getResources().obtainTypedArray(R.array.feet_elivated), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest5), getResources().getString(R.string.str_narrow_grip_desc), getResources().obtainTypedArray(R.array.narrow_grip), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest6), getResources().getString(R.string.str_pike_desc), getResources().obtainTypedArray(R.array.pike), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest7), getResources().getString(R.string.str_single_arm_left_desc), getResources().obtainTypedArray(R.array.single_arm_left), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest8), getResources().getString(R.string.str_single_arm_right_desc), getResources().obtainTypedArray(R.array.single_arm_right), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest9), getResources().getString(R.string.str_single_leg_left_desc), getResources().obtainTypedArray(R.array.single_leg_left), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest10), getResources().getString(R.string.str_single_leg_right_desc), getResources().obtainTypedArray(R.array.single_leg_right), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest11), getResources().getString(R.string.str_spiderman_desc), getResources().obtainTypedArray(R.array.spiderman), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest12), getResources().getString(R.string.str_standard_desc), getResources().obtainTypedArray(R.array.standard_grip), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest13), getResources().getString(R.string.str_wide_grip_desc), getResources().obtainTypedArray(R.array.wide_grip), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_chest14), getResources().getString(R.string.str_with_jump_desc), getResources().obtainTypedArray(R.array.with_jump), getResources().getIntArray(R.array.fatburn_cycles));
            workoutExerciseModels.add(exerciseModel);
            CountDays = "Day 4";

            if (helper.getExerciseCount() > 0) {
                if (!helper.IsExistType(WorkoutType)) {
                    for (int i = 0; i < workoutExerciseModels.size(); i++) {
                        int[] getInts = new int[workoutExerciseModels.get(i).getExerciseImg().length()];
                        for (int j = 0; j < workoutExerciseModels.get(i).getExerciseImg().length(); j++) {
                            getInts[j] = workoutExerciseModels.get(i).getExerciseImg().getResourceId(j, 0);
                        }
                        helper.insertExerciseCycles(WorkoutType, workoutExerciseModels.get(i).getExerciseName(), workoutExerciseModels.get(i).getExerciseDesc(), getInts, workoutExerciseModels.get(i).getExerciseType()[i] + "");
                    }
                }
            } else {
                for (int i = 0; i < workoutExerciseModels.size(); i++) {
                    int[] getInts = new int[workoutExerciseModels.get(i).getExerciseImg().length()];
                    for (int j = 0; j < workoutExerciseModels.get(i).getExerciseImg().length(); j++) {
                        getInts[j] = workoutExerciseModels.get(i).getExerciseImg().getResourceId(j, 0);
                    }
                    helper.insertExerciseCycles(WorkoutType, workoutExerciseModels.get(i).getExerciseName(), workoutExerciseModels.get(i).getExerciseDesc(), getInts, workoutExerciseModels.get(i).getExerciseType()[i] + "");
                }
            }
        } else if (WorkoutType.contains("Morning")) {

            WorkoutExerciseModel exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_jumping_jacks), getResources().getString(R.string.str_desc_jumping_jacks), getResources().obtainTypedArray(R.array.jumping_jacks), getResources().getIntArray(R.array.morning_cycle));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm_crunches), getResources().getString(R.string.str_desc_arm_crunches), getResources().obtainTypedArray(R.array.arm_crunches), getResources().getIntArray(R.array.morning_cycle));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_high_knees), getResources().getString(R.string.str_desc_high_knees), getResources().obtainTypedArray(R.array.high_knees), getResources().getIntArray(R.array.morning_cycle));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_basic_crunches), getResources().getString(R.string.str_desc_basic_crunches), getResources().obtainTypedArray(R.array.basic_crunches), getResources().getIntArray(R.array.morning_cycle));
            workoutExerciseModels.add(exerciseModel);
            CountDays = "Day 5";

            if (helper.getExerciseCount() > 0) {
                if (!helper.IsExistType(WorkoutType)) {
                    for (int i = 0; i < workoutExerciseModels.size(); i++) {
                        int[] getInts = new int[workoutExerciseModels.get(i).getExerciseImg().length()];
                        for (int j = 0; j < workoutExerciseModels.get(i).getExerciseImg().length(); j++) {
                            getInts[j] = workoutExerciseModels.get(i).getExerciseImg().getResourceId(j, 0);
                        }
                        helper.insertExerciseCycles(WorkoutType, workoutExerciseModels.get(i).getExerciseName(), workoutExerciseModels.get(i).getExerciseDesc(), getInts, workoutExerciseModels.get(i).getExerciseType()[i] + "");
                    }
                }
            } else {
                for (int i = 0; i < workoutExerciseModels.size(); i++) {
                    int[] getInts = new int[workoutExerciseModels.get(i).getExerciseImg().length()];
                    for (int j = 0; j < workoutExerciseModels.get(i).getExerciseImg().length(); j++) {
                        getInts[j] = workoutExerciseModels.get(i).getExerciseImg().getResourceId(j, 0);
                    }
                    helper.insertExerciseCycles(WorkoutType, workoutExerciseModels.get(i).getExerciseName(), workoutExerciseModels.get(i).getExerciseDesc(), getInts, workoutExerciseModels.get(i).getExerciseType()[i] + "");
                }
            }
        } else if (WorkoutType.contains("Evening")) {

            WorkoutExerciseModel exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_jumping_jacks), getResources().getString(R.string.str_desc_jumping_jacks), getResources().obtainTypedArray(R.array.jumping_jacks), getResources().getIntArray(R.array.morning_cycle));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_arm_crunches), getResources().getString(R.string.str_desc_arm_crunches), getResources().obtainTypedArray(R.array.arm_crunches), getResources().getIntArray(R.array.morning_cycle));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_high_knees), getResources().getString(R.string.str_desc_high_knees), getResources().obtainTypedArray(R.array.high_knees), getResources().getIntArray(R.array.morning_cycle));
            workoutExerciseModels.add(exerciseModel);
            exerciseModel = new WorkoutExerciseModel(getResources().getString(R.string.str_basic_crunches), getResources().getString(R.string.str_desc_basic_crunches), getResources().obtainTypedArray(R.array.basic_crunches), getResources().getIntArray(R.array.morning_cycle));
            workoutExerciseModels.add(exerciseModel);
            CountDays = "Day 6";

            if (helper.getExerciseCount() > 0) {
                if (!helper.IsExistType(WorkoutType)) {
                    for (int i = 0; i < workoutExerciseModels.size(); i++) {
                        int[] getInts = new int[workoutExerciseModels.get(i).getExerciseImg().length()];
                        for (int j = 0; j < workoutExerciseModels.get(i).getExerciseImg().length(); j++) {
                            getInts[j] = workoutExerciseModels.get(i).getExerciseImg().getResourceId(j, 0);
                        }
                        helper.insertExerciseCycles(WorkoutType, workoutExerciseModels.get(i).getExerciseName(), workoutExerciseModels.get(i).getExerciseDesc(), getInts, workoutExerciseModels.get(i).getExerciseType()[i] + "");
                    }
                }
            } else {
                for (int i = 0; i < workoutExerciseModels.size(); i++) {
                    int[] getInts = new int[workoutExerciseModels.get(i).getExerciseImg().length()];
                    for (int j = 0; j < workoutExerciseModels.get(i).getExerciseImg().length(); j++) {
                        getInts[j] = workoutExerciseModels.get(i).getExerciseImg().getResourceId(j, 0);
                    }
                    helper.insertExerciseCycles(WorkoutType, workoutExerciseModels.get(i).getExerciseName(), workoutExerciseModels.get(i).getExerciseDesc(), getInts, workoutExerciseModels.get(i).getExerciseType()[i] + "");
                }
            }
        }
        if (helper.IsExistType(WorkoutType)) {
            helper.getExerciseRecords(WorkoutType);

            int[] IntCycles = new int[helper.getExerciseRecords(WorkoutType).size()];
            for (int i = 0; i < helper.getExerciseRecords(WorkoutType).size(); i++) {
                try {
                    IntCycles[i] = Integer.parseInt(String.valueOf(helper.getExerciseRecords(WorkoutType).get(i).getExerciseType()[0]));
                } catch (NumberFormatException nfe) {
                }
            }
            for (int i = 0; i < helper.getExerciseRecords(WorkoutType).size(); i++) {
                WorkoutExerciseModel exerciseModel = new WorkoutExerciseModel();
                exerciseModel.setExerciseName(helper.getExerciseRecords(WorkoutType).get(i).getExerciseName());
                exerciseModel.setExerciseDesc(helper.getExerciseRecords(WorkoutType).get(i).getExerciseDesc());
                exerciseModel.setExerciseImg(workoutExerciseModels.get(i).getExerciseImg());
                exerciseModel.setExerciseType(IntCycles);
                workoutExerciseModels.set(i, exerciseModel);
            }
        }
        return workoutExerciseModels;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ExerciseCode:
                switch (resultCode) {
                    case RESULT_OK:
                        int pos = data.getIntExtra(Constants.ExercisePos, 0);
                        if (helper.IsExistType(WorkoutType)) {
                            helper.getExerciseRecords(WorkoutType);

                            int[] IntCycles = new int[helper.getExerciseRecords(WorkoutType).size()];
                            for (int i = 0; i < helper.getExerciseRecords(WorkoutType).size(); i++) {
                                try {
                                    IntCycles[i] = Integer.parseInt(String.valueOf(helper.getExerciseRecords(WorkoutType).get(i).getExerciseType()[0]));
                                } catch (NumberFormatException nfe) {
                                }
                            }
                            for (int i = 0; i < helper.getExerciseRecords(WorkoutType).size(); i++) {
                                WorkoutExerciseModel exerciseModel = new WorkoutExerciseModel();
                                exerciseModel.setExerciseName(helper.getExerciseRecords(WorkoutType).get(i).getExerciseName());
                                exerciseModel.setExerciseDesc(helper.getExerciseRecords(WorkoutType).get(i).getExerciseDesc());
                                exerciseModel.setExerciseImg(workoutExerciseModels.get(i).getExerciseImg());
                                exerciseModel.setExerciseType(IntCycles);
                                workoutExerciseModels.set(i, exerciseModel);
                            }
                        }
                        workoutListAdapter.notifyItemChanged(pos);

                        break;
                }
                break;
        }
    }
}