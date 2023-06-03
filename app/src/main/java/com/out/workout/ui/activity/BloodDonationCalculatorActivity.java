package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.Ads.Ad_Interstitial;
import com.out.workout.R;
import com.out.workout.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BloodDonationCalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private TextView EdtDateBloodDonate;
    private TextView BtnWeightBloodDonate, BtnBloodDonate, BtnEligibilityBloodDonate;
    private boolean BoolChooseDate=false;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private int Fab,dateLong,date;
    private ImageView IvCalendarBloodDonate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donation_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtDateBloodDonate = (TextView) findViewById(R.id.EdtDateBloodDonate);
        IvCalendarBloodDonate = (ImageView) findViewById(R.id.IvCalendarBloodDonate);
        BtnWeightBloodDonate = (TextView) findViewById(R.id.BtnWeightBloodDonate);
        BtnBloodDonate = (TextView) findViewById(R.id.BtnBloodDonate);
        BtnEligibilityBloodDonate = (TextView) findViewById(R.id.BtnEligibilityBloodDonate);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        EdtDateBloodDonate.setOnClickListener(this);
        IvCalendarBloodDonate.setOnClickListener(this);
        BtnWeightBloodDonate.setOnClickListener(this);
        BtnBloodDonate.setOnClickListener(this);
        BtnEligibilityBloodDonate.setOnClickListener(this);
    }

    private void initActions() {
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

        TvTitle.setText(getString(R.string.str_blood_donate));
        calendar = Calendar.getInstance();
        Fab = calendar.get(Calendar.YEAR);
        dateLong = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DAY_OF_MONTH);
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String[] split = simpleDateFormat.format(calendar.getTime()).split("/");
        switch (Integer.parseInt(split[0])) {
            case 1:
                EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_jan) + " " + split[2]);
                break;
            case 2:
                EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_feb) + " " + split[2]);
                break;
            case 3:
                EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_mar) + " " + split[2]);
                break;
            case 4:
                EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_apr) + " " + split[2]);
                break;
            case 5:
                EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_may) + " " + split[2]);
                break;
            case 6:
                EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_jun) + " " + split[2]);
                break;
            case 7:
                EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_jul) + " " + split[2]);
                break;
            case 8:
                EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_aug) + " " + split[2]);
                break;
            case 9:
                EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_sep) + " " + split[2]);
                break;
            case 10:
                EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_oct) + " " + split[2]);
                break;
            case 11:
                EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_nov) + " " + split[2]);
                break;
            case 12:
                EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_dec) + " " + split[2]);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.EdtDateBloodDonate:
            case R.id.IvCalendarBloodDonate:
                GotoChooseDate();
                break;
            case R.id.BtnWeightBloodDonate:
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Load Ad....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Ad_Interstitial.getInstance().showInter(BloodDonationCalculatorActivity.this, new Ad_Interstitial.MyCallback() {
                            @Override
                            public void callbackCall() {
                                GotoCalculateBloodDonation();
                            }
                        });
                    }
                }, 3000L);
                break;
            case R.id.BtnBloodDonate:
                GotoBloodDonate();
                break;
            case R.id.BtnEligibilityBloodDonate:
                GotoEligibilityBlood();
                break;
        }
    }

    private void GotoChooseDate() {
        BoolChooseDate=true;
        calendar.set(Fab, dateLong, date);
        new DatePickerDialog(context, onDateSetListener,  calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void GotoCalculateBloodDonation() {
        String format;
        if (!BoolChooseDate) {
            calendar.set(Fab, dateLong, date);
            calendar.add(5, 56);
            format = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(calendar.getTime());
        }else {
            format = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(calendar.getTime());
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
        TextView TvDialogBloodDonate = dialog.findViewById(R.id.TvDialogBloodDonate);
        TextView TvDialogBloodDonateLong = dialog.findViewById(R.id.TvDialogBloodDonateLong);
        TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);
        LinearLayout LlBloodDonate = dialog.findViewById(R.id.LlBloodDonate);

        ImageView IvDialogBanner = dialog.findViewById(R.id.IvDialogBanner);
        TextView TvDialogName = dialog.findViewById(R.id.TvDialogName);
        TextView TvDialogDesc = dialog.findViewById(R.id.TvDialogDesc);

        IvDialogBanner.setImageResource(R.drawable.ic_blood_donate);
        TvDialogName.setText(getResources().getString(R.string.str_blood_donate));
        TvDialogDesc.setText(getResources().getString(R.string.str_blood_don_desc));

        LlBloodDonate.setVisibility(View.VISIBLE);

        TvDialogWeightSubTitle.setText(getString(R.string.str_urblddate));
        TvDialogBloodDonate.setText(format);

        String[] split = format.split("/");
        switch (Integer.parseInt(split[0])) {
            case 1:
                TvDialogBloodDonateLong.setText(split[1] + " " + getResources().getString(R.string.str_jan) + " " + split[2]);
                break;
            case 2:
                TvDialogBloodDonateLong.setText(split[1] + " " + getResources().getString(R.string.str_feb) + " " + split[2]);
                break;
            case 3:
                TvDialogBloodDonateLong.setText(split[1] + " " + getResources().getString(R.string.str_mar) + " " + split[2]);
                break;
            case 4:
                TvDialogBloodDonateLong.setText(split[1] + " " + getResources().getString(R.string.str_apr) + " " + split[2]);
                break;
            case 5:
                TvDialogBloodDonateLong.setText(split[1] + " " + getResources().getString(R.string.str_may) + " " + split[2]);
                break;
            case 6:
                TvDialogBloodDonateLong.setText(split[1] + " " + getResources().getString(R.string.str_jun) + " " + split[2]);
                break;
            case 7:
                TvDialogBloodDonateLong.setText(split[1] + " " + getResources().getString(R.string.str_jul) + " " + split[2]);
                break;
            case 8:
                TvDialogBloodDonateLong.setText(split[1] + " " + getResources().getString(R.string.str_aug) + " " + split[2]);
                break;
            case 9:
                TvDialogBloodDonateLong.setText(split[1] + " " + getResources().getString(R.string.str_sep) + " " + split[2]);
                break;
            case 10:
                TvDialogBloodDonateLong.setText(split[1] + " " + getResources().getString(R.string.str_oct) + " " + split[2]);
                break;
            case 11:
                TvDialogBloodDonateLong.setText(split[1] + " " + getResources().getString(R.string.str_nov) + " " + split[2]);
                break;
            case 12:
                TvDialogBloodDonateLong.setText(split[1] + " " + getResources().getString(R.string.str_dec) + " " + split[2]);
                break;
        }

        BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());


        dialog.show();
    }

    private void GotoBloodDonate() {
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
        TextView TvDialogBloodDonateDesc = dialog.findViewById(R.id.TvDialogBloodDonateDesc);
        TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);
        TextView BtnDialogBloodDonateChart = dialog.findViewById(R.id.BtnDialogBloodDonateChart);
        LinearLayout LlBloodDonateDesc = dialog.findViewById(R.id.LlBloodDonateDesc);

        ImageView IvDialogBanner = dialog.findViewById(R.id.IvDialogBanner);
        TextView TvDialogName = dialog.findViewById(R.id.TvDialogName);
        TextView TvDialogDesc = dialog.findViewById(R.id.TvDialogDesc);

        IvDialogBanner.setImageResource(R.drawable.ic_blood_donate);
        TvDialogName.setText(getResources().getString(R.string.str_blood_donate));
        TvDialogDesc.setText(getResources().getString(R.string.str_blood_don_desc));

        SpannableString content = new SpannableString(getString(R.string.str_chart_giving_blood));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        BtnDialogBloodDonateChart.setText(content);
        LlBloodDonateDesc.setVisibility(View.VISIBLE);
        TvDialogWeightSubTitle.setVisibility(View.GONE);
        BtnDialogWeight.setVisibility(View.GONE);

        TvDialogWeightSubTitle.setText(getString(R.string.str_urblddate));
        TvDialogBloodDonateDesc.setText(getString(R.string.str_canigiveblood_desc));
        TvDialogBloodDonateDesc.setMovementMethod(new ScrollingMovementMethod());

        BtnDialogBloodDonateChart.setOnClickListener(view -> {
            startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.str_canidonate)));
        });


        dialog.show();
    }

    private void GotoEligibilityBlood() {
        startActivity(new Intent(context, EligibilityBloodActivity.class).putExtra(Constants.ChartType, getString(R.string.str_canidonate)));
    }

    final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i4, int i5, int i6) {
            calendar.set(Calendar.YEAR, i4);
            calendar.set(Calendar.MONTH, i5);
            calendar.set(Calendar.DAY_OF_MONTH, i6);
            String[] split = simpleDateFormat.format(calendar.getTime()).split("/");
            switch (Integer.parseInt(split[0])) {
                case 1:
                    EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_jan) + " " + split[2]);
                    break;
                case 2:
                    EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_feb) + " " + split[2]);
                    break;
                case 3:
                    EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_mar) + " " + split[2]);
                    break;
                case 4:
                    EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_apr) + " " + split[2]);
                    break;
                case 5:
                    EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_may) + " " + split[2]);
                    break;
                case 6:
                    EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_jun) + " " + split[2]);
                    break;
                case 7:
                    EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_jul) + " " + split[2]);
                    break;
                case 8:
                    EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_aug) + " " + split[2]);
                    break;
                case 9:
                    EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_sep) + " " + split[2]);
                    break;
                case 10:
                    EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_oct) + " " + split[2]);
                    break;
                case 11:
                    EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_nov) + " " + split[2]);
                    break;
                case 12:
                    EdtDateBloodDonate.setText(split[1] + " " + getResources().getString(R.string.str_dec) + " " + split[2]);
                    break;
            }
            calendar.add(5, 56);
        }
    };
}