package com.out.workout.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Data implements Serializable {
    @SerializedName("categories")
    private ArrayList<DietTipsCategory> categories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return Objects.equals(categories, data.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categories);
    }

    public final ArrayList<DietTipsCategory> getCategories() {
        return this.categories;
    }

    public String toString() {
        return "Data(categories=" + this.categories + ')';
    }
}
