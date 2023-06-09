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
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.out.workout.Ads.Ad_Interstitial;
import com.out.workout.Ads.Ad_Native;
import com.out.workout.R;
import com.out.workout.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PregnancyCalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private TextView EdtDatePregnancy;
    private TextView BtnWeightPregnancy;
    private TextView BtnPregnancy;
    private boolean BoolChooseDate = false;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private int Fab, dateLong, date;
    private ImageView IvCalendarPregnancy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtDatePregnancy = (TextView) findViewById(R.id.EdtDatePregnancy);
        IvCalendarPregnancy = (ImageView) findViewById(R.id.IvCalendarPregnancy);
        BtnWeightPregnancy = (TextView) findViewById(R.id.BtnWeightPregnancy);
        BtnPregnancy = (TextView) findViewById(R.id.BtnPregnancy);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        EdtDatePregnancy.setOnClickListener(this);
        IvCalendarPregnancy.setOnClickListener(this);
        BtnWeightPregnancy.setOnClickListener(this);
        BtnPregnancy.setOnClickListener(this);
    }

    private void initActions() {
        Ad_Native.getInstance().showNative250(this, findViewById(R.id.FlNative));
        TvTitle.setText(getString(R.string.str_pregnancy));
        calendar = Calendar.getInstance();
        Fab = calendar.get(Calendar.YEAR);
        dateLong = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DAY_OF_MONTH);
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String[] split = simpleDateFormat.format(calendar.getTime()).split("/");
        switch (Integer.parseInt(split[0])) {
            case 1:
                EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_jan) + " " + split[2]);
                break;
            case 2:
                EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_feb) + " " + split[2]);
                break;
            case 3:
                EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_mar) + " " + split[2]);
                break;
            case 4:
                EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_apr) + " " + split[2]);
                break;
            case 5:
                EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_may) + " " + split[2]);
                break;
            case 6:
                EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_jun) + " " + split[2]);
                break;
            case 7:
                EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_jul) + " " + split[2]);
                break;
            case 8:
                EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_aug) + " " + split[2]);
                break;
            case 9:
                EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_sep) + " " + split[2]);
                break;
            case 10:
                EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_oct) + " " + split[2]);
                break;
            case 11:
                EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_nov) + " " + split[2]);
                break;
            case 12:
                EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_dec) + " " + split[2]);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.EdtDatePregnancy:
            case R.id.IvCalendarPregnancy:
                GotoChooseDate();
                break;
            case R.id.BtnWeightPregnancy:
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Load Ad....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Ad_Interstitial.getInstance().showInter(PregnancyCalculatorActivity.this, new Ad_Interstitial.MyCallback() {
                            @Override
                            public void callbackCall() {
                                GotoCalculateBloodDonation();
                            }
                        });
                    }
                }, 3000L);
                break;
            case R.id.BtnPregnancy:
                GotoPregnancyChart();
                break;
        }
    }

    private void GotoChooseDate() {
        BoolChooseDate = true;
        calendar.set(Fab, dateLong, date);
        new DatePickerDialog(context, setListener,  calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void GotoCalculateBloodDonation() {
        String format;
        if (!BoolChooseDate) {
            calendar.set(Fab, dateLong, date);
            calendar.add(5, 282);
            format = new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(calendar.getTime());
        } else {
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
        TextView TvDialogPregnancy = dialog.findViewById(R.id.TvDialogPregnancy);
        TextView TvDialogPregnancyLong = dialog.findViewById(R.id.TvDialogPregnancyLong);
        TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);
        LinearLayout LlPregnancy = dialog.findViewById(R.id.LlPregnancyDesc);


        ImageView IvDialogBanner = dialog.findViewById(R.id.IvDialogBanner);
        TextView TvDialogName = dialog.findViewById(R.id.TvDialogName);
        TextView TvDialogDesc = dialog.findViewById(R.id.TvDialogDesc);

        IvDialogBanner.setImageResource(R.drawable.ic_pregncy_due_date);
        TvDialogName.setText(getResources().getString(R.string.str_pregnancy));
        TvDialogDesc.setText(getResources().getString(R.string.str_pregnancy_desc));

        LlPregnancy.setVisibility(View.VISIBLE);

        TvDialogWeightSubTitle.setText(getString(R.string.str_uredd));
        TvDialogPregnancy.setText(format);

        String[] split = format.split("/");
        switch (Integer.parseInt(split[0])) {
            case 1:
                TvDialogPregnancyLong.setText(split[1] + " " + getResources().getString(R.string.str_jan) + " " + split[2]);
                break;
            case 2:
                TvDialogPregnancyLong.setText(split[1] + " " + getResources().getString(R.string.str_feb) + " " + split[2]);
                break;
            case 3:
                TvDialogPregnancyLong.setText(split[1] + " " + getResources().getString(R.string.str_mar) + " " + split[2]);
                break;
            case 4:
                TvDialogPregnancyLong.setText(split[1] + " " + getResources().getString(R.string.str_apr) + " " + split[2]);
                break;
            case 5:
                TvDialogPregnancyLong.setText(split[1] + " " + getResources().getString(R.string.str_may) + " " + split[2]);
                break;
            case 6:
                TvDialogPregnancyLong.setText(split[1] + " " + getResources().getString(R.string.str_jun) + " " + split[2]);
                break;
            case 7:
                TvDialogPregnancyLong.setText(split[1] + " " + getResources().getString(R.string.str_jul) + " " + split[2]);
                break;
            case 8:
                TvDialogPregnancyLong.setText(split[1] + " " + getResources().getString(R.string.str_aug) + " " + split[2]);
                break;
            case 9:
                TvDialogPregnancyLong.setText(split[1] + " " + getResources().getString(R.string.str_sep) + " " + split[2]);
                break;
            case 10:
                TvDialogPregnancyLong.setText(split[1] + " " + getResources().getString(R.string.str_oct) + " " + split[2]);
                break;
            case 11:
                TvDialogPregnancyLong.setText(split[1] + " " + getResources().getString(R.string.str_nov) + " " + split[2]);
                break;
            case 12:
                TvDialogPregnancyLong.setText(split[1] + " " + getResources().getString(R.string.str_dec) + " " + split[2]);
                break;
        }

        BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());


        dialog.show();
    }

    private void GotoPregnancyChart() {
        startActivity(new Intent(context,ChartActivity.class).putExtra(Constants.ChartType,getString(R.string.str_preg_chart)));
    }

    final DatePickerDialog.OnDateSetListener setListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i4, int i5, int i6) {
            calendar.set(Calendar.YEAR, i4);
            calendar.set(Calendar.MONTH, i5);
            calendar.set(Calendar.DAY_OF_MONTH, i6);
            String[] split = simpleDateFormat.format(calendar.getTime()).split("/");
            switch (Integer.parseInt(split[0])) {
                case 1:
                    EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_jan) + " " + split[2]);
                    break;
                case 2:
                    EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_feb) + " " + split[2]);
                    break;
                case 3:
                    EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_mar) + " " + split[2]);
                    break;
                case 4:
                    EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_apr) + " " + split[2]);
                    break;
                case 5:
                    EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_may) + " " + split[2]);
                    break;
                case 6:
                    EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_jun) + " " + split[2]);
                    break;
                case 7:
                    EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_jul) + " " + split[2]);
                    break;
                case 8:
                    EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_aug) + " " + split[2]);
                    break;
                case 9:
                    EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_sep) + " " + split[2]);
                    break;
                case 10:
                    EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_oct) + " " + split[2]);
                    break;
                case 11:
                    EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_nov) + " " + split[2]);
                    break;
                case 12:
                    EdtDatePregnancy.setText(split[1] + " " + getResources().getString(R.string.str_dec) + " " + split[2]);
                    break;
            }
            calendar.add(5, 282);
        }
    };

}