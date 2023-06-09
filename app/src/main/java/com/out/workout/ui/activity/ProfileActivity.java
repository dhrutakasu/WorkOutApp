package com.out.workout.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.out.workout.Ads.Ad_Interstitial;
import com.out.workout.Ads.Ad_Native;
import com.out.workout.BuildConfig;
import com.out.workout.R;
import com.out.workout.utils.Pref;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private CardView CardExerciseReminders, CardDayReminders, CardExerciseSounds,CardExercisePrivacy,CardExerciseShare,CardExerciseRate;
    private TextView TvCountDownTime, TvRestTime;
    private SwitchCompat SwitchSound;
    private ImageView IvCountDownMinus, IvCountDownPlus, IvRestMinus, IvRestPlus;
    private int IsCounter, IsRest;
    private boolean IsSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        CardExerciseReminders = (CardView) findViewById(R.id.CardExerciseReminders);
        CardDayReminders = (CardView) findViewById(R.id.CardDayReminders);
        SwitchSound = (SwitchCompat) findViewById(R.id.SwitchSound);
        CardExerciseSounds = (CardView) findViewById(R.id.CardExerciseSounds);
        CardExercisePrivacy = (CardView) findViewById(R.id.CardExercisePrivacy);
        CardExerciseShare = (CardView) findViewById(R.id.CardExerciseShare);
        CardExerciseRate = (CardView) findViewById(R.id.CardExerciseRate);
        IvCountDownMinus = (ImageView) findViewById(R.id.IvCountDownMinus);
        IvCountDownPlus = (ImageView) findViewById(R.id.IvCountDownPlus);
        TvCountDownTime = (TextView) findViewById(R.id.TvCountDownTime);
        IvRestMinus = (ImageView) findViewById(R.id.IvRestMinus);
        IvRestPlus = (ImageView) findViewById(R.id.IvRestPlus);
        TvRestTime = (TextView) findViewById(R.id.TvRestTime);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        CardExerciseReminders.setOnClickListener(this);
        CardDayReminders.setOnClickListener(this);
        CardExerciseSounds.setOnClickListener(this);
        CardExercisePrivacy.setOnClickListener(this);
        CardExerciseShare.setOnClickListener(this);
        CardExerciseRate.setOnClickListener(this);
        IvCountDownMinus.setOnClickListener(this);
        IvCountDownPlus.setOnClickListener(this);
        IvRestMinus.setOnClickListener(this);
        IvRestPlus.setOnClickListener(this);
        SwitchSound.setOnClickListener(this);
    }

    private void initActions() {
        Ad_Native.getInstance().showNative250(this, findViewById(R.id.FlNative));
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Load Ad....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
        Ad_Interstitial.getInstance().showInter(ProfileActivity.this, new Ad_Interstitial.MyCallback() {
                    @Override
                    public void callbackCall() {
                    }
                });
            }
        }, 3000L);
        TvTitle.setText(getResources().getString(R.string.str_profile));

        IsCounter =   new Pref(context).getInt(Pref.COUNT_TIMER,10);
        IsRest = new Pref(context).getInt(Pref.REST_TIMER,25);
        IsSound = new Pref(context).getBoolean(Pref.IS_SOUND,true);
        SwitchSound.setChecked(IsSound);
        TvCountDownTime.setText(String.valueOf(IsCounter));
        TvRestTime.setText(String.valueOf(IsRest).toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.CardExerciseReminders:
                GotoReminders();
                break;
            case R.id.CardDayReminders:
                GotoDayReminders();
                break;
            case R.id.CardExerciseSounds:
            case R.id.SwitchSound:
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
            case R.id.CardExercisePrivacy:
                GotoPrivacy();
                break;
            case R.id.CardExerciseShare:
                GotoShare();
                break;
            case R.id.CardExerciseRate:
                GotoRate();
                break;
        }
    }

    private void GotoReminders() {
        startActivity(new Intent(context, ExerciseRemindersActivity.class));
    }

    private void GotoDayReminders() {
        startActivity(new Intent(context, DayRemindersActivity.class));
    }

    private void GotoSound() {
        if (IsSound) {
            IsSound = false;
        } else {
            IsSound = true;
        }
        new Pref(context).putBoolean( Pref.IS_SOUND, IsSound);
        SwitchSound.setChecked(IsSound);
    }

    private void GotoCountDownMinus() {
        if (IsCounter > 5) {
            IsCounter--;
            TvCountDownTime.setText(String.valueOf(IsCounter));
            new Pref(context).getInt(Pref.COUNT_TIMER, IsCounter);
        }
    }

    private void GotoCountDownPlus() {
        if (IsCounter < 65) {
            IsCounter++;
            TvCountDownTime.setText(String.valueOf(IsCounter));
            new Pref(context).getInt(Pref.COUNT_TIMER, IsCounter);
        }
    }

    private void GotoRestMinus() {
        if (IsRest > 5) {
            IsRest--;
            TvRestTime.setText(String.valueOf(IsRest).toString());
            new Pref(context).putInt( Pref.REST_TIMER, IsRest);
        }
    }

    private void GotoRestPlus() {
        if (IsRest < 65) {
            IsRest++;
            TvRestTime.setText(String.valueOf(IsRest));
            new Pref(context).putInt( Pref.REST_TIMER, IsRest);
        }
    }
    private void GotoPrivacy() {
    }
    private void GotoShare() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage= "\nInstall this application: \n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share link:"));
        } catch(Exception e) {
        }
    }
    private void GotoRate() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}