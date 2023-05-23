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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.out.workout.R;
import com.out.workout.utils.Constants;
import com.out.workout.utils.SharePreference;

import java.text.NumberFormat;

public class BodyFatCalculatorActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtAgeBodyFate;
    private RadioGroup RgGender, RgWeight, RgWeightBodyFate, RgWaistBodyFate, RgNeckBodyFate, RgArmBodyFate, RgWristBodyFate, RgHipBodyFate;
    private RadioButton RbMale, RbFemale, RbCm, RbInch, RbWaistCm, RbWaistInch, RbNeckCm, RbNeckInch;
    private RadioButton RbArmCm, RbArmInch, RbWristCm, RbWristInch, RbHipCm, RbHipInch;
    private EditText EdtHeightBodyFate, EdtInchBodyFate, EdtWeightBodyFate, EdtWaistBodyFate, EdtNeckBodyFate, EdtArmBodyFate, EdtWristBodyFate, EdtHipBodyFate;
    private LinearLayout LLHeightBodyFate, LLArmRg, LLArmEdt, LLWristRg, LLWristEdt, LLHipRg, LLHipEdt;
    private Button BtnWeightBodyFate, BtnResetBodyFate,BtnChartBodyFate;
    private double DoubleHeight, DoubleWeight, DoubleAge, DoubleInch, DoubleWaist, DoubleNeck,DoubleArm,DoubleWrist,DoubleHip, DoubleBodyFate, DoubleFate, DoubleNewFate;
    private boolean BoolCheckBodyFate;
    private String calculate_BodyFate, calculateFate;

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
        RgGender = (RadioGroup) findViewById(R.id.RgGender);
        RbMale = (RadioButton) findViewById(R.id.RbMale);
        RbFemale = (RadioButton) findViewById(R.id.RbFemale);
        RgWeight = (RadioGroup) findViewById(R.id.RgWeight);
        RgWeightBodyFate = (RadioGroup) findViewById(R.id.RgWeightBodyFate);
        RbCm = (RadioButton) findViewById(R.id.RbCm);
        RbInch = (RadioButton) findViewById(R.id.RbInch);
        RgWaistBodyFate = (RadioGroup) findViewById(R.id.RgWaistBodyFate);
        RbWaistCm = (RadioButton) findViewById(R.id.RbWaistCm);
        RbWaistInch = (RadioButton) findViewById(R.id.RbWaistInch);
        RgNeckBodyFate = (RadioGroup) findViewById(R.id.RgNeckBodyFate);
        RbNeckCm = (RadioButton) findViewById(R.id.RbNeckCm);
        RbNeckInch = (RadioButton) findViewById(R.id.RbNeckInch);
        RgArmBodyFate = (RadioGroup) findViewById(R.id.RgArmBodyFate);
        RbArmCm = (RadioButton) findViewById(R.id.RbArmCm);
        RbArmInch = (RadioButton) findViewById(R.id.RbArmInch);
        RgWristBodyFate = (RadioGroup) findViewById(R.id.RgWristBodyFate);
        RbWristCm = (RadioButton) findViewById(R.id.RbWristCm);
        RbWristInch = (RadioButton) findViewById(R.id.RbWristInch);
        RgHipBodyFate = (RadioGroup) findViewById(R.id.RgHipBodyFate);
        RbHipCm = (RadioButton) findViewById(R.id.RbHipCm);
        RbHipInch = (RadioButton) findViewById(R.id.RbHipInch);
        EdtHeightBodyFate = (EditText) findViewById(R.id.EdtHeightBodyFate);
        LLHeightBodyFate = (LinearLayout) findViewById(R.id.LLHeightBodyFate);
        LLArmRg = (LinearLayout) findViewById(R.id.LlArmRg);
        LLArmEdt = (LinearLayout) findViewById(R.id.LlArmEdt);
        LLWristRg = (LinearLayout) findViewById(R.id.LlWristRg);
        LLWristEdt = (LinearLayout) findViewById(R.id.LlWristEdt);
        LLHipRg = (LinearLayout) findViewById(R.id.LlHipRg);
        LLHipEdt = (LinearLayout) findViewById(R.id.LlHipEdt);
        EdtInchBodyFate = (EditText) findViewById(R.id.EdtInchBodyFate);
        EdtWeightBodyFate = (EditText) findViewById(R.id.EdtWeightBodyFate);
        EdtWaistBodyFate = (EditText) findViewById(R.id.EdtWaistBodyFate);
        EdtNeckBodyFate = (EditText) findViewById(R.id.EdtNeckBodyFate);
        EdtArmBodyFate = (EditText) findViewById(R.id.EdtArmBodyFate);
        EdtWristBodyFate = (EditText) findViewById(R.id.EdtWristBodyFate);
        EdtHipBodyFate = (EditText) findViewById(R.id.EdtHipBodyFate);
        BtnWeightBodyFate = (Button) findViewById(R.id.BtnWeightBodyFate);
        BtnResetBodyFate = (Button) findViewById(R.id.BtnResetBodyFate);
        BtnChartBodyFate = (Button) findViewById(R.id.BtnChartBodyFate);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnWeightBodyFate.setOnClickListener(this);
        BtnResetBodyFate.setOnClickListener(this);
        BtnChartBodyFate.setOnClickListener(this);
        RgGender.setOnCheckedChangeListener(this);
        RgWeight.setOnCheckedChangeListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.bodyfat));
        LLHeightBodyFate.setVisibility(View.GONE);
        EdtAgeBodyFate.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
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
        RadioButton radioGenderButton = (RadioButton) findViewById(RgGender.getCheckedRadioButtonId());
        String gender = (String) radioGenderButton.getText();
        RadioButton radioWeightButton = (RadioButton) findViewById(RgWeight.getCheckedRadioButtonId());
        String weight = (String) radioWeightButton.getText();
        RadioButton radioBodyFateWeightButton = (RadioButton) findViewById(RgWeightBodyFate.getCheckedRadioButtonId());
        String BodyFateWeight = (String) radioBodyFateWeightButton.getText();
        RadioButton radioBodyFateWaistButton = (RadioButton) findViewById(RgWaistBodyFate.getCheckedRadioButtonId());
        String BodyFateWaist = (String) radioBodyFateWaistButton.getText();
        RadioButton radioBodyFateNeckButton = (RadioButton) findViewById(RgNeckBodyFate.getCheckedRadioButtonId());
        String BodyFateNeck = (String) radioBodyFateNeckButton.getText();
        RadioButton radioBodyFateArmButton = (RadioButton) findViewById(RgArmBodyFate.getCheckedRadioButtonId());
        String BodyFateArm = (String) radioBodyFateArmButton.getText();
        RadioButton radioBodyFateWristButton = (RadioButton) findViewById(RgWristBodyFate.getCheckedRadioButtonId());
        String BodyFateWrist = (String) radioBodyFateWristButton.getText();
        RadioButton radioBodyFateHipButton = (RadioButton) findViewById(RgHipBodyFate.getCheckedRadioButtonId());
        String BodyFateHip = (String) radioBodyFateHipButton.getText();
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
                if (BodyFateWeight.equalsIgnoreCase(getResources().getString(R.string.kilograms))) {
                    DoubleWeight *= 2.20462d;
                }
                if (weight.equalsIgnoreCase(getResources().getString(R.string.cm))) {
                    DoubleHeight *= 0.393701d;
                } else {
                    DoubleHeight *= 12.0d;
                    DoubleHeight += DoubleInch;
                }
                if (BodyFateWaist.equalsIgnoreCase(getResources().getString(R.string.cm))) {
                    DoubleWaist *= 0.393701d;
                }
                if (BodyFateNeck.equalsIgnoreCase(getResources().getString(R.string.cm))) {
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
                if (BodyFateWeight.equalsIgnoreCase(getString(R.string.cm))) {
                    DoubleHeight *= 0.393701d;
                } else {
                    DoubleHeight *= 12.0d;
                    DoubleHeight += DoubleInch;
                }
                if (BodyFateWaist.equalsIgnoreCase(getString(R.string.cm))) {
                    DoubleWaist *= 0.393701d;
                }
                if (BodyFateNeck.equalsIgnoreCase(getString(R.string.cm))) {
                    DoubleNeck *= 0.393701d;
                }
                if (BodyFateArm.equalsIgnoreCase(getString(R.string.cm))) {
                    DoubleArm *= 0.393701d;
                }
                if (BodyFateWrist.equalsIgnoreCase(getString(R.string.cm))) {
                    DoubleWrist *= 0.393701d;
                }
                if (BodyFateHip.equalsIgnoreCase(getString(R.string.cm))) {
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

            ImageView IvWeightClose = dialog.findViewById(R.id.IvWeightClose);
            LinearLayout LlBodyFate = dialog.findViewById(R.id.LlBodyFate);
            TextView TvDialogBodyFate = dialog.findViewById(R.id.TvDialogBodyFate);
            TextView TvAssessment = dialog.findViewById(R.id.TvAssessment);
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            Button BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);

            LlBodyFate.setVisibility(View.VISIBLE);
            TvDialogWeightSubTitle.setText(getString(R.string.urbodyfat));
            TvDialogBodyFate.setText(calculateFate);
            TvAssessment.setText(calculate_BodyFate);

            BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());

            IvWeightClose.setOnClickListener(view -> dialog.dismiss());
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

        RadioButton radioGenderButton = (RadioButton) RgGender.getChildAt(0);
        radioGenderButton.setChecked(true);
        RadioButton radioWeightButton = (RadioButton) RgWeight.getChildAt(0);
        radioWeightButton.setChecked(true);
        RadioButton radioBodyFateWeightButton = (RadioButton) RgWeightBodyFate.getChildAt(0);
        radioBodyFateWeightButton.setChecked(true);
        RadioButton radioBodyFateWaistButton = (RadioButton) RgWaistBodyFate.getChildAt(0);
        radioBodyFateWaistButton.setChecked(true);
        RadioButton radioBodyFateNeckButton = (RadioButton) RgNeckBodyFate.getChildAt(0);
        radioBodyFateNeckButton.setChecked(true);
        RadioButton radioBodyFateArmButton = (RadioButton) RgArmBodyFate.getChildAt(0);
        radioBodyFateArmButton.setChecked(true);
        RadioButton radioBodyFateWristButton = (RadioButton) RgWristBodyFate.getChildAt(0);
        radioBodyFateWristButton.setChecked(true);
        RadioButton radioBodyFateHipButton = (RadioButton) RgHipBodyFate.getChildAt(0);
        radioBodyFateHipButton.setChecked(true);
    }

    private void GotoCalculateChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.bodyfat)));
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getId()) {
            case R.id.RgWeight:
                RadioButton radioWeightButton = (RadioButton) findViewById(RgWeight.getCheckedRadioButtonId());
                String weight = (String) radioWeightButton.getText();
                if (weight.equalsIgnoreCase("CM")) {
                    EdtHeightBodyFate.setText("");
                    EdtInchBodyFate.setText("");
                    LLHeightBodyFate.setVisibility(View.GONE);
                } else {
                    EdtHeightBodyFate.setText("");
                    EdtInchBodyFate.setText("");
                    LLHeightBodyFate.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.RgGender:
                RadioButton radioGenderButton = (RadioButton) findViewById(RgGender.getCheckedRadioButtonId());
                String gender = (String) radioGenderButton.getText();
                if (gender.equalsIgnoreCase(getString(R.string.male))) {
                    EdtHeightBodyFate.setText("");
                    EdtInchBodyFate.setText("");
                    EdtWaistBodyFate.setText("");
                    EdtNeckBodyFate.setText("");
                    EdtArmBodyFate.setText("");
                    EdtWristBodyFate.setText("");
                    EdtHipBodyFate.setText("");
                    LLArmRg.setVisibility(View.GONE);
                    LLArmEdt.setVisibility(View.GONE);
                    LLWristRg.setVisibility(View.GONE);
                    LLWristEdt.setVisibility(View.GONE);
                    LLHipRg.setVisibility(View.GONE);
                    LLHipEdt.setVisibility(View.GONE);
                } else {
                    EdtHeightBodyFate.setText("");
                    EdtInchBodyFate.setText("");
                    EdtWaistBodyFate.setText("");
                    EdtNeckBodyFate.setText("");
                    EdtArmBodyFate.setText("");
                    EdtWristBodyFate.setText("");
                    EdtHipBodyFate.setText("");
                    LLArmRg.setVisibility(View.VISIBLE);
                    LLArmEdt.setVisibility(View.VISIBLE);
                    LLWristRg.setVisibility(View.VISIBLE);
                    LLWristEdt.setVisibility(View.VISIBLE);
                    LLHipRg.setVisibility(View.VISIBLE);
                    LLHipEdt.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}