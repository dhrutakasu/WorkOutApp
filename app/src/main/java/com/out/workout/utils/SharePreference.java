package com.out.workout.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreference {
    public static String CALCULATOR_AGE = "CalculatorAge";
    public static int AGE = 25;
    public static String PREF = "CalculatorPref";


    public static void setCalculatorAge(Context context, int age) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF, 0).edit();
        edit.putInt(CALCULATOR_AGE, age);
        edit.apply();
        edit.commit();
    }

    public static int getCalculatorAge(Context context) {
        return context.getSharedPreferences(PREF, 0).getInt(CALCULATOR_AGE, AGE);
    }
}
