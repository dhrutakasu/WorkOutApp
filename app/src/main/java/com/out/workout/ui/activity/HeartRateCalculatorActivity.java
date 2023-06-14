package com.out.workout.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.Ads.Ad_Interstitial;
import com.out.workout.R;
import com.out.workout.ui.adapter.SpinnerAdapters;
import com.out.workout.utils.Constants;
import com.out.workout.utils.Pref;

import java.text.NumberFormat;

import androidx.appcompat.app.AppCompatActivity;

public class HeartRateCalculatorActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtAgeHeart, EdtHeartRate;
    private Spinner SpinnerHeartRate, SpinnerGenderHeart;
    private TextView BtnHeartRate;
    private TextView BtnResetHeart;
    private ImageView BtnChartHeart;
    private double StrRate, StrRateSec;
    private double DoubleRate, DoubleAge;

    private double DoubleRatemax, DoubleRateMhr, DoubleRatemin;
    private boolean BoolCheck;
    private String str_Rate, str_RateMin, str_RateMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtAgeHeart = (EditText) findViewById(R.id.EdtAgeHeart);
        EdtHeartRate = (EditText) findViewById(R.id.EdtHeartRate);
        SpinnerGenderHeart = (Spinner) findViewById(R.id.SpinnerGenderHeart);
        SpinnerHeartRate = (Spinner) findViewById(R.id.SpinnerHeartRate);
        BtnHeartRate = (TextView) findViewById(R.id.BtnHeartRate);
        BtnResetHeart = (TextView) findViewById(R.id.BtnResetHeart);
        BtnChartHeart = (ImageView) findViewById(R.id.BtnChartHeart);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnHeartRate.setOnClickListener(this);
        BtnResetHeart.setOnClickListener(this);
        BtnChartHeart.setOnClickListener(this);
        SpinnerHeartRate.setOnItemSelectedListener(this);
    }

    private void initActions() {
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        TvTitle.setText(getString(R.string.str_heartrate));
        EdtAgeHeart.setText(String.valueOf(new Pref(context).getInt(Pref.CALCULATOR_AGE,Pref.AGE)));
        String[] GenderArr = {getResources().getString(R.string.str_male), getResources().getString(R.string.str_female)};
        String[] ArrHeartRate = {getResources().getString(R.string.str_moderate), getResources().getString(R.string.str_little_diff), getResources().getString(R.string.str_moderately_diff), getResources().getString(R.string.str_hard)};
        SpinnerHeartRate.setAdapter((SpinnerAdapter) new SpinnerAdapters(this, R.layout.item_spinner, ArrHeartRate));
        SpinnerGenderHeart.setAdapter((SpinnerAdapter) new SpinnerAdapters(this, R.layout.item_spinner, GenderArr));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnHeartRate:
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Load Ad....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Ad_Interstitial.getInstance().showInter(HeartRateCalculatorActivity.this, new Ad_Interstitial.MyCallback() {
                            @Override
                            public void callbackCall() {
                                GotoCalculateHeartRate();
                            }
                        });
                    }
                }, 3000L);
                break;
            case R.id.BtnResetHeart:
                GotoCalculateReset();
                break;
            case R.id.BtnChartHeart:
                GotoCalculateChart();
                break;
        }
    }

    private void GotoCalculateHeartRate() {
        String gender = (String) SpinnerGenderHeart.getSelectedItem().toString();
        try {
            try {
                DoubleAge = Double.parseDouble(EdtAgeHeart.getText().toString());
            } catch (NumberFormatException unused) {
                BoolCheck = true;
            }
            try {
                DoubleRate = Double.parseDouble(EdtHeartRate.getText().toString());
            } catch (NumberFormatException unused2) {
                BoolCheck = true;
            }
            if (BoolCheck) {
                BoolCheck = false;
                return;
            }
            if (gender.equalsIgnoreCase(getString(R.string.str_male))) {
                DoubleAge *= 0.8d;
                DoubleRateMhr = 214.0d - DoubleAge;
            } else {
                DoubleAge *= 0.9d;
                DoubleRateMhr = 209.0d - DoubleAge;
            }
            try {
                new Pref(context).putInt(Pref.CALCULATOR_AGE,Integer.parseInt(EdtAgeHeart.getText().toString()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            str_Rate = NumberFormat.getInstance().format(DoubleRateMhr);
            DoubleRateMhr -= DoubleRate;
            DoubleRatemin = (DoubleRateMhr * StrRate) + DoubleRate;
            DoubleRatemax = (DoubleRateMhr * StrRateSec) + DoubleRate;
            str_RateMin = NumberFormat.getInstance().format(DoubleRatemin);
            str_RateMax = NumberFormat.getInstance().format(DoubleRatemax);

            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_weight);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            Window window = dialog.getWindow();
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            window.setAttributes(lp);


            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            LinearLayout LlHeartRate = dialog.findViewById(R.id.LlHeartRate);
            TextView TvDialogMaxRateValue = dialog.findViewById(R.id.TvDialogMaxRateValue);
            TextView TvDialogTrainingRateValue = dialog.findViewById(R.id.TvDialogTrainingRateValue);
            TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);

            ImageView IvDialogBanner = dialog.findViewById(R.id.IvDialogBanner);
            TextView TvDialogName = dialog.findViewById(R.id.TvDialogName);
            TextView TvDialogDesc = dialog.findViewById(R.id.TvDialogDesc);

            IvDialogBanner.setImageResource(R.drawable.ic_heart_reate);
            TvDialogName.setText(getResources().getString(R.string.str_heartrate));
            TvDialogDesc.setText(getResources().getString(R.string.str_heart_desc));
            LlHeartRate.setVisibility(View.VISIBLE);

            TvDialogMaxRateValue.setText(str_Rate);
            TvDialogTrainingRateValue.setText(str_RateMin + " - " + str_RateMax);
            TvDialogWeightSubTitle.setText(getString(R.string.str_maxhrrate));

            BtnDialogWeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


            dialog.show();
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
        }
    }

    private void GotoCalculateReset() {
        EdtHeartRate.setText("");
        EdtAgeHeart.setText(String.valueOf(new Pref(context).getInt(Pref.CALCULATOR_AGE,Pref.AGE)));
        EdtAgeHeart.requestFocus();
        SpinnerHeartRate.setSelection(0);
        SpinnerHeartRate.setSelection(0);
    }

    private void GotoCalculateChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.str_heartrate)));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String toString = SpinnerHeartRate.getSelectedItem().toString();
        if (toString.equals(getResources().getString(R.string.str_moderate))) {
            StrRate = 0.6d;
            StrRateSec = 0.65d;
        } else if (toString.equals(getResources().getString(R.string.str_little_diff))) {
            StrRate = 0.65d;
            StrRateSec = 0.7d;
        } else if (toString.equals(getResources().getString(R.string.str_moderately_diff))) {
            StrRate = 0.7d;
            StrRateSec = 0.75d;
        } else {
            StrRate = 0.75d;
            StrRateSec = 0.8d;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}