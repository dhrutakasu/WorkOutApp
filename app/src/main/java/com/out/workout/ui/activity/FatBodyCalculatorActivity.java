package com.out.workout.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.out.workout.R;
import com.out.workout.ui.adapter.ExerciseAdapter;
import com.out.workout.ui.adapter.FitSliderAdapter;
import com.out.workout.ui.adapter.SliderLayoutManager;
import com.out.workout.utils.Constants;
import com.out.workout.utils.DecimalDigitsInputFilter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FatBodyCalculatorActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private Context context;
    private ImageView IvBack;
    private TextView TvTitle;
    private RecyclerView RvFatActivities, RvFatAge;
    private TextView TvFatWorkoutTitle;
    private EditText EdtFatHeightCm, EdtFatWeight, EdtFatFt, EdtFatFt2, EdtFatIn, EdtFatIn2;
    private LinearLayoutCompat LlFatFtIn;
    private RadioGroup RgFatWeightUnit, RgFatHeightUnit;
    private TextView BtnFatCalculate;
    private FrameLayout FlFatMale, FlFatFemale;
    private ArrayList<String> data = new ArrayList<>();
    private ImageView IvFatFemale, IvFatMale;
    private int age;
    private LinearLayoutCompat LlFatWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fat_body_calculator);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = findViewById(R.id.IvBack);
        TvTitle = findViewById(R.id.TvTitle);
        RvFatActivities = findViewById(R.id.RvFatActivities);
        RvFatAge = findViewById(R.id.RvFatAge);
        TvFatWorkoutTitle = findViewById(R.id.TvFatWorkoutTitle);
        EdtFatHeightCm = findViewById(R.id.EdtFatHeightCm);
        EdtFatWeight = findViewById(R.id.EdtFatWeight);
        EdtFatFt = findViewById(R.id.EdtFatFt);
        EdtFatFt2 = findViewById(R.id.EdtFatFt2);
        EdtFatIn = findViewById(R.id.EdtFatIn);
        EdtFatIn2 = findViewById(R.id.EdtFatIn2);
        RgFatWeightUnit = findViewById(R.id.RgFatWeightUnit);
        RgFatHeightUnit = findViewById(R.id.RgFatHeightUnit);
        LlFatFtIn = findViewById(R.id.LlFatFtIn);
        BtnFatCalculate = findViewById(R.id.BtnFatCalculate);
        FlFatMale = findViewById(R.id.FlFatMale);
        FlFatFemale = findViewById(R.id.FlFatFemale);
        IvFatFemale = findViewById(R.id.IvFatFemale);
        IvFatMale = findViewById(R.id.IvFatMale);
        LlFatWeight = findViewById(R.id.LlFatWeight);
    }

    private void initListeners() {
        IvBack.setOnClickListener(this);
        BtnFatCalculate.setOnClickListener(this);
        FlFatMale.setOnClickListener(this);
        FlFatFemale.setOnClickListener(this);
        RgFatWeightUnit.setOnCheckedChangeListener(this);
        RgFatHeightUnit.setOnCheckedChangeListener(this);
    }

    private void initActions() {
        List<Number> list = new ArrayList<>();
        for (int i = 5; i < 99; i++) {
            list.add(i);
        }
        ArrayList<String> arrayList = new ArrayList<>();
        for (Number number : list) {
            arrayList.add(String.valueOf(number.intValue()));
        }
        data = arrayList;
        System.out.println("----- arrrr : " + data.size());
        TvTitle.setText(getIntent().getBooleanExtra(Constants.BMR, false) ? "Body Fat Calculator" : "Ideal Weight Calculator");
        RvFatActivities.setVisibility(View.GONE);
        TvFatWorkoutTitle.setVisibility(View.GONE);
        if (!getIntent().getBooleanExtra(Constants.BMR, false)) {
            LlFatWeight.setVisibility(View.GONE);
        }
        setHorizontalSlider();
        FlFatMale.setSelected(true);
        RvFatActivities.setAdapter(new ExerciseAdapter());
        SetOnHeight();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvBack:
                onBackPressed();
                break;
            case R.id.BtnFatCalculate:
                GotoFitCalculate();
                break;
            case R.id.FlFatMale:
                SelectGenderMale();
                break;
            case R.id.FlFatFemale:
                SelectGenderFemale();
                break;
        }
    }

    public void SelectGenderFemale() {
        FlFatFemale.setSelected(true);
        FlFatMale.setSelected(false);
        IvFatFemale.setVisibility(View.VISIBLE);
        IvFatMale.setVisibility(View.GONE);
    }

    public void SelectGenderMale() {
        FlFatMale.setSelected(true);
        FlFatFemale.setSelected(false);
        IvFatFemale.setVisibility(View.GONE);
        IvFatMale.setVisibility(View.VISIBLE);
    }

    public final void setHorizontalSlider() {
        int screenWidth = (Constants.getScreenWidth(context) / 2) - Constants.dpToPx(context, 40);
        RvFatAge.setPadding(screenWidth, 0, screenWidth, 0);
        SliderLayoutManager sliderLayoutManager = new SliderLayoutManager(context);
        sliderLayoutManager.setCallback(i -> {
            String str = getData().get(i);
            setAge(Integer.parseInt(str));
        });

        RvFatAge.setLayoutManager(sliderLayoutManager);
        FitSliderAdapter fitSliderAdapter = new FitSliderAdapter();
        fitSliderAdapter.setData(getData());
        fitSliderAdapter.setCallback(view -> RvFatAge.smoothScrollToPosition(RvFatAge.getChildLayoutPosition(view)));
        RvFatAge.setAdapter(fitSliderAdapter);
        RvFatAge.postDelayed(() -> setHorizontalPicker(), 250L);
    }

    public void setHorizontalPicker() {
        RvFatAge.smoothScrollToPosition(20);
    }

    public void setAge(int i) {
        age = i;
    }

    public final int getAge() {
        return this.age;
    }

    public final ArrayList<String> getData() {
        return data;
    }

    public final void SetOnHeight() {
        EdtFatHeightCm.setFilters(new DecimalDigitsInputFilter[]{new DecimalDigitsInputFilter(5, 1)});
        EdtFatWeight.setFilters(new DecimalDigitsInputFilter[]{new DecimalDigitsInputFilter(6, 2)});
        EdtFatFt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                EdtFatFt2.setText(String.valueOf(editable));
            }
        });
        EdtFatIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                EdtFatIn2.setText(String.valueOf(editable));
            }
        });
    }

    public final void GotoFitCalculate() {
        float FloatCm;
        BtnFatCalculate.postDelayed((Runnable) () -> updateValueOfHeightOnCalculate(), 2_000);
        if (!isHeightValueValid()) {
            showErrorDialog(context, 2);
            return;
        }
        if (RgFatHeightUnit.getCheckedRadioButtonId() == R.id.RbFatCm) {
            FloatCm = Float.parseFloat(String.valueOf(EdtFatHeightCm.getText()));
        } else {
            int parseInt = Integer.parseInt(String.valueOf(EdtFatFt.getText()));
            Integer length = String.valueOf(EdtFatIn.getText()).length();
            FloatCm = Constants.toCm((parseInt * 12) + (length == null ? 0 : length.intValue()));
        }
        if (!getIntent().getBooleanExtra(Constants.BMR, false)) {
            showResultDialog(!getIntent().getBooleanExtra(Constants.BMR, false), FloatCm, getAge(), FlFatMale.isSelected(), 0f);
        } else if (!isWeightValueValid()) {
            showErrorDialog(context, 1);
            BtnFatCalculate.postDelayed(() -> updateValueOfWeight(), 2_000);
        } else {
            float parseFloat = Float.parseFloat(String.valueOf(EdtFatWeight.getText()));
            if (RgFatWeightUnit.getCheckedRadioButtonId() != R.id.RbFatKg) {
                parseFloat = Constants.toKg(parseFloat);
            }
            showResultDialog(!getIntent().getBooleanExtra(Constants.BMR, false), FloatCm, getAge(), FlFatMale.isSelected(), parseFloat);
        }
    }

    private void showResultDialog(boolean b, float cm, int age, boolean selected, float weight) {
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
        Button BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);
        if (b) {
            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            TextView TvDialogWWeightCal = dialog.findViewById(R.id.TvDialogWWeightCal);
            TextView TvDialogWWeightCalMax = dialog.findViewById(R.id.TvDialogWWeightCalMax);
            TextView TvDialogRobinsonResult = dialog.findViewById(R.id.TvDialogRobinsonResult);
            TextView TvDialogMillerResult = dialog.findViewById(R.id.TvDialogMillerResult);
            TextView TvDialogDevineResult = dialog.findViewById(R.id.TvDialogDevineResult);
            TextView TvDialogHombiResult = dialog.findViewById(R.id.TvDialogHombiResult);
            TextView TvDialogWeightMessage = dialog.findViewById(R.id.TvDialognWeightMessage);
            LinearLayout LlDialogMoreResult = dialog.findViewById(R.id.LlDialogMoreResult);
            NestedScrollView ScrollIdealWeightCalorie = dialog.findViewById(R.id.ScrollIdealWeightCalorie);

            ScrollIdealWeightCalorie.setVisibility(View.VISIBLE);

            TvDialogWeightSubTitle.setText("Healthy BMI Range");
            double doubleCm = cm;
            double doubleCenti = doubleCm * 0.01d * doubleCm * 0.01d;
            ValueAnimator animator = ValueAnimator.ofFloat(0.0f, (float) (18.5d * doubleCenti));
            animator.setDuration(1000L);
            animator.addUpdateListener(valueAnimator -> TvDialogWWeightCal.setText(Constants.formattedString(Float.parseFloat(valueAnimator.getAnimatedValue().toString())) + " - "));
            animator.start();

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, (float) (doubleCenti * 25.0d));
            valueAnimator.setDuration(1000L);
            valueAnimator.addUpdateListener(valueAnimator1 -> TvDialogWWeightCalMax.setText(Constants.formattedString(Float.parseFloat(valueAnimator1.getAnimatedValue().toString()))));
            valueAnimator.start();

            if (cm > 152.0f) {
                TvDialogWeightMessage.setVisibility(View.VISIBLE);
                LlDialogMoreResult.setVisibility(View.VISIBLE);
                double doubleCentimeter = doubleCm * 0.3937d;
                TvDialogRobinsonResult.setText(Constants.fromHtml("<font color=#448AFF><b>" + ((Object) new DecimalFormat("0.#").format(getWeightResult(1, doubleCentimeter, selected))) + " kgs.</b></font>"));
                TvDialogMillerResult.setText(Constants.fromHtml("<font color=#448AFF><b>" + ((Object) new DecimalFormat("0.#").format(getWeightResult(2, doubleCentimeter, selected))) + " kgs.</b></font>"));
                TvDialogDevineResult.setText(Constants.fromHtml("<font color=#448AFF><b>" + ((Object) new DecimalFormat("0.#").format(getWeightResult(3, doubleCentimeter, selected))) + " kgs.</b></font>"));
                TvDialogHombiResult.setText(Constants.fromHtml("<font color=#448AFF><b>" + ((Object) new DecimalFormat("0.#").format(getWeightResult(4, doubleCentimeter, selected))) + " kgs.</b></font>"));
            } else {
                TvDialogWeightMessage.setVisibility(View.GONE);
                LlDialogMoreResult.setVisibility(View.GONE);
            }
        } else {
            NestedScrollView ScrollBodyFatCalorie = dialog.findViewById(R.id.ScrollBodyFatCalorie);

            ScrollBodyFatCalorie.setVisibility(View.VISIBLE);

            TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
            TextView TvDialogBodyWWeightCal = dialog.findViewById(R.id.TvDialogBodyWWeightCal);
            TextView TvDialogBodyWeightMessage = dialog.findViewById(R.id.TvDialogBodyWeightMessage);
            TextView TvDialogBodyNote = dialog.findViewById(R.id.TvDialogBodyNote);

            if (getIntent().getBooleanExtra(Constants.BMR, false)) {
                float[] floats = new float[2];
                floats[0] = 0.0f;
                floats[1] = (float) ((((weight / (((cm * 0.01d) * cm) * 0.01d)) * 1.2d) + (age * 0.23d)) - (selected ? 16.2d : 5.4d));
                ValueAnimator animator = ValueAnimator.ofFloat(floats);
                animator.setDuration(1000L);
                animator.addUpdateListener(valueAnimator -> TvDialogBodyWWeightCal.setText(Constants.formattedString(Float.parseFloat(valueAnimator.getAnimatedValue().toString())) + "%"));
                animator.start();
                TvDialogBodyWeightMessage.setVisibility(View.GONE);
                TvDialogBodyNote.setVisibility(View.VISIBLE);
                TvDialogBodyWeightMessage.setVisibility(View.VISIBLE);
                TvDialogBodyWeightMessage.setText(selected ? "Recommended amount 8-14%" : "Recommended amount 20-25%");
                TvDialogWeightSubTitle.setText("Body Fat");
            } else {
                TvDialogBodyNote.setVisibility(View.GONE);
                TvDialogBodyWeightMessage.setVisibility(View.GONE);
            }
            if (!selected) {
                cm -= 10.0d;
            }
            TvDialogBodyWWeightCal.setText(new DecimalFormat("0.#").format(22.0d * cm * cm * 0.01d * 0.01d) + "kg");
        }
        BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());

        IvWeightClose.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    private double getWeightResult(int pos, double doubleCm, boolean BoolGender) {
        double double2;
        double double3;
        double double4;
        double double5;
        double double6;
        if (pos == 1) {
            if (BoolGender) {
                double2 = 52.0d;
                double3 = doubleCm - 60.0d;
                double4 = 1.9d;
            } else {
                double2 = 49.0d;
                double3 = doubleCm - 60.0d;
                double4 = 1.7d;
            }
            return double2 + (double3 * double4);
        }
        double d7 = 0D;
        if (pos != 2) {
            double5 = 45.5d;
            if (pos != 3) {
                return BoolGender ? ((doubleCm - 60.0d) * 2.3d) + 50.0d : ((doubleCm - 60.0d) * 2.3d) + 45.5d;
            } else if (BoolGender) {
                double6 = 0.0d;
            } else {
                d7 = doubleCm - 60.0d;
                double6 = 2.2d;
            }
        } else if (BoolGender) {
            double5 = 56.2d;
            d7 = doubleCm - 60.0d;
            double6 = 1.41d;
        } else {
            double5 = 53.1d;
            d7 = doubleCm - 60.0d;
            double6 = 1.36d;
        }
        return double5 + (d7 * double6);
    }

    public final void showErrorDialog(Context context, int i) {
        String str;
        if (i == 1) {
            str = RgFatWeightUnit.getCheckedRadioButtonId() == R.id.RbFatKg ? "Please input a valid Weight(1kg - 250kg)" : "Please input a valid Weight(2lb - 551lb)";
        } else {
            str = RgFatHeightUnit.getCheckedRadioButtonId() == R.id.RbFatCm ? "Please input a valid Height(1cm - 250cm)" : "Please input a valid Height(1' - 8'2\")";
        }
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public final boolean isHeightValueValid() {
        float f;
        if (RgFatHeightUnit.getCheckedRadioButtonId() == R.id.RbFatCm) {
            String valueOf = String.valueOf(EdtFatHeightCm.getText());
            if (valueOf.length() != 0) {
                try {
                    f = Float.parseFloat(valueOf);
                } catch (NumberFormatException unused) {
                    f = 0.0f;
                }
                return f > 0.0f && f <= 250.0f;
            }
            return false;
        }
        String valueOf2 = String.valueOf(EdtFatFt.getText());
        String valueOf3 = String.valueOf(EdtFatIn.getText());
        if (valueOf2.isEmpty() || !TextUtils.isDigitsOnly(valueOf2) || !TextUtils.isDigitsOnly(valueOf3) || Integer.parseInt(valueOf2) <= 0 || Integer.parseInt(valueOf2) > 8) {
            return false;
        }
        return Integer.parseInt(valueOf2) != 8 || valueOf3.isEmpty() || Integer.parseInt(valueOf3) <= 2;
    }

    public final boolean isWeightValueValid() {
        String obj;
        float f = 0.0f;
        try {
            Editable text = EdtFatWeight.getText();
            if (text != null && (obj = text.toString()) != null) {
                f = Float.parseFloat(obj);
            }
        } catch (NumberFormatException unused) {
        }
        if (EdtFatWeight.getText().toString().isEmpty()) {
            return false;
        }
        if (RgFatWeightUnit.getCheckedRadioButtonId() == R.id.RbFatKg) {
            return 1.0f <= f && f <= 250.0f;
        } else return 2.0f <= f && f <= 551.0f;
    }

    public final void updateValueOfWeight() {
        Editable editable;
        String obj;
        try {
            editable = EdtFatWeight.getText();
        } catch (NumberFormatException unused) {
            editable = null;
        }
        float f = 551.0f;
        if (editable != null && (obj = editable.toString()) != null) {
            float parseFloat = Float.parseFloat(obj);
            if (RgFatWeightUnit.getCheckedRadioButtonId() == R.id.RbFatKg) {
                if (parseFloat == 0.0f) {
                    f = 65.0f;
                } else if (parseFloat > 2.0f || parseFloat <= 0.0f) {
                    f = parseFloat >= 551.0f ? 250.0f : Constants.toKg(parseFloat);
                } else {
                    f = 1.0f;
                }
            } else {
                int i = (parseFloat > 0.0f ? 1 : (parseFloat == 0.0f ? 0 : -1));
                if (i == 0) {
                    f = 144.0f;
                } else if (parseFloat <= 1.0f && i > 0) {
                    f = 2.0f;
                } else if (parseFloat < 250.0f) {
                    f = Constants.toLb(parseFloat);
                }
            }
            EdtFatWeight.setText(String.valueOf(f));
            int length = String.valueOf(EdtFatWeight.getText()).length();
            if (length <= -1) {
                EdtFatWeight.setSelection(length);
                return;
            }
            return;
        }
        RgFatWeightUnit.getCheckedRadioButtonId();
        EdtFatWeight.setText(String.valueOf(551.0f));
        String.valueOf(EdtFatWeight.getText()).length();
    }

    public void updateValueOfHeightOnCalculate() {
        if (LlFatFtIn.getVisibility() == View.VISIBLE) {
            Pair<Integer, Integer> feetInches = getFeetInches();
            EdtFatFt.setText(String.valueOf(feetInches.first.intValue()));
            EdtFatFt.setSelection(String.valueOf(feetInches.first.intValue()).length());
            EdtFatIn.setText(String.valueOf(feetInches.second.intValue()));
            EdtFatIn.setSelection(String.valueOf(feetInches.second.intValue()).length());
            return;
        }
        EdtFatHeightCm.setText(Constants.toFormattedCm(getCmForBtnClick()));
        EdtFatHeightCm.setSelection(EdtFatHeightCm.getText() == null ? 0 : EdtFatHeightCm.getText().length());
    }

    private Pair<Integer, Integer> getFeetInches() {
        Editable editable;
        String obj;
        String obj2;
        try {
            editable = EdtFatFt.getText();
        } catch (NumberFormatException unused) {
            editable = null;
        }
        if (editable != null && (obj = editable.toString()) != null) {
            int parseInt = Integer.parseInt(obj);
            Editable text = EdtFatIn.getText();
            if (text != null && (obj2 = text.toString()) != null) {
                int parseInt2 = Integer.parseInt(obj2);
                if (String.valueOf(EdtFatFt.getText()).length() == 0) {
                    parseInt = 5;
                    parseInt2 = 7;
                }
                int i = 8;
                int i2 = parseInt > 0 ? parseInt > 8 ? 8 : parseInt : 1;
                if (parseInt2 >= 12) {
                    parseInt2 = 11;
                }
                if (i2 >= 8 || parseInt2 <= 2) {
                    i = i2;
                } else {
                    parseInt2 = 2;
                }
                return new Pair<>(Integer.valueOf(i), Integer.valueOf(parseInt2));
            }
            String.valueOf(EdtFatFt.getText()).length();
            return new Pair<>(1, 0);
        } else if (EdtFatIn.getText() != null) {
            int parseInt3 = Integer.parseInt(null);
            String.valueOf(EdtFatFt.getText()).length();
            return new Pair<>(1, Integer.valueOf(parseInt3));
        } else {
            String.valueOf(EdtFatFt.getText()).length();
            return new Pair<>(1, 0);
        }
    }

    private float getCmForBtnClick() {
        Editable editable;
        String obj;
        try {
            editable = EdtFatHeightCm.getText();
        } catch (NumberFormatException unused) {
            editable = null;
        }
        if (editable == null || (obj = editable.toString()) == null) {
            return 0.0f;
        }
        float parseFloat = Float.parseFloat(obj);
        if (EdtFatHeightCm.getText().toString().isEmpty()) {
            if (parseFloat <= 0.0f) {
                return 1.0f;
            }
            if (parseFloat > 250.0f) {
                return 250.0f;
            }
            return parseFloat;
        }
        return 170.2f;
    }

    private void updateValueOfHeight(int i) {
        if (i == R.id.RbFatCm) {
            EdtFatHeightCm.setVisibility(View.VISIBLE);
            LlFatFtIn.setVisibility(View.GONE);
            Pair<Integer, Integer> feetInches = getFeetInches();
            EdtFatHeightCm.setText(String.valueOf(Constants.toCm((feetInches.first.intValue() * 12) + feetInches.second.intValue())));
            EdtFatHeightCm.setSelection(EdtFatHeightCm.getText() != null ? EdtFatHeightCm.getText().length() : 0);
            return;
        }
        EdtFatHeightCm.setVisibility(View.GONE);
        LlFatFtIn.setVisibility(View.VISIBLE);
        Pair<Integer, Integer> feetAndInches = Constants.toFeetAndInches(getCm());
        EdtFatFt.setText(String.valueOf(feetAndInches.first.intValue()));
        EdtFatIn.setText(String.valueOf(feetAndInches.second.intValue()));
        EdtFatFt.setSelection(String.valueOf(feetAndInches.first.intValue()).length());
        EdtFatIn.setSelection(String.valueOf(feetAndInches.second.intValue()).length());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.RgFatHeightUnit:
                updateValueOfHeight(checkedId);
                break;
            case R.id.RgFatWeightUnit:
                updateValueOfWeight();
                break;
        }
    }

    private float getCm() {
        String obj;
        float f = 0.0f;
        try {
            Editable text = EdtFatHeightCm.getText();
            if (text != null && (obj = text.toString()) != null) {
                f = Float.parseFloat(obj);
            }
        } catch (NumberFormatException unused) {
        }
        if (EdtFatHeightCm.getText().toString().isEmpty()) {
            return 170.2f;
        }
        if (f < 31.0f) {
            return 31.0f;
        }
        if (f > 250.0f) {
            return 250.0f;
        }
        return f;
    }

}