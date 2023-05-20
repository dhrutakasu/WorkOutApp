package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.out.workout.R;
import com.out.workout.utils.Constants;

import java.util.HashMap;

public class WorkoutListActivity extends AppCompatActivity {

    private String WorkoutType;
    private HashMap<String, Integer> hashMapExcAnimResIds;
    private HashMap<String, Integer> hashMapExcDescription;

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

    }

    private void initIntents() {
        WorkoutType = getIntent().getStringExtra(Constants.WorkoutType);
    }

    private void initListeners() {

    }

    private void initActions() {
        if (WorkoutType.contains("Arm")) {

            hashMapExcAnimResIds.put(getResources().getString(R.string.arm), Integer.valueOf(R.array.triceps_dips));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm1), Integer.valueOf(R.array.pushups));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm2), Integer.valueOf(R.array.decline_pushups));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm3), Integer.valueOf(R.array.incline_pushups));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm4), Integer.valueOf(R.array.army_pushups));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm5), Integer.valueOf(R.array.arm_scissors));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm6), Integer.valueOf(R.array.triceps_stretch_left));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm7), Integer.valueOf(R.array.triceps_stretch_right));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm8), Integer.valueOf(R.array.standing_biceps_stretch_left));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm9), Integer.valueOf(R.array.standing_biceps_stretch_right));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm10), Integer.valueOf(R.array.elbow_back));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm11), Integer.valueOf(R.array.arm_crunches_left));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm12), Integer.valueOf(R.array.arm_crunches_right));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm13), Integer.valueOf(R.array.wall_pushups));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm14), Integer.valueOf(R.array.jumping_jacks));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm15), Integer.valueOf(R.array.plank_taps));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm16), Integer.valueOf(R.array.punches));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm17), Integer.valueOf(R.array.knee_pushups));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm18), Integer.valueOf(R.array.pushup_low_hold));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm19), Integer.valueOf(R.array.wide_arm_pushups));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm20), Integer.valueOf(R.array.diamond_pushups));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm21), Integer.valueOf(R.array.plank));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm22), Integer.valueOf(R.array.shoulder_gators));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm23), Integer.valueOf(R.array.skip_the_rope));
            hashMapExcAnimResIds.put(getResources().getString(R.string.arm24), Integer.valueOf(R.array.plank_alternate_reach));

            hashMapExcDescription.put(getResources().getString(R.string.arm), Integer.valueOf(R.string.desc_triceps_dips));
            hashMapExcDescription.put(getResources().getString(R.string.arm1), Integer.valueOf(R.string.desc_pushups));
            hashMapExcDescription.put(getResources().getString(R.string.arm2), Integer.valueOf(R.string.desc_decline_pushups));
            hashMapExcDescription.put(getResources().getString(R.string.arm3), Integer.valueOf(R.string.desc_incline_pushups));
            hashMapExcDescription.put(getResources().getString(R.string.arm4), Integer.valueOf(R.string.desc_army_pushups));
            hashMapExcDescription.put(getResources().getString(R.string.arm5), Integer.valueOf(R.string.desc_arm_scissors));
            hashMapExcDescription.put(getResources().getString(R.string.arm6), Integer.valueOf(R.string.desc_triceps_stretch_left));
            hashMapExcDescription.put(getResources().getString(R.string.arm7), Integer.valueOf(R.string.desc_triceps_stretch_right));
            hashMapExcDescription.put(getResources().getString(R.string.arm8), Integer.valueOf(R.string.desc_standing_biceps_stretch_left));
            hashMapExcDescription.put(getResources().getString(R.string.arm9), Integer.valueOf(R.string.desc_standing_biceps_stretch_right));
            hashMapExcDescription.put(getResources().getString(R.string.arm10), Integer.valueOf(R.string.desc_elbow_back));
            hashMapExcDescription.put(getResources().getString(R.string.arm11), Integer.valueOf(R.string.desc_arm_crunches_left));
            hashMapExcDescription.put(getResources().getString(R.string.arm12), Integer.valueOf(R.string.desc_arm_crunches_right));
            hashMapExcDescription.put(getResources().getString(R.string.arm13), Integer.valueOf(R.string.desc_wall_pushups));
            hashMapExcDescription.put(getResources().getString(R.string.arm14), Integer.valueOf(R.string.desc_jumping_jacks));
            hashMapExcDescription.put(getResources().getString(R.string.arm15), Integer.valueOf(R.string.desc_plank_taps));
            hashMapExcDescription.put(getResources().getString(R.string.arm16), Integer.valueOf(R.string.desc_punches));
            hashMapExcDescription.put(getResources().getString(R.string.arm17), Integer.valueOf(R.string.desc_knee_pushups));
            hashMapExcDescription.put(getResources().getString(R.string.arm18), Integer.valueOf(R.string.desc_pushup_low_hold));
            hashMapExcDescription.put(getResources().getString(R.string.arm19), Integer.valueOf(R.string.desc_wide_arm_pushups));
            hashMapExcDescription.put(getResources().getString(R.string.arm20), Integer.valueOf(R.string.desc_diamond_pushups));
            hashMapExcDescription.put(getResources().getString(R.string.arm21), Integer.valueOf(R.string.desc_plank));
            hashMapExcDescription.put(getResources().getString(R.string.arm22), Integer.valueOf(R.string.desc_shoulder_gators));
            hashMapExcDescription.put(getResources().getString(R.string.arm23), Integer.valueOf(R.string.desc_skip_the_rope));
            hashMapExcDescription.put(getResources().getString(R.string.arm24), Integer.valueOf(R.string.desc_plank_alternate_reach));
        } else if (WorkoutType.contains("Weight Loss")) {
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose), Integer.valueOf(R.array.vertical_leg_crunches));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose1), Integer.valueOf(R.array.v_crunches));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose2), Integer.valueOf(R.array.lunge));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose3), Integer.valueOf(R.array.squats));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose4), Integer.valueOf(R.array.pushups));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose5), Integer.valueOf(R.array.leg_up_crunches));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose6), Integer.valueOf(R.array.jumping_jacks));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose7), Integer.valueOf(R.array.plank_with_leg_lift));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose8), Integer.valueOf(R.array.high_knees));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose9), Integer.valueOf(R.array.basic_crunches));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose10), Integer.valueOf(R.array.alternater_heeltouch));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose11), Integer.valueOf(R.array.bicycle_crunches));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose12), Integer.valueOf(R.array.flutter_kicks));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose13), Integer.valueOf(R.array.arm_crunches));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose14), Integer.valueOf(R.array.bench_dips));
            hashMapExcAnimResIds.put(getResources().getString(R.string.lose15), Integer.valueOf(R.array.leg_raise));

            hashMapExcDescription.put(getResources().getString(R.string.lose), Integer.valueOf(R.string.desc_vertical_leg_crunches));
            hashMapExcDescription.put(getResources().getString(R.string.lose1), Integer.valueOf(R.string.desc_v_crunches));
            hashMapExcDescription.put(getResources().getString(R.string.lose2), Integer.valueOf(R.string.desc_lunge));
            hashMapExcDescription.put(getResources().getString(R.string.lose3), Integer.valueOf(R.string.desc_squats));
            hashMapExcDescription.put(getResources().getString(R.string.lose4), Integer.valueOf(R.string.desc_pushups));
            hashMapExcDescription.put(getResources().getString(R.string.lose5), Integer.valueOf(R.string.desc_leg_up_crunches));
            hashMapExcDescription.put(getResources().getString(R.string.lose6), Integer.valueOf(R.string.desc_jumping_jacks));
            hashMapExcDescription.put(getResources().getString(R.string.lose7), Integer.valueOf(R.string.desc_plank_with_leg_lift));
            hashMapExcDescription.put(getResources().getString(R.string.lose8), Integer.valueOf(R.string.desc_high_knees));
            hashMapExcDescription.put(getResources().getString(R.string.lose9), Integer.valueOf(R.string.desc_basic_crunches));
            hashMapExcDescription.put(getResources().getString(R.string.lose10), Integer.valueOf(R.string.desc_alternater_heeltouch));
            hashMapExcDescription.put(getResources().getString(R.string.lose11), Integer.valueOf(R.string.desc_bicycle_crunches));
            hashMapExcDescription.put(getResources().getString(R.string.lose12), Integer.valueOf(R.string.desc_flutter_kicks));
            hashMapExcDescription.put(getResources().getString(R.string.lose13), Integer.valueOf(R.string.desc_arm_crunches));
            hashMapExcDescription.put(getResources().getString(R.string.lose14), Integer.valueOf(R.string.desc_bench_dips));
            hashMapExcDescription.put(getResources().getString(R.string.lose15), Integer.valueOf(R.string.desc_leg_raise));

        } else if (WorkoutType.contains("Abs")) {

            hashMapExcAnimResIds.put(getResources().getString(R.string.abs), Integer.valueOf(R.array.abs_v_crunch));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs1), Integer.valueOf(R.array.abs_clapping_crunches));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs2), Integer.valueOf(R.array.abs_flutter_kicks));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs3), Integer.valueOf(R.array.abs_plank));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs4), Integer.valueOf(R.array.abs_reverse_crunches));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs5), Integer.valueOf(R.array.abs_bent_leg_twist));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs6), Integer.valueOf(R.array.abs_bicycle_crunches));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs7), Integer.valueOf(R.array.abs_cross_arm_crunches));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs8), Integer.valueOf(R.array.abs_x_man_crunch));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs9), Integer.valueOf(R.array.abs_dumbbell_paddle_boats));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs10), Integer.valueOf(R.array.abs_dumbbell_crunches));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs11), Integer.valueOf(R.array.abs_dumbbell_crunch_and_punches));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs12), Integer.valueOf(R.array.abs_side_leg_rise_left));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs13), Integer.valueOf(R.array.abs_side_leg_rise_right));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs14), Integer.valueOf(R.array.abs_lying_twist_stretch_right));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs15), Integer.valueOf(R.array.abs_one_down_two_ups));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs16), Integer.valueOf(R.array.abs_dumbbell_toe_touch_crunch_right));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs17), Integer.valueOf(R.array.abs_dumbbell_toe_touch_crunch_left));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs18), Integer.valueOf(R.array.abs_v_hold));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs19), Integer.valueOf(R.array.abs_dumbbell_bicycle_passes));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs20), Integer.valueOf(R.array.abs_dumbbell_torture_tucks));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs21), Integer.valueOf(R.array.abs_seated_abs_counterclockwise));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs22), Integer.valueOf(R.array.abs_seated_abs_clockwise));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs23), Integer.valueOf(R.array.abs_ninty_crunch));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs24), Integer.valueOf(R.array.abs_dumbbell_side_bend_right));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs25), Integer.valueOf(R.array.abs_dumbbell_side_bend_left));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs26), Integer.valueOf(R.array.abs_cross_knee_plank));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs27), Integer.valueOf(R.array.abs_oblique_v_ups_left));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs28), Integer.valueOf(R.array.abs_oblique_v_ups_right));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs29), Integer.valueOf(R.array.abs_crunches_with_legs_raised));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs30), Integer.valueOf(R.array.abs_alt_v_up));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs31), Integer.valueOf(R.array.abs_lying_twist_stretch_left));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs32), Integer.valueOf(R.array.abs_childs_pose));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs33), Integer.valueOf(R.array.abs_cobra_stretch));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs34), Integer.valueOf(R.array.abs_side_plank_left));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs35), Integer.valueOf(R.array.abs_side_plank_right));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs36), Integer.valueOf(R.array.abs_dumbbell_russian_twist));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs37), Integer.valueOf(R.array.abs_v_up));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs38), Integer.valueOf(R.array.abs_side_crunches_right));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs39), Integer.valueOf(R.array.abs_side_crunches_left));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs40), Integer.valueOf(R.array.abs_sit_up_twist));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs41), Integer.valueOf(R.array.abs_heels_to_the_heaven));
            hashMapExcAnimResIds.put(getResources().getString(R.string.abs42), Integer.valueOf(R.array.abs_heel_touch));

            hashMapExcDescription.put(getResources().getString(R.string.abs), Integer.valueOf(R.string.v_crunch_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs1), Integer.valueOf(R.string.clapping_crunches_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs2), Integer.valueOf(R.string.side_crunches_right_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs3), Integer.valueOf(R.string.side_crunches_left_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs4), Integer.valueOf(R.string.flutter_kicks_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs5), Integer.valueOf(R.string.plank_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs6), Integer.valueOf(R.string.reverse_crunches_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs7), Integer.valueOf(R.string.bent_leg_twist_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs8), Integer.valueOf(R.string.bicycle_crunches_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs9), Integer.valueOf(R.string.cross_arm_crunches_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs10), Integer.valueOf(R.string.x_man_crunch_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs11), Integer.valueOf(R.string.dumbbell_paddle_boats_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs12), Integer.valueOf(R.string.dumbbell_crunches_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs13), Integer.valueOf(R.string.dumbbell_crunch_and_punches_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs14), Integer.valueOf(R.string.side_leg_rise_left_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs15), Integer.valueOf(R.string.side_leg_rise_right_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs16), Integer.valueOf(R.string.lying_twist_stretch_right_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs17), Integer.valueOf(R.string.one_down_two_ups_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs18), Integer.valueOf(R.string.dumbbell_toe_touch_crunch_right_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs19), Integer.valueOf(R.string.dumbbell_toe_touch_crunch_left_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs20), Integer.valueOf(R.string.v_hold_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs21), Integer.valueOf(R.string.dumbbell_bicycle_passes_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs22), Integer.valueOf(R.string.dumbbell_torture_tucks_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs23), Integer.valueOf(R.string.seated_abs_counterclockwise_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs24), Integer.valueOf(R.string.seated_abs_clockwise_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs25), Integer.valueOf(R.string.ninty_crunch_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs26), Integer.valueOf(R.string.dumbbell_side_bend_right_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs27), Integer.valueOf(R.string.dumbbell_side_bend_left_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs28), Integer.valueOf(R.string.cross_knee_plank_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs29), Integer.valueOf(R.string.oblique_v_ups_left_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs30), Integer.valueOf(R.string.oblique_v_ups_right_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs31), Integer.valueOf(R.string.crunches_with_legs_raised_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs32), Integer.valueOf(R.string.alt_v_up_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs33), Integer.valueOf(R.string.lying_twist_stretch_left_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs34), Integer.valueOf(R.string.childs_pose_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs35), Integer.valueOf(R.string.cobra_stretch_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs36), Integer.valueOf(R.string.side_plank_left_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs37), Integer.valueOf(R.string.side_plank_right_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs38), Integer.valueOf(R.string.sit_up_twist_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs39), Integer.valueOf(R.string.dumbbell_russian_twist_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs40), Integer.valueOf(R.string.heels_to_the_heaven_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs41), Integer.valueOf(R.string.v_up_desc));
            hashMapExcDescription.put(getResources().getString(R.string.abs42), Integer.valueOf(R.string.heel_touch_desc));

        } else if (WorkoutType.contains("Chest")) {
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest), Integer.valueOf(R.array.atomic));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest1), Integer.valueOf(R.array.clap));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest2), Integer.valueOf(R.array.elevated_hands));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest3), Integer.valueOf(R.array.eccentric));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest4), Integer.valueOf(R.array.feet_elivated));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest5), Integer.valueOf(R.array.narrow_grip));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest6), Integer.valueOf(R.array.pike));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest7), Integer.valueOf(R.array.single_arm_left));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest8), Integer.valueOf(R.array.single_arm_right));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest9), Integer.valueOf(R.array.single_leg_left));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest10), Integer.valueOf(R.array.single_leg_right));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest11), Integer.valueOf(R.array.spiderman));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest12), Integer.valueOf(R.array.standard_grip));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest13), Integer.valueOf(R.array.wide_grip));
            hashMapExcAnimResIds.put(getResources().getString(R.string.chest14), Integer.valueOf(R.array.with_jump));

            hashMapExcDescription.put(getResources().getString(R.string.chest), Integer.valueOf(R.string.atomic_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest1), Integer.valueOf(R.string.clap_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest2), Integer.valueOf(R.string.elevated_hands_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest3), Integer.valueOf(R.string.eccentric_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest4), Integer.valueOf(R.string.feet_elevated_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest5), Integer.valueOf(R.string.narrow_grip_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest6), Integer.valueOf(R.string.pike_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest7), Integer.valueOf(R.string.single_arm_left_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest8), Integer.valueOf(R.string.single_arm_right_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest9), Integer.valueOf(R.string.single_leg_left_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest10), Integer.valueOf(R.string.single_leg_right_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest11), Integer.valueOf(R.string.spiderman_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest12), Integer.valueOf(R.string.standard_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest13), Integer.valueOf(R.string.wide_grip_desc));
            hashMapExcDescription.put(getResources().getString(R.string.chest14), Integer.valueOf(R.string.with_jump_desc));

        }



    }
}