package com.out.workout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ValueModel {

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("daily_value")
    @Expose
    private String dailyValue;
    @SerializedName("subDiv")
    @Expose
    private String subDiv;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDailyValue() {
        return dailyValue;
    }

    public void setDailyValue(String dailyValue) {
        this.dailyValue = dailyValue;
    }

    public String getSubDiv() {
        return subDiv;
    }

    public void setSubDiv(String subDiv) {
        this.subDiv = subDiv;
    }

    @Override
    public String toString() {
        return "ValueModel{" +
                "amount='" + amount + '\'' +
                ", name='" + name + '\'' +
                ", dailyValue='" + dailyValue + '\'' +
                ", subDiv='" + subDiv + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueModel that = (ValueModel) o;
        return Objects.equals(amount, that.amount) && Objects.equals(name, that.name) && Objects.equals(dailyValue, that.dailyValue) && Objects.equals(subDiv, that.subDiv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, name, dailyValue, subDiv);
    }
}
