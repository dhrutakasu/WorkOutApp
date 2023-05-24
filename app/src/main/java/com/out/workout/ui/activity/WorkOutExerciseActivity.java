package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getkeepsafe.android.multistateanimation.MultiStateAnimation;
import com.out.workout.Application.App;
import com.out.workout.R;
import com.out.workout.model.ExerciseModel;
import com.out.workout.model.WorkoutExerciseModel;
import com.out.workout.utils.Constants;
import com.out.workout.utils.SharePreference;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import at.grabner.circleprogress.CircleProgressView;

public class WorkOutExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private ArrayList<WorkoutExerciseModel> WorkoutExerciseList;
    private RelativeLayout RlReadyExercise, RlExerciseStart;
    private ImageView IvAnimatedWorkOutExercise, IvPlayReady;
    private TextView TvWorkOutExercise, TvWorkOutExerciseReadyDesc, TvSkipReady;
    private CircleProgressView CVProgressReady;
    private TextToSpeech tReady;
    private long ReadyCountDown;
    private boolean IsSound;

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
        RlReadyExercise = (RelativeLayout) findViewById(R.id.RlReadyExercise);
        RlExerciseStart = (RelativeLayout) findViewById(R.id.RlExerciseStart);
        IvAnimatedWorkOutExercise = (ImageView) findViewById(R.id.IvAnimatedWorkOutExercise);
        IvPlayReady = (ImageView) findViewById(R.id.IvPlayReady);
        TvWorkOutExercise = (TextView) findViewById(R.id.TvWorkOutExercise);
        TvWorkOutExerciseReadyDesc = (TextView) findViewById(R.id.TvWorkOutExerciseReadyDesc);
        CVProgressReady = (CircleProgressView) findViewById(R.id.CVProgressReady);
        TvSkipReady = (TextView) findViewById(R.id.TvSkipReady);
    }

    private void initIntents() {
        WorkoutExerciseList = Constants.WorkExerciseList;
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        IvPlayReady.setOnClickListener(this);
    }

    private void initActions() {
        tReady = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                tReady.setLanguage(Locale.US);
            }
        });
        RlReadyExercise.setVisibility(View.VISIBLE);
        RlExerciseStart.setVisibility(View.GONE);
        WorkoutExerciseModel model = WorkoutExerciseList.get(0);
        TvWorkOutExerciseReadyDesc.setText(model.getExerciseDesc());
        TvTitle.setText(model.getExerciseName());

        System.out.println("------ modelll : " + model.toString());
        MultiStateAnimation.SectionBuilder sectionBuilder = new MultiStateAnimation.SectionBuilder("pending");
        for (int i = 0; i < model.getExerciseImg().length(); i++) {
            sectionBuilder.addFrame(model.getExerciseImg().getResourceId(i, 0));
        }
        sectionBuilder.setOneshot(false);
        sectionBuilder.setFrameDuration(800);
        MultiStateAnimation stateAnimation = new MultiStateAnimation.Builder(IvAnimatedWorkOutExercise).addSection(sectionBuilder).build(context);
        stateAnimation.transitionNow("pending");

        IvPlayReady.setImageResource(R.drawable.ic_pause);

        ReadyCountDown = ((SharePreference.getInt(context, 10) * 1000) + 2000);
        System.out.println("----valll : " + Float.valueOf((ReadyCountDown - 2000) / 1000));
        CVProgressReady.setMaxValue(Float.valueOf((ReadyCountDown - 2000) / 1000).floatValue());
        CVProgressReady.setValue(Float.valueOf((ReadyCountDown - 2000) / 1000).floatValue());
        CVProgressReady.setText(String.valueOf((ReadyCountDown - 2000) / 1000).toString());

        IsSound = SharePreference.getBoolean(context, true);
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (IsSound) {
                    System.out.println("----valllrrr  : " + tReady);
                    if (tReady != null) {
                        tReady.setSpeechRate(1.0f);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Bundle bundle = new Bundle();
                            bundle.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC);
                            tReady.speak("Ready to go", TextToSpeech.QUEUE_FLUSH, bundle, null);
                        } else {
                            HashMap<String, String> param = new HashMap<>();
                            param.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
                            tReady.speak("Ready to go", TextToSpeech.QUEUE_FLUSH, param);
                        }
//                tReady.speak("Ready to go", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
        },200);*/
        if (IsSound) {
            if (App.textToSpeech != null) {
                App.textToSpeech.setSpeechRate(1.0f);
                App.getInstance().speak("READY TO GO");
//                        .speak("READY TO GO", TextToSpeech.QUEUE_ADD, null);
            }
        }
        GotoCounter(ReadyCountDown);
    }

    private void GotoCounter(long readyCountDown) {

        new CountDownTimer(readyCountDown, 1000) {
            public void onTick(long millisUntilFinished) {
                long shortNum = (millisUntilFinished - 1000) / 1000;
                CVProgressReady.setValue(Float.valueOf(shortNum).floatValue());
                System.out.println("----valllrrr shortNum : " + shortNum);

                CVProgressReady.setText(String.valueOf(shortNum).toString());
                if (shortNum < 4) {
                    if (IsSound) {
                        if (shortNum == 3) {
                            if (tReady != null) {
                                tReady.setSpeechRate(1.0f);
                                tReady.speak("Three", 1, null);
                            }
//                            tReady.speak("Three", TextToSpeech.QUEUE_FLUSH, null);
                        }
                        if (shortNum == 2) {
                            if (tReady != null) {
                                tReady.setSpeechRate(1.0f);
                                tReady.speak("Two", TextToSpeech.QUEUE_ADD, null);
                            }
//                            tReady.speak("Two", TextToSpeech.QUEUE_FLUSH, null);
                        }
                        if (shortNum == 1) {
                            if (tReady != null) {
                                tReady.setSpeechRate(1.0f);
                                tReady.speak("One", TextToSpeech.QUEUE_ADD, null);
                            }
                            if (tReady != null) {
                                tReady.setSpeechRate(1.0f);
                                tReady.speak("Start", TextToSpeech.QUEUE_ADD, null);
                            }
//                            tReady.speak("One", TextToSpeech.QUEUE_FLUSH, null);
//                            tReady.speak("Start", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                }
            }

            public void onFinish() {
                RlReadyExercise.setVisibility(View.GONE);
                RlExerciseStart.setVisibility(View.VISIBLE);
            }

        }.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.IvPlayReady:
                GotoPlayPause();
                break;
        }
    }

    protected void onPause() {
        if (tReady != null) {
            if (tReady.isSpeaking()) {
                tReady.stop();
                tReady.shutdown();
            }
        }
        super.onPause();
    }

    private void GotoPlayPause() {
        if (tReady != null) {
            if (tReady.isSpeaking()) {
                tReady.stop();
                tReady.shutdown();
            }
        }
    }
}