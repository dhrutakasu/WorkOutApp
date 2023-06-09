
package com.out.workout.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.out.workout.utils.Pref;

import java.text.NumberFormat;

import androidx.appcompat.app.AppCompatActivity;

public class BloodVolumeCalculatorActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private EditText EdtAgeBloodVolume,EdtHeightBloodVolume, EdtInchBloodVolume, EdtWeightBloodVolume;
    private LinearLayout LLHeightBloodVolume;
    private TextView BtnWeightBloodVolume, BtnResetBloodVolume,TvFTOrCMBloodVolume;
    private double DoubleHeight, DoubleWeight, DoubleAge, DoubleInch;
    private boolean check;
    private double DoubleBloodVolume;
    private String StrBloodVolume;
    private Spinner SpinnerGenderBloodVolume,SpinnerHeightBloodVolume,SpinnerWeightBloodVolume;

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
        SpinnerGenderBloodVolume = (Spinner) findViewById(R.id.SpinnerGenderBloodVolume);
        SpinnerHeightBloodVolume = (Spinner) findViewById(R.id.SpinnerHeightBloodVolume);
        SpinnerWeightBloodVolume = (Spinner) findViewById(R.id.SpinnerWeightBloodVolume);
        EdtAgeBloodVolume = (EditText) findViewById(R.id.EdtAgeBloodVolume);
        EdtHeightBloodVolume = (EditText) findViewById(R.id.EdtHeightBloodVolume);
        LLHeightBloodVolume = (LinearLayout) findViewById(R.id.LLHeightBloodVolume);
        EdtInchBloodVolume = (EditText) findViewById(R.id.EdtInchBloodVolume);
        EdtWeightBloodVolume = (EditText) findViewById(R.id.EdtWeightBloodVolume);
        TvFTOrCMBloodVolume = (TextView) findViewById(R.id.TvFTOrCMBloodVolume);
        BtnWeightBloodVolume = (TextView) findViewById(R.id.BtnWeightBloodVolume);
        BtnResetBloodVolume = (TextView) findViewById(R.id.BtnResetBloodVolume);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnWeightBloodVolume.setOnClickListener(this);
        BtnResetBloodVolume.setOnClickListener(this);
        SpinnerWeightBloodVolume.setOnItemSelectedListener(this);
    }

    private void initActions() {

        TvTitle.setText(getString(R.string.str_bloodvol));
        TvFTOrCMBloodVolume.setText(getString(R.string.str_cm));
        LLHeightBloodVolume.setVisibility(View.GONE);
        EdtAgeBloodVolume.setText(String.valueOf(new Pref(context).getInt(Pref.CALCULATOR_AGE,Pref.AGE)));
        String[] GenderArr = {getResources().getString(R.string.str_male), getResources().getString(R.string.str_female)};
        String[] HeightArr = {getResources().getString(R.string.str_centimeters), getResources().getString(R.string.str_feets)};
        String[] WeightArr = {getResources().getString(R.string.str_kilograms), getResources().getString(R.string.str_pounds)};
        SpinnerGenderBloodVolume.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, GenderArr));
        SpinnerHeightBloodVolume.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, HeightArr));
        SpinnerWeightBloodVolume.setAdapter((SpinnerAdapter) new SpinnerAdapters(context, R.layout.item_spinner, WeightArr));
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnWeightBloodVolume:
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Load Ad....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Ad_Interstitial.getInstance().showInter(BloodVolumeCalculatorActivity.this, new Ad_Interstitial.MyCallback() {
                            @Override
                            public void callbackCall() {
                                GotoCalculateWeight();
                            }
                        });
                    }
                }, 3000L);
                break;
            case R.id.BtnResetBloodVolume:
                GotoCalculateReset();
                break;
        }
    }

    private void GotoCalculateWeight() {
        String gender = (String) SpinnerGenderBloodVolume.getSelectedItem().toString();
        String rbWeight = (String) SpinnerHeightBloodVolume.getSelectedItem().toString();
        String weightBlood = (String) SpinnerWeightBloodVolume.getSelectedItem().toString();
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
                check = false;
                return;
            }
            if (rbWeight.equalsIgnoreCase(getString(R.string.str_centimeters))) {
                DoubleHeight /= 100.0d;
            } else {
                DoubleHeight *= 12.0d;
                DoubleHeight += DoubleInch;
                DoubleHeight *= 2.54d;
                DoubleHeight /= 100.0d;
            }
            if (!weightBlood.equalsIgnoreCase(getString(R.string.str_kilograms))) {
                DoubleWeight *= 0.453592d;
            }
            if (gender.equalsIgnoreCase(getString(R.string.str_male))) {
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


            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            TextView TvDialogBloodValue = dialog.findViewById(R.id.TvDialogBloodValue);
            TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);
            LinearLayout LlBloodVolume = dialog.findViewById(R.id.LlBloodVolume);

            ImageView IvDialogBanner = dialog.findViewById(R.id.IvDialogBanner);
            TextView TvDialogName = dialog.findViewById(R.id.TvDialogName);
            TextView TvDialogDesc = dialog.findViewById(R.id.TvDialogDesc);

            IvDialogBanner.setImageResource(R.drawable.ic_blood_volume);
            TvDialogName.setText(getResources().getString(R.string.str_bloodvol));
            TvDialogDesc.setText(getResources().getString(R.string.str_bloodvol_desc));

            LlBloodVolume.setVisibility(View.VISIBLE);

            TvDialogWeightSubTitle.setText(getString(R.string.str_urbloodvol));
            TvDialogBloodValue.setText(StrBloodVolume);

            BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());


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

        SpinnerWeightBloodVolume.setSelection(0);
        SpinnerGenderBloodVolume.setSelection(0);
        SpinnerHeightBloodVolume.setSelection(0);
        TvFTOrCMBloodVolume.setText(getString(R.string.str_cm));
        LLHeightBloodVolume.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.SpinnerWeightBloodVolume:
                String weight = (String) SpinnerWeightBloodVolume.getSelectedItem().toString();
                if (weight.equalsIgnoreCase(getString(R.string.str_centimeters))) {
                    TvFTOrCMBloodVolume.setText(getString(R.string.str_cm));
                    EdtHeightBloodVolume.setText("");
                    EdtInchBloodVolume.setText("");
                    LLHeightBloodVolume.setVisibility(View.GONE);
                } else {
                    TvFTOrCMBloodVolume.setText(getString(R.string.str_ft));
                    EdtHeightBloodVolume.setText("");
                    EdtInchBloodVolume.setText("");
                    LLHeightBloodVolume.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}