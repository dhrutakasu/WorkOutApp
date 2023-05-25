package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getkeepsafe.android.multistateanimation.MultiStateAnimation;
import com.out.workout.Application.App;
import com.out.workout.R;
import com.out.workout.model.WorkoutExerciseModel;
import com.out.workout.utils.Constants;
import com.out.workout.utils.SharePreference;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import at.grabner.circleprogress.CircleProgressView;

public class WorkOutExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack, IvHelp;
    private TextView TvTitle;
    private ArrayList<WorkoutExerciseModel> WorkoutExerciseList;
    private RelativeLayout RlReadyExercise, RlExerciseStart;
    private ImageView IvAnimatedReadyExercise, IvPlayReady;
    private TextView TvWorkOutExercise, TvWorkOutExerciseReadyDesc, TvSkipReady;
    private CircleProgressView CVProgressReady;
    private long ReadyCountDown;
    private boolean IsSound;
    private long NotedReadyTime, NotedRestTimer, NotedExerciseTimer;
    private int IntValReady = 0;
    private int IntValExercise = 0;
    private int IntValRest = 0;
    private CountDownTimer countDownTimerReady;
    private boolean BoolTimer;
    private TextView TvWorkOutExerciseCount, TvWorkOutExerciseTimer, TvPauseExercise;
    private ImageView IvAnimatedExercise, IvExerciseNext, IvExercisePrevious;
    private int ExCount = 0;
    private CountDownTimer ExerciseDownTimer;
    private int IsRest;
    private CountDownTimer RestTimer;
    private RelativeLayout RlRestExercise;
    private ImageView IvAnimatedRestExercise, IvPlayRest;
    private TextView TvRestWorkOutExercise, TvWorkOutExerciseRestDesc, TvSkipRest;
    private CircleProgressView CVProgressRest;
    private int ExTimer;

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
        IvHelp = (ImageView) findViewById(R.id.IvHelp);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        RlReadyExercise = (RelativeLayout) findViewById(R.id.RlReadyExercise);
        RlRestExercise = (RelativeLayout) findViewById(R.id.RlRestExercise);
        RlExerciseStart = (RelativeLayout) findViewById(R.id.RlExerciseStart);
        IvAnimatedReadyExercise = (ImageView) findViewById(R.id.IvAnimatedReadyExercise);
        IvAnimatedRestExercise = (ImageView) findViewById(R.id.IvAnimatedRestExercise);
        IvPlayReady = (ImageView) findViewById(R.id.IvPlayReady);
        IvPlayRest = (ImageView) findViewById(R.id.IvPlayRest);
        TvWorkOutExercise = (TextView) findViewById(R.id.TvWorkOutExercise);
        TvRestWorkOutExercise = (TextView) findViewById(R.id.TvRestWorkOutExercise);
        TvWorkOutExerciseReadyDesc = (TextView) findViewById(R.id.TvWorkOutExerciseReadyDesc);
        TvWorkOutExerciseRestDesc = (TextView) findViewById(R.id.TvWorkOutExerciseRestDesc);
        CVProgressReady = (CircleProgressView) findViewById(R.id.CVProgressReady);
        CVProgressRest = (CircleProgressView) findViewById(R.id.CVProgressRest);
        TvSkipReady = (TextView) findViewById(R.id.TvSkipReady);
        TvSkipRest = (TextView) findViewById(R.id.TvSkipRest);
        TvWorkOutExerciseCount = (TextView) findViewById(R.id.TvWorkOutExerciseCount);
        TvWorkOutExerciseTimer = (TextView) findViewById(R.id.TvWorkOutExerciseTimer);
        IvAnimatedExercise = (ImageView) findViewById(R.id.IvAnimatedExercise);
        TvPauseExercise = (TextView) findViewById(R.id.TvPauseExercise);
        IvExerciseNext = (ImageView) findViewById(R.id.IvExerciseNext);
        IvExercisePrevious = (ImageView) findViewById(R.id.IvExercisePrevious);
    }

    private void initIntents() {
        WorkoutExerciseList = Constants.WorkExerciseList;
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        IvHelp.setOnClickListener(this);
        IvPlayReady.setOnClickListener(this);
        TvSkipReady.setOnClickListener(this);
        IvPlayRest.setOnClickListener(this);
        TvSkipRest.setOnClickListener(this);
        TvPauseExercise.setOnClickListener(this);
        IvExercisePrevious.setOnClickListener(this);
        IvExerciseNext.setOnClickListener(this);
    }

    private void initActions() {
        RlReadyExercise.setVisibility(View.VISIBLE);
        RlExerciseStart.setVisibility(View.GONE);
        RlRestExercise.setVisibility(View.GONE);

        WorkoutExerciseModel model = WorkoutExerciseList.get(0);
        TvWorkOutExerciseReadyDesc.setText(model.getExerciseDesc());
        TvTitle.setText(Constants.getCapsSentences(model.getExerciseName()));

        System.out.println("------ modelll : " + model);
        MultiStateAnimation.SectionBuilder sectionBuilder = new MultiStateAnimation.SectionBuilder("pending");
        for (int i = 0; i < model.getExerciseImg().length(); i++) {
            sectionBuilder.addFrame(model.getExerciseImg().getResourceId(i, 0));
        }
        sectionBuilder.setOneshot(false);
        sectionBuilder.setFrameDuration(800);
        MultiStateAnimation stateAnimation = new MultiStateAnimation.Builder(IvAnimatedReadyExercise).addSection(sectionBuilder).build(context);
        stateAnimation.transitionNow("pending");

        IvPlayReady.setImageResource(R.drawable.ic_pause);

        System.out.println("----valll : " + Float.valueOf((ReadyCountDown - 2000) / 1000));
        ReadyCountDown = ((SharePreference.getInt(context, SharePreference.COUNT_TIMER, 10) * 1000) + 2000);
        CVProgressReady.setMaxValue(Float.valueOf((ReadyCountDown - 2000) / 1000).floatValue());
        CVProgressReady.setValue(Float.valueOf((ReadyCountDown - 2000) / 1000).floatValue());
        CVProgressReady.setText(String.valueOf((ReadyCountDown - 2000) / 1000));

        IsRest = SharePreference.getInt(context, SharePreference.REST_TIMER, 25);
        IsSound = SharePreference.getBoolean(context, true);

        if (IsSound) {
            if (App.textToSpeech != null) {
                App.textToSpeech.setSpeechRate(1.0f);
                App.getInstance().TextSpeak("READY TO GO");
            }
        }
        GotoCounter(ReadyCountDown);
    }

    private void GotoCounter(long readyCountDown) {
        countDownTimerReady = new CountDownTimer(readyCountDown, 1000) {
            public void onTick(long millisUntilFinished) {
                NotedReadyTime = millisUntilFinished;
                long shortNum = (millisUntilFinished - 1000) / 1000;
                CVProgressReady.setValue(Float.valueOf(shortNum).floatValue());
                System.out.println("----valllrrr shortNum : " + shortNum);

                CVProgressReady.setText(String.valueOf(shortNum));
                if (shortNum < 4) {
                    if (IsSound) {
                        if (shortNum == 3) {
                            if (App.textToSpeech != null) {
                                App.textToSpeech.setSpeechRate(1.0f);
                                App.textToSpeech.speak("Three", TextToSpeech.QUEUE_ADD, null);
                            }
                        }
                        if (shortNum == 2) {
                            if (App.textToSpeech != null) {
                                App.textToSpeech.setSpeechRate(1.0f);
                                App.textToSpeech.speak("Two", TextToSpeech.QUEUE_ADD, null);
                            }
                        }
                        if (shortNum == 1) {
                            if (App.textToSpeech != null) {
                                App.textToSpeech.setSpeechRate(1.0f);
                                App.textToSpeech.speak("One", TextToSpeech.QUEUE_ADD, null);
                            }
                            if (App.textToSpeech != null) {
                                App.textToSpeech.setSpeechRate(1.0f);
                                App.textToSpeech.speak("Start", TextToSpeech.QUEUE_ADD, null);
                            }
                        }
                    }
                }
            }

            public void onFinish() {
                RlReadyExercise.setVisibility(View.GONE);
                RlExerciseStart.setVisibility(View.VISIBLE);
                IvHelp.setVisibility(View.VISIBLE);
                TvWorkOutExerciseCount.setText((ExCount + 1) + " Of " + WorkoutExerciseList.size());
                System.out.println("----- exCount Ready : " + (ExCount + 2));
                WorkoutExerciseModel model = WorkoutExerciseList.get(ExCount);
                MultiStateAnimation.SectionBuilder sectionBuilder = new MultiStateAnimation.SectionBuilder("pending");
                for (int i = 0; i < model.getExerciseImg().length(); i++) {
                    sectionBuilder.addFrame(model.getExerciseImg().getResourceId(i, 0));
                }
                sectionBuilder.setOneshot(false);
                sectionBuilder.setFrameDuration(800);
                MultiStateAnimation stateAnimation = new MultiStateAnimation.Builder(IvAnimatedExercise).addSection(sectionBuilder).build(context);
                stateAnimation.transitionNow("pending");

                TvPauseExercise.setText(getString(R.string.pause));
                TvTitle.setText(Constants.getCapsSentences(WorkoutExerciseList.get((ExCount)).getExerciseName()));
                ExTimer = model.getExerciseType()[ExCount];
                ExerciseTimer(model, ExCount);
            }

        }.start();
    }

    private void ExerciseTimer(WorkoutExerciseModel model, int exCount) {
        App.getInstance().addSpeaker();

        System.out.println("----- long ExTimer : " + ExTimer);
        ExerciseDownTimer = new CountDownTimer((ExTimer + 1) * 1000L, 1000) {
            public void onTick(long millisUntilFinished) {
                NotedExerciseTimer = millisUntilFinished;
                App.getInstance().playSpeaker();
                System.out.println("----- long : " + millisUntilFinished);
                TvWorkOutExerciseTimer.setText(hmsTimeFormatter(millisUntilFinished));
            }

            public void onFinish() {
                TvPauseExercise.setText(getString(R.string.play));
                if (exCount > 0) {
                    IvExercisePrevious.setVisibility(View.VISIBLE);
                }
                IvHelp.setVisibility(View.GONE);
                RlExerciseStart.setVisibility(View.GONE);
                RlRestExercise.setVisibility(View.VISIBLE);

                WorkoutExerciseModel model = WorkoutExerciseList.get((exCount + 1));
                MultiStateAnimation.SectionBuilder sectionBuilder = new MultiStateAnimation.SectionBuilder("pending");
                for (int i = 0; i < model.getExerciseImg().length(); i++) {
                    sectionBuilder.addFrame(model.getExerciseImg().getResourceId(i, 0));
                }
                sectionBuilder.setOneshot(false);
                sectionBuilder.setFrameDuration(800);
                MultiStateAnimation stateAnimation = new MultiStateAnimation.Builder(IvAnimatedRestExercise).addSection(sectionBuilder).build(context);
                stateAnimation.transitionNow("pending");

                System.out.println("----- exCount exer : " + (ExCount + 1));
                TvTitle.setText(Constants.getCapsSentences("Next " + (ExCount + 1) + "/" + WorkoutExerciseList.size()));
                TvRestWorkOutExercise.setText(Constants.getCapsSentences(WorkoutExerciseList.get((exCount + 1)).getExerciseName()));
                if (WorkoutExerciseList.get((exCount + 1)).getExerciseImg().length() > 1) {
                    TvWorkOutExerciseRestDesc.setText("x" + WorkoutExerciseList.get((exCount + 1)).getExerciseType()[(exCount)]);
                } else {
                    TvWorkOutExerciseRestDesc.setText(WorkoutExerciseList.get((exCount + 1)).getExerciseType()[(exCount)] + " Sec");
                }
                IsRest = SharePreference.getInt(context, SharePreference.REST_TIMER, 25);
                if (exCount == WorkoutExerciseList.size()) {

                } else {
                    RestTimer();
                }
            }
        }.start();
    }

    private void RestTimer() {
        CVProgressRest.setBlockCount(IsRest);
        CVProgressRest.setBlockScale(0.45f);
        CVProgressRest.setMaxValue(Float.valueOf(IsRest).floatValue());
        RestTimer = new CountDownTimer((IsRest + 1) * 1000L, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                NotedRestTimer = (int) millisUntilFinished;
                System.out.println("----millis shortNum : " + millisUntilFinished);
                long shortNum = (millisUntilFinished - 1000) / 1000;
                System.out.println("----valllrrr shortNum : " + shortNum);
                CVProgressRest.setValue(Float.valueOf(shortNum).floatValue());

                CVProgressRest.setText(String.valueOf(shortNum));
                if (shortNum < 4) {
                    if (IsSound) {
                        if (shortNum == 3) {
                            if (App.textToSpeech != null) {
                                App.textToSpeech.setSpeechRate(1.0f);
                                App.textToSpeech.speak("Three", 1, null);
                            }
                        }
                        if (shortNum == 2) {
                            if (App.textToSpeech != null) {
                                App.textToSpeech.setSpeechRate(1.0f);
                                App.textToSpeech.speak("Two", TextToSpeech.QUEUE_ADD, null);
                            }
                        }
                        if (shortNum == 1) {
                            if (App.textToSpeech != null) {
                                App.textToSpeech.setSpeechRate(1.0f);
                                App.textToSpeech.speak("One", TextToSpeech.QUEUE_ADD, null);
                            }
                            if (App.textToSpeech != null) {
                                App.textToSpeech.setSpeechRate(1.0f);
                                App.textToSpeech.speak("Start", TextToSpeech.QUEUE_ADD, null);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFinish() {
                ExCount++;
                RlRestExercise.setVisibility(View.GONE);
                RlExerciseStart.setVisibility(View.VISIBLE);
                IvHelp.setVisibility(View.VISIBLE);
                TvWorkOutExerciseCount.setText((ExCount) + " Of " + WorkoutExerciseList.size());
                WorkoutExerciseModel model = WorkoutExerciseList.get(ExCount);
                MultiStateAnimation.SectionBuilder sectionBuilder = new MultiStateAnimation.SectionBuilder("pending");
                for (int i = 0; i < model.getExerciseImg().length(); i++) {
                    sectionBuilder.addFrame(model.getExerciseImg().getResourceId(i, 0));
                }
                sectionBuilder.setOneshot(false);
                sectionBuilder.setFrameDuration(800);
                MultiStateAnimation stateAnimation = new MultiStateAnimation.Builder(IvAnimatedExercise).addSection(sectionBuilder).build(context);
                stateAnimation.transitionNow("pending");

                TvPauseExercise.setText(getString(R.string.pause));
                TvTitle.setText(Constants.getCapsSentences(WorkoutExerciseList.get((ExCount)).getExerciseName()));
                System.out.println("----- exCount Rest : " + ExCount);
                ExTimer = model.getExerciseType()[ExCount];
                ExerciseTimer(model, ExCount);
            }
        }.start();
    }

    private String hmsTimeFormatter(long milliSeconds) {
        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        return hms;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.IvHelp:
                GotoHelp();
                break;
            case R.id.IvPlayReady:
                GotoPlayPauseReady();
                break;
            case R.id.TvPauseExercise:
                GotoPlayPauseExercise();
                break;
            case R.id.IvPlayRest:
                GotoPlayPauseRest();
                break;
            case R.id.TvSkipReady:
                GotoSkipReady();
                break;
            case R.id.TvSkipRest:
                GotoSkipRest();
                break;
            case R.id.IvExercisePrevious:
                GotoPreviousExercise();
                break;
            case R.id.IvExerciseNext:
                GotoNextExercise();
                break;
        }
    }


    @Override
    protected void onPause() {
        if (App.textToSpeech != null) {
            if (App.textToSpeech.isSpeaking()) {
                App.textToSpeech.stop();
                App.textToSpeech.shutdown();
            }
        }
        super.onPause();
    }

    private void GotoHelp() {

    }

    private void GotoPlayPauseReady() {
        if (App.textToSpeech != null) {
            if (App.textToSpeech.isSpeaking()) {
                App.textToSpeech.stop();
                App.textToSpeech.shutdown();
            }
        }
        if (IntValReady % 2 == 0) {
            IvPlayReady.setImageResource(R.drawable.ic_play);
            BoolTimer = true;
            countDownTimerReady.cancel();
        } else {
            BoolTimer = false;
            IvPlayReady.setImageResource(R.drawable.ic_pause);
            GotoCounter(NotedReadyTime);
        }
        IntValReady++;
    }

    private void GotoPlayPauseExercise() {
        if (IntValExercise % 2 == 0) {
            TvPauseExercise.setText(getString(R.string.play));
            BoolTimer = true;
            ExerciseDownTimer.cancel();
        } else {
            BoolTimer = false;
            TvPauseExercise.setText(getString(R.string.pause));

            if (ExCount > 0) {
                IvExercisePrevious.setVisibility(View.VISIBLE);
            }
            IvHelp.setVisibility(View.GONE);
            RlExerciseStart.setVisibility(View.GONE);
            RlRestExercise.setVisibility(View.VISIBLE);
            WorkoutExerciseModel model = WorkoutExerciseList.get(ExCount);
            MultiStateAnimation.SectionBuilder sectionBuilder = new MultiStateAnimation.SectionBuilder("pending");
            for (int i = 0; i < model.getExerciseImg().length(); i++) {
                sectionBuilder.addFrame(model.getExerciseImg().getResourceId(i, 0));
            }
            sectionBuilder.setOneshot(false);
            sectionBuilder.setFrameDuration(800);
            MultiStateAnimation stateAnimation = new MultiStateAnimation.Builder(IvAnimatedExercise).addSection(sectionBuilder).build(context);
            stateAnimation.transitionNow("pending");

            TvPauseExercise.setText(getString(R.string.pause));
            TvTitle.setText(Constants.getCapsSentences(WorkoutExerciseList.get((ExCount)).getExerciseName()));
            NotedExerciseTimer = ExTimer;
            ExerciseTimer(model, ExCount);
        }
        IntValExercise++;
    }

    private void GotoPlayPauseRest() {
        if (App.textToSpeech != null) {
            if (App.textToSpeech.isSpeaking()) {
                App.textToSpeech.stop();
                App.textToSpeech.shutdown();
            }
        }
        if (IntValRest % 2 == 0) {
            IvPlayRest.setImageResource(R.drawable.ic_play);
            BoolTimer = true;
            countDownTimerReady.cancel();
        } else {
            BoolTimer = false;
            IvPlayRest.setImageResource(R.drawable.ic_pause);
            IsRest = (int) NotedRestTimer;
            RestTimer();
        }
        IntValRest++;
    }

    private void GotoSkipReady() {
        countDownTimerReady.cancel();
        countDownTimerReady.onFinish();
    }

    private void GotoSkipRest() {
        RestTimer.cancel();
        RestTimer.onFinish();
    }

    private void GotoPreviousExercise() {
        ExCount--;
        TvPauseExercise.setText(getString(R.string.play));
        if (ExCount > 0) {
            IvExercisePrevious.setVisibility(View.VISIBLE);
        } else {
            IvExercisePrevious.setVisibility(View.GONE);
        }
        IvHelp.setVisibility(View.GONE);
        RlExerciseStart.setVisibility(View.GONE);
        RlRestExercise.setVisibility(View.VISIBLE);
        WorkoutExerciseModel model = WorkoutExerciseList.get(ExCount);
        MultiStateAnimation.SectionBuilder sectionBuilder = new MultiStateAnimation.SectionBuilder("pending");
        for (int i = 0; i < model.getExerciseImg().length(); i++) {
            sectionBuilder.addFrame(model.getExerciseImg().getResourceId(i, 0));
        }
        sectionBuilder.setOneshot(false);
        sectionBuilder.setFrameDuration(800);
        MultiStateAnimation stateAnimation = new MultiStateAnimation.Builder(IvAnimatedExercise).addSection(sectionBuilder).build(context);
        stateAnimation.transitionNow("pending");

        TvPauseExercise.setText(getString(R.string.pause));
        TvTitle.setText(Constants.getCapsSentences(WorkoutExerciseList.get((ExCount)).getExerciseName()));
        ExTimer = model.getExerciseType()[ExCount];
        System.out.println("-------- Excount : " + ExCount);
        ExerciseTimer(model, ExCount);
    }

    private void GotoNextExercise() {
        ExCount++;
        if (ExCount > 0) {
            IvExercisePrevious.setVisibility(View.VISIBLE);
        } else {
            IvExercisePrevious.setVisibility(View.GONE);
        }
        RlRestExercise.setVisibility(View.GONE);
        RlExerciseStart.setVisibility(View.VISIBLE);
        IvHelp.setVisibility(View.VISIBLE);
        TvWorkOutExerciseCount.setText((ExCount + 1) + " Of " + WorkoutExerciseList.size());
        WorkoutExerciseModel model = WorkoutExerciseList.get(ExCount);
        MultiStateAnimation.SectionBuilder sectionBuilder = new MultiStateAnimation.SectionBuilder("pending");
        for (int i = 0; i < model.getExerciseImg().length(); i++) {
            sectionBuilder.addFrame(model.getExerciseImg().getResourceId(i, 0));
        }
        sectionBuilder.setOneshot(false);
        sectionBuilder.setFrameDuration(800);
        MultiStateAnimation stateAnimation = new MultiStateAnimation.Builder(IvAnimatedExercise).addSection(sectionBuilder).build(context);
        stateAnimation.transitionNow("pending");

        TvPauseExercise.setText(getString(R.string.pause));
        TvTitle.setText(Constants.getCapsSentences(WorkoutExerciseList.get((ExCount)).getExerciseName()));
        System.out.println("----- exCount Rest : " + ExCount);
        IsRest = SharePreference.getInt(context, SharePreference.REST_TIMER, 25);
        RestTimer();
    }
}