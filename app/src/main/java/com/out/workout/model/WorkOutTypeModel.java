package com.out.workout.model;

public class WorkOutTypeModel {
    String TrainingName, Description;
    int TrainingImg;

    public WorkOutTypeModel(String trainingName, int trainingImg) {
        TrainingName = trainingName;
        TrainingImg = trainingImg;
    }

    public WorkOutTypeModel(String trainingName, String description, int trainingImg) {
        TrainingName = trainingName;
        Description = description;
        TrainingImg = trainingImg;
    }

    public String getTrainingName() {
        return TrainingName;
    }

    public void setTrainingName(String trainingName) {
        TrainingName = trainingName;
    }

    public int getTrainingImg() {
        return TrainingImg;
    }

    public void setTrainingImg(int trainingImg) {
        TrainingImg = trainingImg;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "WorkOutTypeModel{" +
                "TrainingName='" + TrainingName + '\'' +
                ", Description='" + Description + '\'' +
                ", TrainingImg=" + TrainingImg +
                '}';
    }
}
