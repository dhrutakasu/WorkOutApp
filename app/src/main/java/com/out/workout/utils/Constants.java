package com.out.workout.utils;

import com.out.workout.model.WorkoutExerciseModel;

import java.util.AbstractCollection;
import java.util.ArrayList;

public class Constants {
    public static String WorkoutType = "WorkoutType";
    public static String ChartType = "ChartType";
    public static String ExerciseImage = "ExerciseImage";
    public static String ExerciseName = "ExerciseName";
    public static String ExerciseDesc = "ExerciseDesc";
    public static String ExercisePos = "ExercisePos";
    public static String ExerciseDays = "ExerciseDays";
    public static String ExerciseRotate = "ExerciseRotate";
    public static String WorkList = "WorkList";
    public static String Count = "Count";
    public static ArrayList<WorkoutExerciseModel> WorkExerciseList = new ArrayList<>();

    public static String getCapsSentences(String tagName) {
        String[] splits = tagName.toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < splits.length; i++) {
            String eachWord = splits[i];
            if (i > 0 && eachWord.length() > 0) {
                sb.append(" ");
            }
            String cap = eachWord.substring(0, 1).toUpperCase()
                    + eachWord.substring(1);
            sb.append(cap);
        }
        return sb.toString();
    }
}
