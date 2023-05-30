package com.out.workout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NutrientModel {
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("noOfColumn")
    @Expose
    private Integer noOfColumn;
    @SerializedName("values")
    @Expose
    private List<ValueModel> values;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Integer getNoOfColumn() {
        return noOfColumn;
    }

    public void setNoOfColumn(Integer noOfColumn) {
        this.noOfColumn = noOfColumn;
    }

    public List<ValueModel> getValues() {
        return values;
    }

    public void setValues(List<ValueModel> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NutrientModel.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("heading");
        sb.append('=');
        sb.append(((this.heading == null) ? "<null>" : this.heading));
        sb.append(',');
        sb.append("noOfColumn");
        sb.append('=');
        sb.append(((this.noOfColumn == null) ? "<null>" : this.noOfColumn));
        sb.append(',');
        sb.append("values");
        sb.append('=');
        sb.append(((this.values == null) ? "<null>" : this.values));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.noOfColumn == null) ? 0 : this.noOfColumn.hashCode()));
        result = ((result * 31) + ((this.heading == null) ? 0 : this.heading.hashCode()));
        result = ((result * 31) + ((this.values == null) ? 0 : this.values.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NutrientModel) == false) {
            return false;
        }
        NutrientModel rhs = ((NutrientModel) other);
        return ((((this.noOfColumn == rhs.noOfColumn) || ((this.noOfColumn != null) && this.noOfColumn.equals(rhs.noOfColumn))) && ((this.heading == rhs.heading) || ((this.heading != null) && this.heading.equals(rhs.heading)))) && ((this.values == rhs.values) || ((this.values != null) && this.values.equals(rhs.values))));
    }
}
