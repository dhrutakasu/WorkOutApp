package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.out.workout.Ads.Ad_Native;
import com.out.workout.R;
import com.out.workout.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OvulationCalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private TextView EdtDateOvulation;
    private TextView BtnWeightOvulation;
    private TextView BtnOvulation;
    private boolean BoolChooseDate = false;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private int Fab, dateLong, date;
    private ImageView IvCalendarOvulation;
    private String calculate_Ovulation, calculateOvulation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ovulation_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtDateOvulation = (TextView) findViewById(R.id.EdtDateOvulation);
        IvCalendarOvulation = (ImageView) findViewById(R.id.IvCalendarOvulation);
        BtnWeightOvulation = (TextView) findViewById(R.id.BtnWeightOvulation);
        BtnOvulation = (TextView) findViewById(R.id.BtnOvulation);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        EdtDateOvulation.setOnClickListener(this);
        IvCalendarOvulation.setOnClickListener(this);
        BtnWeightOvulation.setOnClickListener(this);
        BtnOvulation.setOnClickListener(this);
    }

    private void initActions() {
        Ad_Native.getInstance().showNative250(this, findViewById(R.id.FlNative));
        TvTitle.setText(getString(R.string.ovulation));
        calendar = Calendar.getInstance();
        Fab = calendar.get(Calendar.YEAR);
        dateLong = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DAY_OF_MONTH);
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String[] split = simpleDateFormat.format(calendar.getTime()).split("/");
        switch (Integer.parseInt(split[0])) {
            case 1:
                EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.jan) + " " + split[2]);
                break;
            case 2:
                EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.feb) + " " + split[2]);
                break;
            case 3:
                EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.mar) + " " + split[2]);
                break;
            case 4:
                EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.apr) + " " + split[2]);
                break;
            case 5:
                EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.may) + " " + split[2]);
                break;
            case 6:
                EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.jun) + " " + split[2]);
                break;
            case 7:
                EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.jul) + " " + split[2]);
                break;
            case 8:
                EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.aug) + " " + split[2]);
                break;
            case 9:
                EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.sep) + " " + split[2]);
                break;
            case 10:
                EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.oct) + " " + split[2]);
                break;
            case 11:
                EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.nov) + " " + split[2]);
                break;
            case 12:
                EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.dec) + " " + split[2]);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.EdtDateOvulation:
            case R.id.IvCalendarOvulation:
                GotoChooseDate();
                break;
            case R.id.BtnWeightOvulation:
                GotoCalculateBloodDonation();
                break;
            case R.id.BtnOvulation:
                GotoOvulationChart();
                break;
        }
    }

    private void GotoChooseDate() {
        BoolChooseDate = true;
        calendar.set(Fab, dateLong, date);
        new DatePickerDialog(context, setListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void GotoCalculateBloodDonation() {
        if (!BoolChooseDate) {
            calendar.set(Fab, dateLong, date);
            calendar.add(5, 12);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            calculate_Ovulation = dateFormat.format(calendar.getTime());
            calendar.add(5, 4);
            calculateOvulation = dateFormat.format(calendar.getTime());
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
        TextView TvDialogOvulation = dialog.findViewById(R.id.TvDialogOvulation);
        TextView TvDialogOvulationLong = dialog.findViewById(R.id.TvDialogOvulationLong);
        TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);
        LinearLayout LlOvulation = dialog.findViewById(R.id.LlOvulationDesc);


        ImageView IvDialogBanner = dialog.findViewById(R.id.IvDialogBanner);
        TextView TvDialogName = dialog.findViewById(R.id.TvDialogName);
        TextView TvDialogDesc = dialog.findViewById(R.id.TvDialogDesc);

        IvDialogBanner.setImageResource(R.drawable.ic_ovulation);
        TvDialogName.setText(getResources().getString(R.string.ovulation));
        TvDialogDesc.setText(getResources().getString(R.string.ovulation_desc));

        LlOvulation.setVisibility(View.VISIBLE);
        TvDialogWeightSubTitle.setText(getString(R.string.urfer));

        String[] ArrDate, ArrDateOvulation;
        int Covulation, Covulation2;
        if (TextUtils.isEmpty("/")) {
            ArrDate = null;
            ArrDateOvulation = null;
            Covulation = 0;
            Covulation2 = 0;
        } else {
            ArrDateOvulation = calculate_Ovulation.split("/");
            ArrDate = calculateOvulation.split("/");
            Covulation2 = Integer.parseInt(ArrDateOvulation[0]);
            Covulation = Integer.parseInt(ArrDate[0]);
        }

        switch (Covulation2) {
            case 1:
                TvDialogOvulation.setText(ArrDateOvulation[1] + " " + getResources().getString(R.string.jan) + " " + ArrDateOvulation[2]);
                break;
            case 2:
                TvDialogOvulation.setText(ArrDateOvulation[1] + " " + getResources().getString(R.string.feb) + " " + ArrDateOvulation[2]);
                break;
            case 3:
                TvDialogOvulation.setText(ArrDateOvulation[1] + " " + getResources().getString(R.string.mar) + " " + ArrDateOvulation[2]);
                break;
            case 4:
                TvDialogOvulation.setText(ArrDateOvulation[1] + " " + getResources().getString(R.string.apr) + " " + ArrDateOvulation[2]);
                break;
            case 5:
                TvDialogOvulation.setText(ArrDateOvulation[1] + " " + getResources().getString(R.string.may) + " " + ArrDateOvulation[2]);
                break;
            case 6:
                TvDialogOvulation.setText(ArrDateOvulation[1] + " " + getResources().getString(R.string.jun) + " " + ArrDateOvulation[2]);
                break;
            case 7:
                TvDialogOvulation.setText(ArrDateOvulation[1] + " " + getResources().getString(R.string.jul) + " " + ArrDateOvulation[2]);
                break;
            case 8:
                TvDialogOvulation.setText(ArrDateOvulation[1] + " " + getResources().getString(R.string.aug) + " " + ArrDateOvulation[2]);
                break;
            case 9:
                TvDialogOvulation.setText(ArrDateOvulation[1] + " " + getResources().getString(R.string.sep) + " " + ArrDateOvulation[2]);
                break;
            case 10:
                TvDialogOvulation.setText(ArrDateOvulation[1] + " " + getResources().getString(R.string.oct) + " " + ArrDateOvulation[2]);
                break;
            case 11:
                TvDialogOvulation.setText(ArrDateOvulation[1] + " " + getResources().getString(R.string.nov) + " " + ArrDateOvulation[2]);
                break;
            case 12:
                TvDialogOvulation.setText(ArrDateOvulation[1] + " " + getResources().getString(R.string.dec) + " " + ArrDateOvulation[2]);
                break;
        }
        switch (Covulation) {
            case 1:
                TvDialogOvulationLong.setText(ArrDate[1] + " " + getResources().getString(R.string.jan) + " " + ArrDate[2]);
                break;
            case 2:
                TvDialogOvulationLong.setText(ArrDate[1] + " " + getResources().getString(R.string.feb) + " " + ArrDate[2]);
                break;
            case 3:
                TvDialogOvulationLong.setText(ArrDate[1] + " " + getResources().getString(R.string.mar) + " " + ArrDate[2]);
                break;
            case 4:
                TvDialogOvulationLong.setText(ArrDate[1] + " " + getResources().getString(R.string.apr) + " " + ArrDate[2]);
                break;
            case 5:
                TvDialogOvulationLong.setText(ArrDate[1] + " " + getResources().getString(R.string.may) + " " + ArrDate[2]);
                break;
            case 6:
                TvDialogOvulationLong.setText(ArrDate[1] + " " + getResources().getString(R.string.jun) + " " + ArrDate[2]);
                break;
            case 7:
                TvDialogOvulationLong.setText(ArrDate[1] + " " + getResources().getString(R.string.jul) + " " + ArrDate[2]);
                break;
            case 8:
                TvDialogOvulationLong.setText(ArrDate[1] + " " + getResources().getString(R.string.aug) + " " + ArrDate[2]);
                break;
            case 9:
                TvDialogOvulationLong.setText(ArrDate[1] + " " + getResources().getString(R.string.sep) + " " + ArrDate[2]);
                break;
            case 10:
                TvDialogOvulationLong.setText(ArrDate[1] + " " + getResources().getString(R.string.oct) + " " + ArrDate[2]);
                break;
            case 11:
                TvDialogOvulationLong.setText(ArrDate[1] + " " + getResources().getString(R.string.nov) + " " + ArrDate[2]);
                break;
            case 12:
                TvDialogOvulationLong.setText(ArrDate[1] + " " + getResources().getString(R.string.dec) + " " + ArrDate[2]);
                break;
        }

        BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());



        dialog.show();
    }

    private void GotoOvulationChart() {
        startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ChartType, getString(R.string.ovulation)));
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
                    EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.jan) + " " + split[2]);
                    break;
                case 2:
                    EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.feb) + " " + split[2]);
                    break;
                case 3:
                    EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.mar) + " " + split[2]);
                    break;
                case 4:
                    EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.apr) + " " + split[2]);
                    break;
                case 5:
                    EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.may) + " " + split[2]);
                    break;
                case 6:
                    EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.jun) + " " + split[2]);
                    break;
                case 7:
                    EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.jul) + " " + split[2]);
                    break;
                case 8:
                    EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.aug) + " " + split[2]);
                    break;
                case 9:
                    EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.sep) + " " + split[2]);
                    break;
                case 10:
                    EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.oct) + " " + split[2]);
                    break;
                case 11:
                    EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.nov) + " " + split[2]);
                    break;
                case 12:
                    EdtDateOvulation.setText(split[1] + " " + getResources().getString(R.string.dec) + " " + split[2]);
                    break;
            }
            calendar.add(5, 12);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            calculate_Ovulation = dateFormat.format(calendar.getTime());
            calendar.add(5, 4);
            calculateOvulation = dateFormat.format(calendar.getTime());
        }
    };
}