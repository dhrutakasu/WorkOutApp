package com.out.workout.model;

import android.content.res.TypedArray;

import java.util.Arrays;

public class WorkoutExerciseModel {
    String ExerciseName,ExerciseDesc;
    TypedArray ExerciseImg;
    int[] ExerciseType;

    public WorkoutExerciseModel(String exerciseName, String exerciseDesc, TypedArray exerciseImg, int[] exerciseType) {
        ExerciseName = exerciseName;
        ExerciseDesc = exerciseDesc;
        ExerciseImg = exerciseImg;
        ExerciseType = exerciseType;
    }

    public String getExerciseName() {
        return ExerciseName;
    }

    public void setExerciseName(String exerciseName) {
        ExerciseName = exerciseName;
    }

    public String getExerciseDesc() {
        return ExerciseDesc;
    }

    public void setExerciseDesc(String exerciseDesc) {
        ExerciseDesc = exerciseDesc;
    }

    public TypedArray getExerciseImg() {
        return ExerciseImg;
    }

    public void setExerciseImg(TypedArray exerciseImg) {
        ExerciseImg = exerciseImg;
    }

    public int[] getExerciseType() {
        return ExerciseType;
    }

    public void setExerciseType(int[] exerciseType) {
        ExerciseType = exerciseType;
    }

    @Override
    public String toString() {
        return "WorkoutExerciseModel{" +
                "ExerciseName='" + ExerciseName + '\'' +
                ", ExerciseDesc='" + ExerciseDesc + '\'' +
                ", ExerciseImg=" + ExerciseImg +
                ", ExerciseType=" + Arrays.toString(ExerciseType) +
                '}';
    }
}
