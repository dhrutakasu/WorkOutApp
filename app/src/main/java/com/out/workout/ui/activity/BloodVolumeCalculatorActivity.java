
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.out.workout.R;
import com.out.workout.utils.SharePreference;

import java.text.NumberFormat;

public class BloodVolumeCalculatorActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private RadioGroup RgGender, RgWeight, RgWeightBloodVolume;
    private EditText EdtAgeBloodVolume,EdtHeightBloodVolume, EdtInchBloodVolume, EdtWeightBloodVolume;
    private LinearLayout LLHeightBloodVolume;
    private Button BtnWeightBloodVolume, BtnResetBloodVolume;
    private double DoubleHeight, DoubleWeight, DoubleAge, DoubleInch;
    private boolean check;
    private double calculate;
    private String calculate_Kg, calculate_BMI;
    private int calculate_BMI_Int;
    private double DoubleBloodVolume;
    private String StrBloodVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_volume_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        RgGender = (RadioGroup) findViewById(R.id.RgGender);
        RgWeight = (RadioGroup) findViewById(R.id.RgWeight);
        EdtAgeBloodVolume = (EditText) findViewById(R.id.EdtAgeBloodVolume);
        EdtHeightBloodVolume = (EditText) findViewById(R.id.EdtHeightBloodVolume);
        LLHeightBloodVolume = (LinearLayout) findViewById(R.id.LLHeightBloodVolume);
        EdtInchBloodVolume = (EditText) findViewById(R.id.EdtInchBloodVolume);
        RgWeightBloodVolume = (RadioGroup) findViewById(R.id.RgWeightBloodVolume);
        EdtWeightBloodVolume = (EditText) findViewById(R.id.EdtWeightBloodVolume);
        BtnWeightBloodVolume = (Button) findViewById(R.id.BtnWeightBloodVolume);
        BtnResetBloodVolume = (Button) findViewById(R.id.BtnResetBloodVolume);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnWeightBloodVolume.setOnClickListener(this);
        BtnResetBloodVolume.setOnClickListener(this);
        RgWeight.setOnCheckedChangeListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.bloodvol));
        LLHeightBloodVolume.setVisibility(View.GONE);
        EdtAgeBloodVolume.setText(String.valueOf(SharePreference.getCalculatorAge(context)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnWeightBloodVolume:
                GotoCalculateWeight();
                break;
            case R.id.BtnResetBloodVolume:
                GotoCalculateReset();
                break;
        }
    }

    private void GotoCalculateWeight() {
        RadioButton radioGenderButton = (RadioButton) findViewById(RgGender.getCheckedRadioButtonId());
        String gender = (String) radioGenderButton.getText();
        RadioButton radioWeightButton = (RadioButton) findViewById(RgWeight.getCheckedRadioButtonId());
        String rbWeight = (String) radioWeightButton.getText();
        RadioButton radioBloodWeightButton = (RadioButton) findViewById(RgWeightBloodVolume.getCheckedRadioButtonId());
        String weightBlood = (String) radioBloodWeightButton.getText();
        try {
            try {
                DoubleHeight = Double.parseDouble(EdtHeightBloodVolume.getText().toString());
            } catch (NumberFormatException unused) {
                check = true;
            }
            try {
                DoubleWeight = Double.parseDouble(EdtWeightBloodVolume.getText().toString());
            } catch (NumberFormatException unused2) {
                check = true;
            }
            try {
                DoubleInch = Double.parseDouble(EdtInchBloodVolume.getText().toString());
            } catch (NumberFormatException unused3) {
                DoubleInch = 0.0d;
            }
            if (check) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid), Toast.LENGTH_SHORT).show();
                check = false;
                return;
            }
            if (rbWeight.equalsIgnoreCase(getString(R.string.cm))) {
                DoubleHeight /= 100.0d;
            } else {
                DoubleHeight *= 12.0d;
                DoubleHeight += DoubleInch;
                DoubleHeight *= 2.54d;
                DoubleHeight /= 100.0d;
            }
            if (!weightBlood.equalsIgnoreCase(getString(R.string.kilograms))) {
                DoubleWeight *= 0.453592d;
            }
            if (gender.equalsIgnoreCase(getString(R.string.male))) {
                DoubleWeight *= 0.03219d;
                DoubleWeight += 0.6041d;
                DoubleHeight = DoubleHeight * DoubleHeight * DoubleHeight;
                DoubleHeight *= 0.3669d;
            } else {
                DoubleWeight *= 0.03308d;
                DoubleWeight += 0.1833d;
                DoubleHeight = DoubleHeight * DoubleHeight * DoubleHeight;
                DoubleHeight *= 0.3561d;
            }
            DoubleBloodVolume = DoubleWeight + DoubleHeight;
            StrBloodVolume = NumberFormat.getInstance().format(DoubleBloodVolume);

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
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            TextView TvDialogBloodValue = dialog.findViewById(R.id.TvDialogBloodValue);
            Button BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);
            LinearLayout LlBloodVolume = dialog.findViewById(R.id.LlBloodVolume);

            LlBloodVolume.setVisibility(View.VISIBLE);

            TvDialogWeightSubTitle.setText(getString(R.string.urbloodvol));
            TvDialogBloodValue.setText(StrBloodVolume);

            BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());

            IvWeightClose.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
        }
    }

    private void GotoCalculateReset() {
        EdtHeightBloodVolume.setText("");
        EdtInchBloodVolume.setText("");
        EdtWeightBloodVolume.setText("");
        EdtHeightBloodVolume.requestFocus();

        RadioButton radioGenderButton = (RadioButton) RgGender.getChildAt(0);
        radioGenderButton.setChecked(true);
        RadioButton radioWeightButton = (RadioButton) RgWeight.getChildAt(0);
        radioWeightButton.setChecked(true);
        RadioButton radioBloodWeightButton = (RadioButton) RgWeightBloodVolume.getChildAt(0);
        radioBloodWeightButton.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getId()) {
            case R.id.RgWeight:
                RadioButton radioWeightButton = (RadioButton) findViewById(RgWeight.getCheckedRadioButtonId());
                String weight = (String) radioWeightButton.getText();
                if (weight.equalsIgnoreCase("CM")) {
                    EdtHeightBloodVolume.setText("");
                    EdtInchBloodVolume.setText("");
                    LLHeightBloodVolume.setVisibility(View.GONE);
                } else {
                    EdtHeightBloodVolume.setText("");
                    EdtInchBloodVolume.setText("");
                    LLHeightBloodVolume.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}