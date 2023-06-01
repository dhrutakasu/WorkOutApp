package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    private String WorkoutType;
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
    private Dialog dialogHelp;


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
        IvHelp = (ImageView) findViewById(R.id.IvHelp);
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
        WorkoutType = getIntent().getStringExtra(Constants.WorkoutType);
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

        ReadyCountDown = ((SharePreference.getInt(context, SharePreference.COUNT_TIMER, 10) * 1000) + 2000);
        System.out.println("----valll : " + Float.valueOf((ReadyCountDown - 2000) / 1000));
        CVProgressReady.setMaxValue(Float.valueOf((ReadyCountDown - 2000) / 1000).floatValue());
        CVProgressReady.setBlockCount((SharePreference.getInt(context, SharePreference.COUNT_TIMER, 10)));
        if (CVProgressReady.getBlockCount() >= 30) {
            CVProgressReady.setBlockScale(0.2f);
            CVProgressReady.setBarStrokeCap(Paint.Cap.ROUND);
        } else if (CVProgressReady.getBlockCount() >= 25) {
            CVProgressReady.setBlockScale(0.3f);
            CVProgressReady.setBarStrokeCap(Paint.Cap.ROUND);
        } else if (CVProgressReady.getBlockCount() >= 20) {
            CVProgressReady.setBlockScale(0.4f);
            CVProgressReady.setBarStrokeCap(Paint.Cap.ROUND);
        } else if (CVProgressReady.getBlockCount() >= 15) {
            CVProgressReady.setBlockScale(0.5f);
            CVProgressReady.setBarStrokeCap(Paint.Cap.ROUND);
        } else if (CVProgressReady.getBlockCount() >= 10) {
            CVProgressReady.setBlockScale(0.6f);
            CVProgressReady.setBarStrokeCap(Paint.Cap.ROUND);
        } else if (CVProgressReady.getBlockCount() >= 5) {
            CVProgressReady.setBlockScale(0.7f);
            CVProgressReady.setBarStrokeCap(Paint.Cap.ROUND);
        }
        CVProgressReady.setValue(Float.valueOf((ReadyCountDown - 2000) / 1000).floatValue());
        CVProgressReady.setText(String.valueOf((ReadyCountDown - 2000) / 1000));

        IsRest = SharePreference.getInt(context, SharePreference.REST_TIMER, 25);
        IsSound = SharePreference.getBoolean(context, SharePreference.IS_SOUND, true);

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
                RlRestExercise.setVisibility(View.GONE);
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
                countDownTimerReady.cancel();

                ExerciseTimer(model, ExCount);
            }
        }.start();
    }

    private void ExerciseTimer(WorkoutExerciseModel model, int exCount) {
        App.getInstance().addSpeaker();

        System.out.println("----- long ExTimer : " + ExTimer);
        ExerciseDownTimer = new CountDownTimer((ExTimer + 1) * 1000L, 1000) {
            public void onTick(long millisUntilFinished) {
                long shortNum = (millisUntilFinished - 1000) / 1000;
                NotedExerciseTimer = shortNum;
                App.getInstance().playSpeaker();
                System.out.println("----- long : " + millisUntilFinished);
                TvWorkOutExerciseTimer.setText(hmsTimeFormatter(millisUntilFinished));
            }

            public void onFinish() {
                TvPauseExercise.setText(getString(R.string.play));
                System.out.println("----- **** : " + exCount);
                if ((exCount + 1) > 0) {
                    IvExercisePrevious.setVisibility(View.VISIBLE);
                }
                System.out.println("-----*** : " + exCount);
                if ((exCount + 1) != WorkoutExerciseList.size()) {
                    IvHelp.setVisibility(View.GONE);
                    RlExerciseStart.setVisibility(View.GONE);
                    RlReadyExercise.setVisibility(View.GONE);
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
                    TvTitle.setText(Constants.getCapsSentences("Next " + (ExCount + 2) + "/" + WorkoutExerciseList.size()));
                    TvRestWorkOutExercise.setText(Constants.getCapsSentences(WorkoutExerciseList.get((exCount + 1)).getExerciseName()));
                    if (WorkoutExerciseList.get((exCount + 1)).getExerciseImg().length() > 1) {
                        TvWorkOutExerciseRestDesc.setText("x" + WorkoutExerciseList.get((exCount + 1)).getExerciseType()[(exCount)]);
                    } else {
                        TvWorkOutExerciseRestDesc.setText(WorkoutExerciseList.get((exCount + 1)).getExerciseType()[(exCount)] + " Sec");
                    }
                    IsRest = SharePreference.getInt(context, SharePreference.REST_TIMER, 25);
                    IntValRest = 0;
                    RestTimer();
                    ExerciseDownTimer.cancel();
                } else {
                    System.out.println("-----*** Welcome Come: " + exCount);
                }
            }
        }.start();
    }

    private void RestTimer() {
        IvPlayRest.setImageResource(R.drawable.ic_pause);
        System.out.println("----- exCount IsRest : " + IsRest);
        CVProgressRest.setBlockCount(SharePreference.getInt(context, SharePreference.REST_TIMER, 25));
        if (CVProgressRest.getBlockCount() >= 50) {
            CVProgressRest.setBlockScale(1f);
            CVProgressRest.setBarStrokeCap(Paint.Cap.BUTT);
        } else if (CVProgressRest.getBlockCount() >= 40) {
            CVProgressRest.setBlockScale(0.1f);
            CVProgressRest.setBarStrokeCap(Paint.Cap.BUTT);
        } else if (CVProgressRest.getBlockCount() >= 30) {
            CVProgressRest.setBlockScale(0.2f);
            CVProgressRest.setBarStrokeCap(Paint.Cap.ROUND);
        } else if (CVProgressRest.getBlockCount() >= 25) {
            CVProgressRest.setBlockScale(0.3f);
            CVProgressRest.setBarStrokeCap(Paint.Cap.ROUND);
        } else if (CVProgressRest.getBlockCount() >= 20) {
            CVProgressRest.setBlockScale(0.4f);
            CVProgressRest.setBarStrokeCap(Paint.Cap.ROUND);
        } else if (CVProgressRest.getBlockCount() >= 15) {
            CVProgressRest.setBlockScale(0.5f);
            CVProgressRest.setBarStrokeCap(Paint.Cap.ROUND);
        } else if (CVProgressRest.getBlockCount() >= 10) {
            CVProgressRest.setBlockScale(0.6f);
            CVProgressRest.setBarStrokeCap(Paint.Cap.ROUND);
        } else if (CVProgressRest.getBlockCount() >= 5) {
            CVProgressRest.setBlockScale(0.7f);
            CVProgressRest.setBarStrokeCap(Paint.Cap.ROUND);
        }
        CVProgressRest.setMaxValue(Float.valueOf(SharePreference.getInt(context, SharePreference.REST_TIMER, 25)).floatValue());
        RestTimer = new CountDownTimer((IsRest + 1) * 1000L, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println("----millis shortNum : " + millisUntilFinished);
                long shortNum = (millisUntilFinished - 1000) / 1000;
                NotedRestTimer = shortNum;
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
                RlReadyExercise.setVisibility(View.GONE);
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

                ExTimer = model.getExerciseType()[ExCount];
                IntValExercise = 0;
                ExerciseTimer(model, ExCount);
                RestTimer.cancel();
            }
        }.start();
    }

    private String hmsTimeFormatter(long milliSeconds) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milliSeconds), TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)), TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        return hms;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                if (countDownTimerReady != null) {
                    countDownTimerReady.cancel();
                }
                if (ExerciseDownTimer != null) {
                    ExerciseDownTimer.cancel();
                }
                if (RestTimer != null) {
                    RestTimer.cancel();
                }
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
        TvPauseExercise.setText(getString(R.string.play));
        BoolTimer = true;
        ExerciseDownTimer.cancel();

        dialogHelp = new Dialog(context);
        dialogHelp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogHelp.setContentView(R.layout.dialog_help);
        dialogHelp.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialogHelp.getWindow().getAttributes();
        Window window = dialogHelp.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);

        TextView BtnDialogExreciseName = dialogHelp.findViewById(R.id.BtnDialogExreciseName);
        ImageView IvAnimatedExercise = dialogHelp.findViewById(R.id.IvAnimatedExercise);
        TextView BtnDialogExreciseDesc = dialogHelp.findViewById(R.id.BtnDialogExreciseDesc);
        WorkoutExerciseModel model = WorkoutExerciseList.get(ExCount);
        MultiStateAnimation.SectionBuilder sectionBuilder = new MultiStateAnimation.SectionBuilder("pending");
        for (int i = 0; i < model.getExerciseImg().length(); i++) {
            sectionBuilder.addFrame(model.getExerciseImg().getResourceId(i, 0));
        }
        sectionBuilder.setOneshot(false);
        sectionBuilder.setFrameDuration(800);
        MultiStateAnimation stateAnimation = new MultiStateAnimation.Builder(IvAnimatedExercise).addSection(sectionBuilder).build(context);
        stateAnimation.transitionNow("pending");
        BtnDialogExreciseName.setText(Constants.getCapsSentences(WorkoutExerciseList.get((ExCount)).getExerciseName()));
        BtnDialogExreciseDesc.setText(Constants.getCapsSentences(WorkoutExerciseList.get((ExCount)).getExerciseDesc()));

        dialogHelp.show();
    }

    @Override
    public void onBackPressed() {
        if (dialogHelp != null && dialogHelp.isShowing()) {
            dialogHelp.dismiss();
        } else {
            if (App.textToSpeech != null) {
                if (App.textToSpeech.isSpeaking()) {
                    App.textToSpeech.stop();
                    App.textToSpeech.shutdown();
                }
            }
            if (RlReadyExercise.getVisibility() == View.VISIBLE) {
                IvPlayReady.setImageResource(R.drawable.ic_play);
                BoolTimer = true;
                countDownTimerReady.cancel();
            } else if (RlExerciseStart.getVisibility() == View.VISIBLE) {
                TvPauseExercise.setText(getString(R.string.play));
                BoolTimer = true;
                ExerciseDownTimer.cancel();
            } else if (RlRestExercise.getVisibility() == View.VISIBLE) {
                IvPlayRest.setImageResource(R.drawable.ic_play);
                BoolTimer = true;
                RestTimer.cancel();
            }

            Dialog dialogExit = new Dialog(context);
            dialogExit.setCancelable(false);
            dialogExit.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogExit.setContentView(R.layout.dialog_quit);
            dialogExit.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            WindowManager.LayoutParams lp = dialogExit.getWindow().getAttributes();
            Window window = dialogExit.getWindow();
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            window.setAttributes(lp);

            TextView BtnDialogExreciseExit = dialogExit.findViewById(R.id.BtnDialogExreciseExit);
            TextView BtnDialogExreciseNo = dialogExit.findViewById(R.id.BtnDialogExreciseNo);

            BtnDialogExreciseExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (RlReadyExercise.getVisibility() == View.VISIBLE) {
                        if (App.textToSpeech != null) {
                            if (App.textToSpeech.isSpeaking()) {
                                App.textToSpeech.stop();
                                App.textToSpeech.shutdown();
                            }
                        }
                        countDownTimerReady.cancel();
                    } else if (RlExerciseStart.getVisibility() == View.VISIBLE) {
                        ExerciseDownTimer.cancel();
                    } else if (RlRestExercise.getVisibility() == View.VISIBLE) {
                        RestTimer.cancel();
                    }
                    finish();
                }
            });

            BtnDialogExreciseNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (RlReadyExercise.getVisibility() == View.VISIBLE) {
                        BoolTimer = false;
                        IvPlayReady.setImageResource(R.drawable.ic_pause);
                        GotoCounter(NotedReadyTime);
                    } else if (RlExerciseStart.getVisibility() == View.VISIBLE) {
                        BoolTimer = false;
                        TvPauseExercise.setText(getString(R.string.pause));

                        if (ExCount > 0) {
                            IvExercisePrevious.setVisibility(View.VISIBLE);
                        }
                        RlReadyExercise.setVisibility(View.GONE);
                        RlRestExercise.setVisibility(View.GONE);
                        RlExerciseStart.setVisibility(View.VISIBLE);
                        IvHelp.setVisibility(View.VISIBLE);
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
                        ExTimer = (int) NotedExerciseTimer;
                        ExerciseTimer(model, ExCount);
                    } else if (RlRestExercise.getVisibility() == View.VISIBLE) {
                        BoolTimer = false;
                        IvPlayRest.setImageResource(R.drawable.ic_pause);
                        IsRest = (int) NotedRestTimer;
                        RestTimer();
                    }
                    dialogExit.dismiss();
                }
            });

            dialogExit.show();
        }
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
        System.out.println("----- exCount IntValExercise : " + IntValExercise);
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
            RlReadyExercise.setVisibility(View.GONE);
            RlRestExercise.setVisibility(View.GONE);
            RlExerciseStart.setVisibility(View.VISIBLE);
            IvHelp.setVisibility(View.VISIBLE);
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
            ExTimer = (int) NotedExerciseTimer;
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
        System.out.println("----- InRest : " + IntValRest + " - " + IsRest);
        if (IntValRest % 2 == 0) {
            IvPlayRest.setImageResource(R.drawable.ic_play);
            BoolTimer = true;
            RestTimer.cancel();
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
        RlReadyExercise.setVisibility(View.GONE);
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
        ExTimer = model.getExerciseType()[ExCount];

        System.out.println("-------- Excount : " + ExCount);
        ExerciseDownTimer.cancel();
        IntValExercise = 0;
        ExerciseTimer(model, ExCount);
    }

    private void GotoNextExercise() {
        System.out.println("----- exCount InRest : " + (ExCount));
        if ((WorkoutExerciseList.size() - 1) == ExCount) {
            int time = 0;
            for (int i = 0; i < WorkoutExerciseList.size(); i++) {
                time = time + WorkoutExerciseList.get(i).getExerciseImg().length() + 30;
            }

            ExerciseDownTimer.cancel();
            startActivity(new Intent(context, CompleteExerciseActivity.class).putExtra(Constants.ExerciseCount, WorkoutExerciseList.size()).putExtra(Constants.WorkoutType, WorkoutType).putExtra(Constants.ExerciseTime, time));
            finish();
        } else {
            ExerciseDownTimer.cancel();
            ExerciseDownTimer.onFinish();
            IsRest = SharePreference.getInt(context, SharePreference.REST_TIMER, 25);
        }
    }
}