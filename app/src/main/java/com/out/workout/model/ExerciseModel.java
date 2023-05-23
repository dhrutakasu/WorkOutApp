package com.out.workout.model;

import android.content.res.TypedArray;

import java.util.Arrays;

public class ExerciseModel {
    int id;
    String ExerciseName,ExerciseDesc,WorkoutType;
    int[] ExerciseImg;
    int[] ExerciseType;

    public ExerciseModel(int id, String exerciseName, String exerciseDesc, String workoutType, int[] exerciseImg, int[] exerciseType) {
        this.id = id;
        ExerciseName = exerciseName;
        ExerciseDesc = exerciseDesc;
        WorkoutType = workoutType;
        ExerciseImg = exerciseImg;
        ExerciseType = exerciseType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getWorkoutType() {
        return WorkoutType;
    }

    public void setWorkoutType(String workoutType) {
        WorkoutType = workoutType;
    }

    public int[] getExerciseImg() {
        return ExerciseImg;
    }

    public void setExerciseImg(int[] exerciseImg) {
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
        return "ExerciseModel{" +
                "ExerciseName='" + ExerciseName + '\'' +
                ", ExerciseDesc='" + ExerciseDesc + '\'' +
                ", WorkoutType='" + WorkoutType + '\'' +
                ", ExerciseImg=" + Arrays.toString(ExerciseImg) +
                ", ExerciseType=" + Arrays.toString(ExerciseType) +
                '}';
    }
}
