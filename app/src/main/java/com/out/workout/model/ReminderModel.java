package com.out.workout.model;

public class ReminderModel {
    String Id, Mon = "false", Tue = "false", Wed = "false", Thu = "false", Fri = "false", Sat = "false", Sun = "false", Time, OnOff;

    public ReminderModel(String id, String mon, String tue, String wed, String thu, String fri, String sat, String sun, String time, String onOff) {
        Id = id;
        Mon = mon;
        Tue = tue;
        Wed = wed;
        Thu = thu;
        Fri = fri;
        Sat = sat;
        Sun = sun;
        Time = time;
        OnOff = onOff;
    }

    public ReminderModel() {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMon() {
        return Mon;
    }

    public void setMon(String mon) {
        Mon = mon;
    }

    public String getTue() {
        return Tue;
    }

    public void setTue(String tue) {
        Tue = tue;
    }

    public String getWed() {
        return Wed;
    }

    public void setWed(String wed) {
        Wed = wed;
    }

    public String getThu() {
        return Thu;
    }

    public void setThu(String thu) {
        Thu = thu;
    }

    public String getFri() {
        return Fri;
    }

    public void setFri(String fri) {
        Fri = fri;
    }

    public String getSat() {
        return Sat;
    }

    public void setSat(String sat) {
        Sat = sat;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSun() {
        return Sun;
    }

    public void setSun(String sun) {
        Sun = sun;
    }

    public String getOnOff() {
        return OnOff;
    }

    public void setOnOff(String onOff) {
        OnOff = onOff;
    }

    @Override
    public String toString() {
        return "ReminderModel{" +
                "Id='" + Id + '\'' +
                ", Mon='" + Mon + '\'' +
                ", Tue='" + Tue + '\'' +
                ", Wed='" + Wed + '\'' +
                ", Thu='" + Thu + '\'' +
                ", Fri='" + Fri + '\'' +
                ", Sat='" + Sat + '\'' +
                ", Sun='" + Sun + '\'' +
                ", Time='" + Time + '\'' +
                ", OnOff='" + OnOff + '\'' +
                '}';
    }
}
