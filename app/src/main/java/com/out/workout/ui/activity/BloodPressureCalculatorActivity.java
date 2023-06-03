package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.Ads.Ad_Interstitial;
import com.out.workout.R;

public class BloodPressureCalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtSystolicBloodPressure, EdtDiastolicBloodPressure;
    private TextView BtnWeightBloodPressure;
    private TextView BtnResetBloodPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtSystolicBloodPressure = (EditText) findViewById(R.id.EdtSystolicBloodPressure);
        EdtDiastolicBloodPressure = (EditText) findViewById(R.id.EdtDiastolicBloodPressure);
        BtnWeightBloodPressure = (TextView) findViewById(R.id.BtnWeightBloodPressure);
        BtnResetBloodPressure = (TextView) findViewById(R.id.BtnResetBloodPressure);
    }

    private void initListeners() {
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        IvBack.setOnClickListener(this);
        BtnWeightBloodPressure.setOnClickListener(this);
        BtnResetBloodPressure.setOnClickListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.str_blood_pressure));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnWeightBloodPressure:
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Load Ad....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Ad_Interstitial.getInstance().showInter(BloodPressureCalculatorActivity.this, new Ad_Interstitial.MyCallback() {
                            @Override
                            public void callbackCall() {
                                GotoCalculationBloodPressure();
                            }
                        });
                    }
                }, 3000L);
                break;
            case R.id.BtnResetBloodPressure:
                GotoResetBloodPressure();
                break;
        }
    }

    private void GotoCalculationBloodPressure() {
        String CalculateBp;
        if (TextUtils.isEmpty(EdtSystolicBloodPressure.getText().toString())) {
            EdtSystolicBloodPressure.setError(getResources().getString(R.string.str_valid));
        } else if (TextUtils.isEmpty(EdtDiastolicBloodPressure.getText().toString())) {
            EdtDiastolicBloodPressure.setError(getResources().getString(R.string.str_valid));
        } else {
            float FloatSystolic = Float.parseFloat(EdtSystolicBloodPressure.getText().toString());
            float FloatDiastolic = Float.parseFloat(EdtDiastolicBloodPressure.getText().toString());
            if (FloatSystolic > 180.0f || FloatDiastolic > 110.0f) {
                CalculateBp = getResources().getString(R.string.str_hypertensive_crisis);
            } else if (FloatSystolic >= 160.0f || FloatDiastolic >= 100.0f) {
                CalculateBp = getResources().getString(R.string.str_high_bp_stage2);
            } else if (FloatSystolic > 140.0f || FloatDiastolic > 90.0f) {
                CalculateBp = getResources().getString(R.string.str_high_bp_stage1);
            } else if (FloatSystolic > 120.0f || FloatDiastolic > 80.0f) {
                CalculateBp = getResources().getString(R.string.str_prehypertension);
            } else if (FloatSystolic <= 80.0f || FloatDiastolic <= 60.0f) {
                CalculateBp = getResources().getString(R.string.str_low_bp);
            } else {
                CalculateBp = getResources().getString(R.string.str_normal_bp);
            }

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
            TextView TvDialogWeightValue = dialog.findViewById(R.id.TvDialogWeightValue);
            CardView CardIdealWeight = dialog.findViewById(R.id.CardIdealWeight);
            TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);

            ImageView IvDialogBanner = dialog.findViewById(R.id.IvDialogBanner);
            TextView TvDialogName = dialog.findViewById(R.id.TvDialogName);
            TextView TvDialogDesc = dialog.findViewById(R.id.TvDialogDesc);

            IvDialogBanner.setImageResource(R.drawable.ic_blood_pressure);
            TvDialogName.setText(getResources().getString(R.string.str_blood_pressure));
            TvDialogDesc.setText(getResources().getString(R.string.str_calc_bp_val));
            CardIdealWeight.setVisibility(View.VISIBLE);

            TvDialogWeightSubTitle.setText(getString(R.string.str_your_bp));
            TvDialogWeightValue.setText(String.valueOf(CalculateBp));

            BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());


            dialog.show();
        }
    }

    private void GotoResetBloodPressure() {
        EdtSystolicBloodPressure.setText("");
        EdtDiastolicBloodPressure.setText("");
        EdtSystolicBloodPressure.requestFocus();
    }
}