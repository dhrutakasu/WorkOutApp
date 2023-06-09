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

public class BodyFatCalculatorActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtAgeBodyFate;
    private EditText EdtHeightBodyFate, EdtInchBodyFate, EdtWeightBodyFate, EdtWaistBodyFate, EdtNeckBodyFate, EdtArmBodyFate, EdtWristBodyFate, EdtHipBodyFate;
    private LinearLayout LLHeightBodyFate,  LLArmEdt,  LLWristEdt,  LLHipEdt;
    private TextView BtnWeightBodyFate;
    private TextView BtnResetBodyFate;
    private ImageView BtnChartBodyFate;
    private double DoubleHeight, DoubleWeight, DoubleAge, DoubleInch, DoubleWaist, DoubleNeck,DoubleArm,DoubleWrist,DoubleHip, DoubleBodyFate, DoubleFate, DoubleNewFate;
    private boolean BoolCheckBodyFate;
    private String calculate_BodyFate, calculateFate;
    private Spinner SpinnerGenderBodyFat,SpinnerHeightBodyFat,SpinnerWeightBodyFat,SpinnerWaistBodyFat,SpinnerArmBodyFat,SpinnerWristBodyFat,SpinnerNeckBodyFat,SpinnerHipBodyFat;
    private TextView TvFTOrCMBodyFat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_fat_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtAgeBodyFate = (EditText) findViewById(R.id.EdtAgeBodyFate);

        SpinnerGenderBodyFat = (Spinner) findViewById(R.id.SpinnerGenderBodyFat);
        SpinnerHeightBodyFat = (Spinner) findViewById(R.id.SpinnerHeightBodyFat);
        SpinnerWeightBodyFat = (Spinner) findViewById(R.id.SpinnerWeightBodyFat);
        SpinnerWaistBodyFat = (Spinner) findViewById(R.id.SpinnerWaistBodyFat);
        SpinnerArmBodyFat = (Spinner) findViewById(R.id.SpinnerArmBodyFat);
        SpinnerWristBodyFat = (Spinner) findViewById(R.id.SpinnerWristBodyFat);
        SpinnerNeckBodyFat = (Spinner) findViewById(R.id.SpinnerNeckBodyFat);
        SpinnerHipBodyFat = (Spinner) findViewById(R.id.SpinnerHipBodyFat);
        TvFTOrCMBodyFat = (TextView) findViewById(R.id.TvFTOrCMBodyFat);
        EdtHeightBodyFate = (EditText) findViewById(R.id.EdtHeightBodyFate);
        LLHeightBodyFate = (LinearLayout) findViewById(R.id.LLHeightBodyFate);
        LLArmEdt = (LinearLayout) findViewById(R.id.LlArmEdt);
        LLWristEdt = (LinearLayout) findViewById(R.id.LlWristEdt);
        LLHipEdt = (LinearLayout) findViewById(R.id.LlHipEdt);
        EdtInchBodyFate = (EditText) findViewById(R.id.EdtInchBodyFate);
        EdtWeightBodyFate = (EditText) findViewById(R.id.EdtWeightBodyFate);
        EdtWaistBodyFate = (EditText) findViewById(R.id.EdtWaistBodyFate);
        EdtNeckBodyFate = (EditText) findViewById(R.id.EdtNeckBodyFate);
        EdtArmBodyFate = (EditText) findViewById(R.id.EdtArmBodyFate);
        EdtWristBodyFate = (EditText) findViewById(R.id.EdtWristBodyFate);
        EdtHipBodyFate = (EditText) findViewById(R.id.EdtHipBodyFate);
        BtnWeightBodyFate = (TextView) findViewById(R.id.BtnWeightBodyFate);
        BtnResetBodyFate = (TextView) findViewById(R.id.BtnResetBodyFate);
        BtnChartBodyFate = (ImageView) findViewById(R.id.BtnChartBodyFate);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnWeightBodyFate.setOnClickListener(this);
        BtnResetBodyFate.setOnClickListener(this);
        BtnChartBodyFate.setOnClickListener(this);
        SpinnerGenderBodyFat.setOnItemSelectedListener(this);
        SpinnerWeightBodyFat.setOnItemSelectedListener(this);
    }

    private void initActions() {
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        TvTitle.setText(getString(R.string.str_bodyfat));
        TvFTOrCMBodyFat.setText(getString(R.string.str_cm));
        LLHeightBodyFate.setVisibility(View.GONE);
        EdtAgeBodyFate.setText(String.valueOf(new Pref(context).getInt(Pref.CALCULATOR_AGE,Pref.AGE)));

        String[] GenderArr = {getResources().getString(R.string.str_male), getResources().getString(R.string.str_female)};
        String[] HeightArr = {getResources().getString(R.string.str_centimeters), getResources().getString(R.string.str_feets)};
        String[] WeightArr = {getResources().getString(R.string.str_kilograms), getResources().getString(R.string.str_pounds)};
        SpinnerGenderBodyFat.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, GenderArr));
        SpinnerHeightBodyFat.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, HeightArr));
        SpinnerWeightBodyFat.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, WeightArr));
        SpinnerWaistBodyFat.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, HeightArr));
        SpinnerWristBodyFat.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, HeightArr));
        SpinnerNeckBodyFat.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, HeightArr));
        SpinnerArmBodyFat.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, HeightArr));
        SpinnerHipBodyFat.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, HeightArr));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnWeightBodyFate:
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Load Ad....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Ad_Interstitial.getInstance().showInter(BodyFatCalculatorActivity.this, new Ad_Interstitial.MyCallback() {
                            @Override
                            public void callbackCall() {
                                GotoCalculateWeight();
                            }
                        });
                    }
                }, 3000L);
                break;
            case R.id.BtnResetBodyFate:
                GotoCalculateReset();
                break;
            case R.id.BtnChartBodyFate:
                GotoCalculateChart();
                break;
        }
    }

    private void GotoCalculateWeight() {
        String gender = (String) SpinnerGenderBodyFat.getSelectedItem().toString();
        String weight = (String) SpinnerWeightBodyFat.getSelectedItem().toString();
        String BodyFateWeight = (String) SpinnerHeightBodyFat.getSelectedItem().toString();
        String BodyFateWaist = (String) SpinnerWaistBodyFat.getSelectedItem().toString();
        String BodyFateNeck = (String) SpinnerNeckBodyFat.getSelectedItem().toString();
        String BodyFateArm = (String) SpinnerArmBodyFat.getSelectedItem().toString();
        String BodyFateWrist = (String) SpinnerWristBodyFat.getSelectedItem().toString();
        String BodyFateHip = (String) SpinnerHipBodyFat.getSelectedItem().toString();
        try {
            if (gender.equalsIgnoreCase(getString(R.string.str_male))) {
                try {
                    DoubleHeight = Double.parseDouble(EdtHeightBodyFate.getText().toString());
                } catch (NumberFormatException unused) {
                    BoolCheckBodyFate = true;
                }
                try {
                    DoubleInch = Double.parseDouble(EdtInchBodyFate.getText().toString());
                } catch (NumberFormatException unused2) {
                    DoubleInch = 0.0d;
                }
                try {
                    DoubleWeight = Double.parseDouble(EdtWeightBodyFate.getText().toString());
                } catch (NumberFormatException unused3) {
                    BoolCheckBodyFate = true;
                }
                try {
                    DoubleWaist = Double.parseDouble(EdtWaistBodyFate.getText().toString());
                } catch (NumberFormatException unused4) {
                    BoolCheckBodyFate = true;
                }
                try {
                    DoubleNeck = Double.parseDouble(EdtNeckBodyFate.getText().toString());
                } catch (NumberFormatException unused5) {
                    BoolCheckBodyFate = true;
                }
                if (BoolCheckBodyFate) {
                    BoolCheckBodyFate = false;
                    return;
                }
                if (BodyFateWeight.equalsIgnoreCase(getResources().getString(R.string.str_centimeters))) {
                    DoubleWeight *= 2.20462d;
                }
                if (weight.equalsIgnoreCase(getResources().getString(R.string.str_kilograms))) {
                    DoubleHeight *= 0.393701d;
                } else {
                    DoubleHeight *= 12.0d;
                    DoubleHeight += DoubleInch;
                }
                if (BodyFateWaist.equalsIgnoreCase(getResources().getString(R.string.str_centimeters))) {
                    DoubleWaist *= 0.393701d;
                }
                if (BodyFateNeck.equalsIgnoreCase(getResources().getString(R.string.str_centimeters))) {
                    DoubleNeck *= 0.393701d;
                }
                DoubleBodyFate = (DoubleWeight * 1.082d) + 94.42d;
                DoubleFate = DoubleBodyFate - (DoubleWaist * 4.15d);
                DoubleNewFate = ((DoubleWeight - DoubleFate) * 100.0d) / DoubleWeight;
                if (DoubleNewFate >= 2.0d && DoubleNewFate <= 5.0d) {
                    calculate_BodyFate = getResources().getString(R.string.str_essential);
                } else if (DoubleNewFate >= 6.0d && DoubleNewFate <= 13.0d) {
                    calculate_BodyFate = getResources().getString(R.string.str_typicalathlete);
                } else if (DoubleNewFate >= 14.0d && DoubleNewFate <= 17.0d) {
                    calculate_BodyFate = getResources().getString(R.string.str_physicallyfit);
                } else if (DoubleNewFate < 18.0d || DoubleNewFate > 24.0d) {
                    calculate_BodyFate = getResources().getString(R.string.str_obese);
                } else {
                    calculate_BodyFate = getResources().getString(R.string.str_acceptable);
                }
                calculateFate = NumberFormat.getInstance().format(DoubleNewFate);
            } else {
                try {
                    DoubleHeight = Double.parseDouble(EdtHeightBodyFate.getText().toString());
                } catch (NumberFormatException unused) {
                    BoolCheckBodyFate = true;
                }
                try {
                    DoubleInch = Double.parseDouble(EdtInchBodyFate.getText().toString());
                } catch (NumberFormatException unused2) {
                    DoubleInch = 0.0d;
                }
                try {
                    DoubleWeight = Double.parseDouble(EdtWeightBodyFate.getText().toString());
                } catch (NumberFormatException unused3) {
                    BoolCheckBodyFate = true;
                }
                try {
                    DoubleWaist = Double.parseDouble(EdtWaistBodyFate.getText().toString());
                } catch (NumberFormatException unused4) {
                    BoolCheckBodyFate = true;
                }
                try {
                    DoubleNeck = Double.parseDouble(EdtNeckBodyFate.getText().toString());
                } catch (NumberFormatException unused5) {
                    BoolCheckBodyFate = true;
                }
                try {
                    DoubleArm = Double.parseDouble(EdtArmBodyFate.getText().toString());
                } catch (NumberFormatException unused6) {
                    BoolCheckBodyFate = true;
                }
                try {
                    DoubleWrist = Double.parseDouble(EdtWristBodyFate.getText().toString());
                } catch (NumberFormatException unused7) {
                    BoolCheckBodyFate = true;
                }
                try {
                    DoubleHip = Double.parseDouble(EdtHipBodyFate.getText().toString());
                } catch (NumberFormatException unused8) {
                    BoolCheckBodyFate = true;
                }
                if (BoolCheckBodyFate) {
                    BoolCheckBodyFate = false;
                    return;
                }
                if (weight.equalsIgnoreCase(getString(R.string.str_kilograms))) {
                    DoubleWeight *= 2.20462d;
                }
                if (BodyFateWeight.equalsIgnoreCase(getString(R.string.str_centimeters))) {
                    DoubleHeight *= 0.393701d;
                } else {
                    DoubleHeight *= 12.0d;
                    DoubleHeight += DoubleInch;
                }
                if (BodyFateWaist.equalsIgnoreCase(getString(R.string.str_centimeters))) {
                    DoubleWaist *= 0.393701d;
                }
                if (BodyFateNeck.equalsIgnoreCase(getString(R.string.str_centimeters))) {
                    DoubleNeck *= 0.393701d;
                }
                if (BodyFateArm.equalsIgnoreCase(getString(R.string.str_centimeters))) {
                    DoubleArm *= 0.393701d;
                }
                if (BodyFateWrist.equalsIgnoreCase(getString(R.string.str_centimeters))) {
                    DoubleWrist *= 0.393701d;
                }
                if (BodyFateHip.equalsIgnoreCase(getString(R.string.str_centimeters))) {
                    DoubleHip *= 0.393701d;
                }
                DoubleFate = (((((DoubleWeight * 0.0732d) + 8.987d) + (DoubleWaist / 3.14d)) - (DoubleWrist * 0.157d)) - (DoubleHip * 0.249d)) + (DoubleArm * 0.434d);
                DoubleNewFate = ((DoubleWeight - DoubleFate) * 100.0d) / DoubleWeight;
                if (DoubleNewFate >= 10.0d && DoubleNewFate <= 13.0d) {
                    calculate_BodyFate = getResources().getString(R.string.str_essential);
                } else if (DoubleNewFate >= 14.0d && DoubleNewFate <= 20.0d) {
                    calculate_BodyFate = getResources().getString(R.string.str_typicalathlete);
                } else if (DoubleNewFate >= 21.0d && DoubleNewFate <= 24.0d) {
                    calculate_BodyFate = getResources().getString(R.string.str_physicallyfit);
                } else if (DoubleNewFate < 25.0d || DoubleNewFate > 31.0d) {
                    calculate_BodyFate = getResources().getString(R.string.str_obese);
                } else {
                    calculate_BodyFate = getResources().getString(R.string.str_acceptable);
                }
                calculateFate = NumberFormat.getInstance().format(DoubleNewFate);
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


            LinearLayout LlBodyFate = dialog.findViewById(R.id.LlBodyFate);
            TextView TvDialogBodyFate = dialog.findViewById(R.id.TvDialogBodyFate);
            TextView TvAssessment = dialog.findViewById(R.id.TvAssessment);
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);


            ImageView IvDialogBanner = dialog.findViewById(R.id.IvDialogBanner);
            TextView TvDialogName = dialog.findViewById(R.id.TvDialogName);
            TextView TvDialogDesc = dialog.findViewById(R.id.TvDialogDesc);

            IvDialogBanner.setImageResource(R.drawable.ic_body_fat);
            TvDialogName.setText(getResources().getString(R.string.str_bodyfat));
            TvDialogDesc.setText(getResources().getString(R.string.str_bodyfat_desc));

            LlBodyFate.setVisibility(View.VISIBLE);
            TvDialogWeightSubTitle.setText(getString(R.string.str_urbodyfat));
            TvDialogBodyFate.setText(calculateFate);
            TvAssessment.setText(calculate_BodyFate);

            BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());


            dialog.show();
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
        }
    }

    private void GotoCalculateReset() {
        EdtHeightBodyFate.setText("");
        EdtAgeBodyFate.setText(String.valueOf(new Pref(context).getInt(Pref.CALCULATOR_AGE,Pref.AGE)));
        EdtInchBodyFate.setText("");
        EdtWeightBodyFate.setText("");
        EdtWaistBodyFate.setText("");
        EdtNeckBodyFate.setText("");
        EdtAgeBodyFate.requestFocus();

        SpinnerGenderBodyFat.setSelection(0);
        SpinnerHipBodyFat.setSelection(0);
        SpinnerWristBodyFat.setSelection(0);
        SpinnerWaistBodyFat.setSelection(0);
        SpinnerNeckBodyFat.setSelection(0);
        SpinnerWeightBodyFat.setSelection(0);
        SpinnerArmBodyFat.setSelection(0);
        SpinnerHeightBodyFat.setSelection(0);
        TvFTOrCMBodyFat.setText(getString(R.string.str_cm));
        LLHeightBodyFate.setVisibility(View.GONE);
    }

    private void GotoCalculateChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.str_bodyfat)));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.SpinnerHeightBodyFat:
                String weight = (String) SpinnerHeightBodyFat.getSelectedItem().toString();
                if (weight.equalsIgnoreCase(getString(R.string.str_centimeters))) {
                    TvFTOrCMBodyFat.setText(getString(R.string.str_cm));
                    EdtHeightBodyFate.setText("");
                    EdtInchBodyFate.setText("");
                    LLHeightBodyFate.setVisibility(View.GONE);
                } else {
                    TvFTOrCMBodyFat.setText(getString(R.string.str_ft));
                    EdtHeightBodyFate.setText("");
                    EdtInchBodyFate.setText("");
                    LLHeightBodyFate.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.SpinnerGenderBodyFat:
                String gender = (String) SpinnerGenderBodyFat.getSelectedItem().toString();
                if (gender.equalsIgnoreCase(getString(R.string.str_male))) {
                    EdtHeightBodyFate.setText("");
                    EdtInchBodyFate.setText("");
                    EdtWaistBodyFate.setText("");
                    EdtNeckBodyFate.setText("");
                    EdtArmBodyFate.setText("");
                    EdtWristBodyFate.setText("");
                    EdtHipBodyFate.setText("");
                    LLArmEdt.setVisibility(View.GONE);
                    LLWristEdt.setVisibility(View.GONE);
                    LLHipEdt.setVisibility(View.GONE);
                } else {
                    EdtHeightBodyFate.setText("");
                    EdtInchBodyFate.setText("");
                    EdtWaistBodyFate.setText("");
                    EdtNeckBodyFate.setText("");
                    EdtArmBodyFate.setText("");
                    EdtWristBodyFate.setText("");
                    EdtHipBodyFate.setText("");
                    LLArmEdt.setVisibility(View.VISIBLE);
                    LLWristEdt.setVisibility(View.VISIBLE);
                    LLHipEdt.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}