package com.out.workout.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreference {
    public static String CALCULATOR_AGE = "CalculatorAge";
    public static String COUNT_TIMER = "CountTimer";
    public static String IS_SOUND = "IsSound";
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

    public static int getInt(Context context, int timer) {
        return context.getSharedPreferences(PREF, 0).getInt(COUNT_TIMER, timer);
    }

    public static void SetInt(Context context, int timer) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF, 0).edit();
        edit.putInt(COUNT_TIMER, timer);
        edit.apply();
        edit.commit();
    }

    public static boolean getBoolean(Context context, boolean IsSound) {
        return context.getSharedPreferences(PREF, 0).getBoolean(IS_SOUND, IsSound);
    }

    public static void SetBoolean(Context context, boolean IsSound) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF, 0).edit();
        edit.putBoolean(IS_SOUND, IsSound);
        edit.apply();
        edit.commit();
    }
}
