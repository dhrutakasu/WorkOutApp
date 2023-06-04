package com.out.workout.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import androidx.cardview.widget.CardView;

public class BmiCalculatorActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtAgeBMI;
    private EditText EdtHeightBMI, EdtInchBMI, EdtWeightBMI;
    private LinearLayout LLHeightBMI;
    private TextView BtnWeightBMI;
    private TextView BtnResetBMI;
    private ImageView BtnChartBMI;
    private double DoubleHeight, DoubleWeight, DoubleAge, DoubleInch;
    private boolean check;
    private double calculate;
    private String calculate_Kg, calculate_BMI;
    private int calculate_BMI_Int;
    private Spinner SpinnerGenderBMI,SpinnerHeightBMI,SpinnerWeightBMI;
    private TextView TvFTOrCMBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator_activtiy);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtAgeBMI = (EditText) findViewById(R.id.EdtAgeBMI);
        EdtHeightBMI = (EditText) findViewById(R.id.EdtHeightBMI);
        LLHeightBMI = (LinearLayout) findViewById(R.id.LLHeightBMI);
        EdtInchBMI = (EditText) findViewById(R.id.EdtInchBMI);
        EdtWeightBMI = (EditText) findViewById(R.id.EdtWeightBMI);
        SpinnerGenderBMI=(Spinner) findViewById(R.id.SpinnerGenderBMI);
        SpinnerHeightBMI=(Spinner) findViewById(R.id.SpinnerHeightBMI);
        SpinnerWeightBMI=(Spinner) findViewById(R.id.SpinnerWeightBMI);
        TvFTOrCMBMI= (TextView) findViewById(R.id.TvFTOrCMBMI);
        BtnWeightBMI = (TextView) findViewById(R.id.BtnWeightBMI);
        BtnResetBMI = (TextView) findViewById(R.id.BtnResetBMI);
        BtnChartBMI = (ImageView) findViewById(R.id.BtnChartBMI);
    }

    private void initListeners() {
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        IvBack.setOnClickListener(this);
        BtnWeightBMI.setOnClickListener(this);
        BtnResetBMI.setOnClickListener(this);
        BtnChartBMI.setOnClickListener(this);
        SpinnerHeightBMI.setOnItemSelectedListener(this);
    }

    private void initActions() {
        TvTitle.setText(getString(R.string.str_bmi_title));
        TvFTOrCMBMI.setText(R.string.str_cm);
        LLHeightBMI.setVisibility(View.GONE);
        EdtAgeBMI.setText(String.valueOf(new Pref(context).getInt(Pref.CALCULATOR_AGE,Pref.AGE)));
        String[] GenderArr = {getResources().getString(R.string.str_male), getResources().getString(R.string.str_female)};
        String[] HeightArr = {getResources().getString(R.string.str_centimeters), getResources().getString(R.string.str_feets)};
        String[] WeightArr = {getResources().getString(R.string.str_kilograms), getResources().getString(R.string.str_pounds)};
        SpinnerGenderBMI.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, GenderArr));
        SpinnerHeightBMI.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, HeightArr));
        SpinnerWeightBMI.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, WeightArr));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnWeightBMI:
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Load Ad....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Ad_Interstitial.getInstance().showInter(BmiCalculatorActivity.this, new Ad_Interstitial.MyCallback() {
                            @Override
                            public void callbackCall() {
                                GotoCalculateBMI();
                            }
                        });
                    }
                }, 3000L);
                break;
            case R.id.BtnResetBMI:
                GotoBMIReset();
                break;
            case R.id.BtnChartBMI:
                GotoBMIChart();
                break;
        }
    }

    private void GotoCalculateBMI() {
        String gender = (String) SpinnerGenderBMI.getSelectedItem().toString();
        String weight = (String) SpinnerHeightBMI.getSelectedItem().toString();
        String weightBMI = (String) SpinnerWeightBMI.getSelectedItem().toString();
        try {
            try {
                DoubleHeight = Double.parseDouble(EdtHeightBMI.getText().toString());
            } catch (NumberFormatException unused) {
                check = true;
            }
            try {
                DoubleWeight = Double.parseDouble(EdtWeightBMI.getText().toString());
            } catch (NumberFormatException unused2) {
                check = true;
            }
            try {
                DoubleAge = Double.parseDouble(EdtAgeBMI.getText().toString());
            } catch (NumberFormatException unused3) {
                check = true;
            }
            try {
                DoubleInch = Double.parseDouble(EdtInchBMI.getText().toString());
            } catch (NumberFormatException unused4) {
                DoubleInch = 0.0d;
            }
            if (check) {
                check = false;
                return;
            }
            if (weight.equalsIgnoreCase(getString(R.string.str_centimeters))) {
                DoubleHeight /= 100.0d;
            } else {
                DoubleHeight *= 12.0d;
                DoubleHeight += DoubleInch;
                DoubleHeight *= 0.0254d;
            }
            if (!weightBMI.equalsIgnoreCase(getString(R.string.str_kilograms))) {
                DoubleWeight *= 0.453592d;
            }
            try {
                new Pref(context).putInt(Pref.CALCULATOR_AGE,Integer.parseInt(EdtAgeBMI.getText().toString()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            calculate = DoubleWeight / (DoubleHeight * DoubleHeight);
            calculate_BMI = NumberFormat.getInstance().format(calculate);
            System.out.println("-------- BMI : " + calculate + " - - - " + calculate_BMI);
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

            ImageView IvWeightWomen = dialog.findViewById(R.id.IvWeightWomen);
            TextView TvDialogWeightValue = dialog.findViewById(R.id.TvDialogWeightValue);
            TextView TvDialogWeightBMIValue = dialog.findViewById(R.id.TvDialogWeightBMIValue);
            TextView BtnDialogResult = dialog.findViewById(R.id.BtnDialogResult);
            ProgressBar PbBMI = dialog.findViewById(R.id.PbBMI);
            CardView CardIdealWeight = dialog.findViewById(R.id.CardIdealWeight);

            ImageView IvDialogBanner = dialog.findViewById(R.id.IvDialogBanner);
            TextView TvDialogName = dialog.findViewById(R.id.TvDialogName);
            TextView TvDialogDesc = dialog.findViewById(R.id.TvDialogDesc);
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);

            IvDialogBanner.setImageResource(R.drawable.ic_body_mass_index);
            TvDialogName.setText(getResources().getString(R.string.str_bmi_title));
            TvDialogDesc.setText(getResources().getString(R.string.str_bmi_desc));

            CardIdealWeight.setVisibility(View.VISIBLE);
            TvDialogWeightValue.setVisibility(View.VISIBLE);
            PbBMI.setVisibility(View.VISIBLE);
            IvWeightWomen.setVisibility(View.VISIBLE);
            TvDialogWeightBMIValue.setVisibility(View.VISIBLE);
            BtnDialogResult.setVisibility(View.VISIBLE);

            TvDialogWeightSubTitle.setText(getString(R.string.str_bmiis));

            SpannableString content = new SpannableString(getString(R.string.str_understand_bmi));
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            BtnDialogResult.setText(content);

            TvDialogWeightValue.setTextColor(getResources().getColor(R.color.black));
            TvDialogWeightBMIValue.setText(calculate_BMI);
            try {
                int BMI_Int = (int) Double.valueOf(calculate_BMI).doubleValue();
                calculate_BMI_Int = BMI_Int;
                if (BMI_Int < 16) {
                    calculate_BMI_Int = 1;
                    TvDialogWeightValue.setText(getResources().getString(R.string.str_sixteenmin) + " " + getResources().getString(R.string.str_exunder));
                } else if (BMI_Int > 40) {
                    calculate_BMI_Int = 100;
                    TvDialogWeightValue.setText(getResources().getString(R.string.str_sixteenmin) + " " + getResources().getString(R.string.str_morbid));
                } else {
                    if (calculate >= 16.0d && calculate <= 18.5d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.str_sixteenmin) + " " + getResources().getString(R.string.str_underweight));
                    } else if (calculate > 18.5d && calculate <= 25.0d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.str_sixteenmin) + " " + getResources().getString(R.string.str_normalweight));
                    } else if (calculate > 25.0d && calculate <= 30.0d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.str_sixteenmin) + " " + getResources().getString(R.string.str_overweight));
                    } else if (calculate > 30.0d && calculate <= 35.0d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.str_sixteenmin) + " " + getResources().getString(R.string.str_obeseone));
                    } else if (calculate > 35.0d && calculate <= 40.0d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.str_sixteenmin) + " " + getResources().getString(R.string.str_obesetwo));
                    } else if (calculate < 16.0d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.str_sixteenmin) + " " + getResources().getString(R.string.str_exunder));
                    } else if (calculate > 40.0d) {
                        TvDialogWeightValue.setText(getResources().getString(R.string.str_sixteenmin) + " " + getResources().getString(R.string.str_morbid));
                    }
                    calculate_BMI_Int = (calculate_BMI_Int - 15) * 4;
                }
            } catch (NumberFormatException e) {
                System.out.println("Could not parse " + e);
                calculate_BMI_Int = 100;
                TvDialogWeightValue.setText(getResources().getString(R.string.str_sixteenmin) + " " + getResources().getString(R.string.str_morbid));
            }
            PbBMI.setProgress(calculate_BMI_Int);
            BtnDialogWeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            BtnDialogResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, BMIUnderstandResultActivity.class));
                }
            });


            dialog.show();
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
        }
    }

    private void GotoBMIReset() {
        EdtHeightBMI.setText("");
        EdtAgeBMI.setText(String.valueOf(new Pref(context).getInt(Pref.CALCULATOR_AGE,Pref.AGE)));
        EdtInchBMI.setText("");
        EdtWeightBMI.setText("");
        EdtAgeBMI.requestFocus();

        TvFTOrCMBMI.setText(getString(R.string.str_cm));
        SpinnerGenderBMI.setSelection(0);
        SpinnerHeightBMI.setSelection(0);
        SpinnerWeightBMI.setSelection(0);
        LLHeightBMI.setVisibility(View.GONE);
    }

    private void GotoBMIChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.str_bmi_title)));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.SpinnerHeightBMI:
                String weight = (String) SpinnerHeightBMI.getSelectedItem().toString();
                if (weight.equalsIgnoreCase(getString(R.string.str_centimeters))) {
                    EdtHeightBMI.setText("");
                    EdtInchBMI.setText("");
                    TvFTOrCMBMI.setText(getString(R.string.str_cm));
                    LLHeightBMI.setVisibility(View.GONE);
                } else {
                    TvFTOrCMBMI.setText(getString(R.string.str_ft));
                    EdtHeightBMI.setText("");
                    EdtInchBMI.setText("");
                    LLHeightBMI.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}