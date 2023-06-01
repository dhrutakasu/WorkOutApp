package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.out.workout.Ads.Ad_Native;
import com.out.workout.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BreathCalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private TextView EdtDateBreathCount;
    private TextView BtnWeightBreathCount;
    private boolean BoolChooseDate = false;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private int Fab, dateLong, date;
    private ImageView IvCalendarBreathCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        EdtDateBreathCount = (TextView) findViewById(R.id.EdtDateBreathCount);
        IvCalendarBreathCount = (ImageView) findViewById(R.id.IvCalendarBreathCount);
        BtnWeightBreathCount = (TextView) findViewById(R.id.BtnWeightBreathCount);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        EdtDateBreathCount.setOnClickListener(this);
        IvCalendarBreathCount.setOnClickListener(this);
        BtnWeightBreathCount.setOnClickListener(this);
    }

    private void initActions() {
        Ad_Native.getInstance().showNative250(this, findViewById(R.id.FlNative));
        TvTitle.setText(getString(R.string.breath_count));
        calendar = Calendar.getInstance();
        Fab = calendar.get(Calendar.YEAR);
        dateLong = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DAY_OF_MONTH);
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String[] split = simpleDateFormat.format(calendar.getTime()).split("/");
        switch (Integer.parseInt(split[0])) {
            case 1:
                EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.jan) + " " + split[2]);
                break;
            case 2:
                EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.feb) + " " + split[2]);
                break;
            case 3:
                EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.mar) + " " + split[2]);
                break;
            case 4:
                EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.apr) + " " + split[2]);
                break;
            case 5:
                EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.may) + " " + split[2]);
                break;
            case 6:
                EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.jun) + " " + split[2]);
                break;
            case 7:
                EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.jul) + " " + split[2]);
                break;
            case 8:
                EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.aug) + " " + split[2]);
                break;
            case 9:
                EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.sep) + " " + split[2]);
                break;
            case 10:
                EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.oct) + " " + split[2]);
                break;
            case 11:
                EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.nov) + " " + split[2]);
                break;
            case 12:
                EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.dec) + " " + split[2]);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.EdtDateBreathCount:
            case R.id.IvCalendarBreathCount:
                GotoChooseDate();
                break;
            case R.id.BtnWeightBreathCount:
                GotoCalculateBloodDonation();
                break;
        }
    }

    private void GotoChooseDate() {
        BoolChooseDate = true;
        calendar.set(Fab, dateLong, date);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, setListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void GotoCalculateBloodDonation() {
        Calendar calendar2 = Calendar.getInstance();
        if (simpleDateFormat.format(calendar.getTime()).equals(simpleDateFormat.format(calendar2.getTime())) || calendar.getTime().after(calendar2.getTime())) {
            Toast.makeText(context, getResources().getString(R.string.valid_date), Toast.LENGTH_SHORT).show();
            return;
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

        IvDialogBanner.setImageResource(R.drawable.ic_breath_count);
        TvDialogName.setText(getResources().getString(R.string.breath_count));
        TvDialogDesc.setText(getResources().getString(R.string.breath_count_desc));
        CardIdealWeight.setVisibility(View.VISIBLE);

        TvDialogWeightSubTitle.setText(getString(R.string.no_of_breath));
        TvDialogWeightValue.setText(String.valueOf(getBreathDate(calendar)));

        BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());


        dialog.show();
    }

    private int getBreathDate(Calendar calendar) {
        TimeUnit.MILLISECONDS.toDays(Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis());
        Calendar calendar2 = Calendar.getInstance();
        int Fab = calendar2.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        int longs = calendar2.get(Calendar.MONTH) - calendar.get(Calendar.MONTH);
        long CalTime = calendar2.getTime().getTime() - calendar.getTime().getTime();
        int breathCal = (int) (((((float) ((CalTime / 1000) / 60)) / 60.0f) / 24.0f) + 0.0f);
        return ((Fab < 1 || Fab > 5) ? Fab > 5 ? breathCal * 11 : longs < 6 ? breathCal * 45 : breathCal * 27 : breathCal * 25) * 1440;
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
                    EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.jan) + " " + split[2]);
                    break;
                case 2:
                    EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.feb) + " " + split[2]);
                    break;
                case 3:
                    EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.mar) + " " + split[2]);
                    break;
                case 4:
                    EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.apr) + " " + split[2]);
                    break;
                case 5:
                    EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.may) + " " + split[2]);
                    break;
                case 6:
                    EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.jun) + " " + split[2]);
                    break;
                case 7:
                    EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.jul) + " " + split[2]);
                    break;
                case 8:
                    EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.aug) + " " + split[2]);
                    break;
                case 9:
                    EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.sep) + " " + split[2]);
                    break;
                case 10:
                    EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.oct) + " " + split[2]);
                    break;
                case 11:
                    EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.nov) + " " + split[2]);
                    break;
                case 12:
                    EdtDateBreathCount.setText(split[1] + " " + getResources().getString(R.string.dec) + " " + split[2]);
                    break;
            }
        }
    };
}