package com.out.workout.ui.activity;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.out.workout.Ads.Ad_Banner;
import com.out.workout.Ads.Ad_Interstitial;
import com.out.workout.R;
import com.out.workout.ui.adapter.ExerciseAdapter;
import com.out.workout.ui.adapter.FitSliderAdapter;
import com.out.workout.ui.adapter.SliderLayoutManager;
import com.out.workout.utils.Constants;
import com.out.workout.utils.DecimalDigitsInputFilter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

public class FatProteinCalculatorActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fat_protein_calculator);
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
        Ad_Banner.getInstance().showBanner(this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlAdView), (RelativeLayout) findViewById(R.id.RlAdViewMain));

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
        TvTitle.setText("Protein Calculator");
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
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Load Ad....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Ad_Interstitial.getInstance().showInter(FatProteinCalculatorActivity.this, new Ad_Interstitial.MyCallback() {
                            @Override
                            public void callbackCall() {
                                GotoFitCalculate();
                            }
                        });
                    }
                }, 3000L);
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
        float cm;
        if (!isWeightValid()) {
            showErrorDialog(context,1);
            BtnFatCalculate.postDelayed(() -> updateValueOfWeight(), 2_000);
            return;
        }
        BtnFatCalculate.postDelayed(() -> updateValueOfHeightOnCalculate(), 2_000);

        if (!isHeightValid()) {
            showErrorDialog(context,2);
            return;
        }
        float parseFloat = Float.parseFloat(String.valueOf(((AppCompatEditText) findViewById(R.id.EdtFatWeight)).getText()));
        if (RgFatWeightUnit.getCheckedRadioButtonId() != R.id.RbFatKg) {
            parseFloat = Constants.toKg(parseFloat);
        }
        if (RgFatHeightUnit.getCheckedRadioButtonId() == R.id.RbFatCm) {
            cm = Float.parseFloat(String.valueOf((EdtFatHeightCm).getText()));
        } else {
            int parseInt = Integer.parseInt(String.valueOf(EdtFatFt.getText()));
            Integer intOrNull = Integer.valueOf(String.valueOf(EdtFatIn.getText()));
            cm = Constants.toCm((parseInt * 12) + (intOrNull == null ? 0 : intOrNull.intValue()));
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

        TextView BtnDialogWeight = dialog.findViewById(R.id.BtnDialogWeight);

        TextView TvDialogProteinCdcResult = dialog.findViewById(R.id.TvDialogProteinCdcResult);
        TextView TvDialogProteinWhoResult = dialog.findViewById(R.id.TvDialogProteinWhoResult);
        TextView TvDialogProteinResult = dialog.findViewById(R.id.TvDialogProteinResult);
        TextView TvDialogProteinResultMax = dialog.findViewById(R.id.TvDialogProteinResultMax);
        TextView TvDialogWeightSubTitle = dialog.findViewById(R.id.TvDialogWeightSubTitle);
        NestedScrollView ScrollProtein = dialog.findViewById(R.id.ScrollProtein);

        RelativeLayout  RlCardItem = dialog.findViewById(R.id.RlCardItem);
        RlCardItem.setVisibility(View.GONE);
        TvDialogWeightSubTitle.setVisibility(View.GONE);
        ScrollProtein.setVisibility(View.VISIBLE);
        double doublef = 10.0f * parseFloat;
        double Factor = (FlFatMale.isSelected() ? ((doublef + (cm * 6.25d)) - (getAge() * 5)) + 5.0d : ((doublef + (cm * 6.25d)) - (getAge() * 5)) - 161.0d) * getFactor();
        Pair<Integer, Integer> adaLimit = getLimit(parseFloat);
        ValueAnimator animator = ValueAnimator.ofInt(0, adaLimit.first.intValue());
        animator.setDuration(1000L);
        animator.addUpdateListener(valueAnimator -> TvDialogProteinResult.setText(valueAnimator.getAnimatedValue() + " - "));
        animator.start();
        ValueAnimator valueAnimator1 = ValueAnimator.ofInt(0, adaLimit.second.intValue());
        valueAnimator1.setDuration(1000L);
        valueAnimator1.addUpdateListener(valueAnimator -> TvDialogProteinResultMax.setText(valueAnimator.getAnimatedValue().toString()));

        valueAnimator1.start();
        StringBuilder builder = new StringBuilder();
        builder.append("The Centers for Disease Control and Prevention (CDC):<br/><font color=");
        builder.append("#448AFF");
        builder.append("><b>");
        double d2 = Factor / 4.0d;
        builder.append((int) Math.rint(0.098d * d2));
        builder.append(" - ");
        builder.append((int) Math.rint(d2 * 0.348d));
        builder.append(" grams/day.</b></font><br/>(10-35% of daily caloric intake).");

        TvDialogProteinWhoResult.setText(Constants.fromHtml("World Health Organization safe lower limit:<br/><font color=#448AFF><b>" + ((int) Math.rint(parseFloat / 1.2d)) + " grams/day.</b></font>"));

        TvDialogProteinCdcResult.setText(Constants.fromHtml(builder.toString()));
        BtnDialogWeight.setOnClickListener(view -> dialog.dismiss());


        dialog.show();
    }
    private Pair<Integer, Integer> getLimit(float f) {
        if (getIntent().getIntExtra("activity", 0) == 0) {
            return new Pair<>(Integer.valueOf((int) Math.rint(f * 0.8d)), Integer.valueOf((int) f));
        }
        return new Pair<>(Integer.valueOf((int) f), Integer.valueOf((int) Math.rint(f * 1.8d)));
    }

    private float getFactor() {
        int itemPosition = ((ExerciseAdapter) RvFatActivities.getAdapter()).getItemPosition();
        if (itemPosition == 1) {
            return 1.375f;
        }
        if (itemPosition == 2) {
            return 1.465f;
        }
        if (itemPosition == 3) {
            return 1.55f;
        }
        if (itemPosition != 4) {
            return itemPosition != 5 ? 1.2f : 1.9f;
        }
        return 1.725f;
    }

    public final void showErrorDialog(Context context, int i) {
        String str;
        if (i == 1) {
            str = RgFatWeightUnit.getCheckedRadioButtonId() == R.id.RbFatKg ? "Please input a valid Weight(1kg - 250kg)" : "Please input a valid Weight(2lb - 551lb)";
        } else {
            str = RgFatHeightUnit.getCheckedRadioButtonId() == R.id.RbFatCm ? "Please input a valid Height(1cm - 250cm)" : "Please input a valid Height(1' - 8'2\")";
        }
    }

    public final boolean isHeightValid() {
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

    public final boolean isWeightValid() {
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