package com.out.workout.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreference {
    public static String CALCULATOR_AGE = "CalculatorAge";
    public static String COUNT_TIMER = "CounterTimer";
    public static String REST_TIMER = "RestTimer";
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

    public static int getInt(Context context, String countTimer, int timer) {
        return context.getSharedPreferences(PREF, 0).getInt(countTimer, timer);
    }

    public static void SetInt(Context context, String countTimer, int timer) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF, 0).edit();
        edit.putInt(countTimer, timer);
        edit.apply();
        edit.commit();
    }

    public static boolean getBoolean(Context context,String key, boolean IsSound) {
        return context.getSharedPreferences(PREF, 0).getBoolean(key, IsSound);
    }

    public static void SetBoolean(Context context,String key, boolean IsSound) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF, 0).edit();
        edit.putBoolean(key, IsSound);
        edit.apply();
        edit.commit();
    }

    public static String getString(Context context, String key, String en) {
        return context.getSharedPreferences(PREF, 0).getString(key, en);
    }
}
