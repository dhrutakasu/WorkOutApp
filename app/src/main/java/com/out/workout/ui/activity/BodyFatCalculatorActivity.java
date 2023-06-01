package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdSize;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.R;
import com.out.workout.ui.adapter.SpinnerAdapters;
import com.out.workout.utils.Constants;
import com.out.workout.utils.SharePreference;

import java.text.NumberFormat;

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

        TvTitle.setText(getString(R.string.bodyfat));
        TvFTOrCMBodyFat.setText(getString(R.string.cm));
        LLHeightBodyFate.setVisibility(View.GONE);
        EdtAgeBodyFate.setText(String.valueOf(SharePreference.getCalculatorAge(context)));

        String[] GenderArr = {getResources().getString(R.string.male), getResources().getString(R.string.female)};
        String[] HeightArr = {getResources().getString(R.string.centimeters), getResources().getString(R.string.feets)};
        String[] WeightArr = {getResources().getString(R.string.kilograms), getResources().getString(R.string.pounds)};
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
                GotoCalculateWeight();
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
        System.out.println("-- --- --- come : ");
        try {
            if (gender.equalsIgnoreCase(getString(R.string.male))) {
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
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                    BoolCheckBodyFate = false;
                    return;
                }
                if (BodyFateWeight.equalsIgnoreCase(getResources().getString(R.string.centimeters))) {
                    DoubleWeight *= 2.20462d;
                }
                if (weight.equalsIgnoreCase(getResources().getString(R.string.kilograms))) {
                    DoubleHeight *= 0.393701d;
                } else {
                    DoubleHeight *= 12.0d;
                    DoubleHeight += DoubleInch;
                }
                if (BodyFateWaist.equalsIgnoreCase(getResources().getString(R.string.centimeters))) {
                    DoubleWaist *= 0.393701d;
                }
                if (BodyFateNeck.equalsIgnoreCase(getResources().getString(R.string.centimeters))) {
                    DoubleNeck *= 0.393701d;
                }
                DoubleBodyFate = (DoubleWeight * 1.082d) + 94.42d;
                DoubleFate = DoubleBodyFate - (DoubleWaist * 4.15d);
                DoubleNewFate = ((DoubleWeight - DoubleFate) * 100.0d) / DoubleWeight;
                if (DoubleNewFate >= 2.0d && DoubleNewFate <= 5.0d) {
                    calculate_BodyFate = getResources().getString(R.string.essential);
                } else if (DoubleNewFate >= 6.0d && DoubleNewFate <= 13.0d) {
                    calculate_BodyFate = getResources().getString(R.string.typicalathlete);
                } else if (DoubleNewFate >= 14.0d && DoubleNewFate <= 17.0d) {
                    calculate_BodyFate = getResources().getString(R.string.physicallyfit);
                } else if (DoubleNewFate < 18.0d || DoubleNewFate > 24.0d) {
                    calculate_BodyFate = getResources().getString(R.string.obese);
                } else {
                    calculate_BodyFate = getResources().getString(R.string.acceptable);
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
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                    BoolCheckBodyFate = false;
                    return;
                }
                if (weight.equalsIgnoreCase(getString(R.string.kilograms))) {
                    DoubleWeight *= 2.20462d;
                }
                if (BodyFateWeight.equalsIgnoreCase(getString(R.string.centimeters))) {
                    DoubleHeight *= 0.393701d;
                } else {
                    DoubleHeight *= 12.0d;
                    DoubleHeight += DoubleInch;
                }
                if (BodyFateWaist.equalsIgnoreCase(getString(R.string.centimeters))) {
                    DoubleWaist *= 0.393701d;
                }
                if (BodyFateNeck.equalsIgnoreCase(getString(R.string.centimeters))) {
                    DoubleNeck *= 0.393701d;
                }
                if (BodyFateArm.equalsIgnoreCase(getString(R.string.centimeters))) {
                    DoubleArm *= 0.393701d;
                }
                if (BodyFateWrist.equalsIgnoreCase(getString(R.string.centimeters))) {
                    DoubleWrist *= 0.393701d;
                }
                if (BodyFateHip.equalsIgnoreCase(getString(R.string.centimeters))) {
                    DoubleHip *= 0.393701d;
                }
                DoubleFate = (((((DoubleWeight * 0.0732d) + 8.987d) + (DoubleWaist / 3.14d)) - (DoubleWrist * 0.157d)) - (DoubleHip * 0.249d)) + (DoubleArm * 0.434d);
                DoubleNewFate = ((DoubleWeight - DoubleFate) * 100.0d) / DoubleWeight;
                if (DoubleNewFate >= 10.0d && DoubleNewFate <= 13.0d) {
                    calculate_BodyFate = getResources().getString(R.string.essential);
                } else if (DoubleNewFate >= 14.0d && DoubleNewFate <= 20.0d) {
                    calculate_BodyFate = getResources().getString(R.string.typicalathlete);
                } else if (DoubleNewFate >= 21.0d && DoubleNewFate <= 24.0d) {
                    calculate_BodyFate = getResources().getString(R.string.physicallyfit);
                } else if (DoubleNewFate < 25.0d || DoubleNewFate > 31.0d) {
                    calculate_BodyFate = getResources().getString(R.string.obese);
                } else {
                    calculate_BodyFate = getResources().getString(R.string.acceptable);
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
            TvDialogName.setText(getResources().getString(R.string.bodyfat));
            TvDialogDesc.setText(getResources().getString(R.string.bodyfat_desc));

            LlBodyFate.setVisibility(View.VISIBLE);
            TvDialogWeightSubTitle.setText(getString(R.string.urbodyfat));
            TvDialogBodyFate.setText(calculateFate);
            TvAssessment.setText(calculate_BodyFate);

            BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());


            dialog.show();
        } catch (Resources.NotFoundException e2) {
            System.out.println("----- -- - - e22 come : " + e2.getMessage());
            e2.printStackTrace();
        }
    }

    private void GotoCalculateReset() {
        EdtHeightBodyFate.setText("");
        EdtAgeBodyFate.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
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
        TvFTOrCMBodyFat.setText(getString(R.string.cm));
        LLHeightBodyFate.setVisibility(View.GONE);
    }

    private void GotoCalculateChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.bodyfat)));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.SpinnerHeightBodyFat:
                String weight = (String) SpinnerHeightBodyFat.getSelectedItem().toString();
                if (weight.equalsIgnoreCase(getString(R.string.centimeters))) {
                    TvFTOrCMBodyFat.setText(getString(R.string.cm));
                    EdtHeightBodyFate.setText("");
                    EdtInchBodyFate.setText("");
                    LLHeightBodyFate.setVisibility(View.GONE);
                } else {
                    TvFTOrCMBodyFat.setText(getString(R.string.ft));
                    EdtHeightBodyFate.setText("");
                    EdtInchBodyFate.setText("");
                    LLHeightBodyFate.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.SpinnerGenderBodyFat:
                String gender = (String) SpinnerGenderBodyFat.getSelectedItem().toString();
                if (gender.equalsIgnoreCase(getString(R.string.male))) {
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