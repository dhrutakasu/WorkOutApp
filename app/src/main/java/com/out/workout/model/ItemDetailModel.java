package com.out.workout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemDetailModel {
    @SerializedName("description")
    @Expose
    private List<String> description;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nutrients")
    @Expose
    private List<NutrientModel> nutrients;
    @SerializedName("nutritionalContent")
    @Expose
    private List<NutritionalContent> nutritionalContent;
    @SerializedName("pros")
    @Expose
    private List<String> pros;
    @SerializedName("serving")
    @Expose
    private String serving;

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NutrientModel> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<NutrientModel> nutrients) {
        this.nutrients = nutrients;
    }

    public List<NutritionalContent> getNutritionalContent() {
        return nutritionalContent;
    }

    public void setNutritionalContent(List<NutritionalContent> nutritionalContent) {
        this.nutritionalContent = nutritionalContent;
    }

    public List<String> getPros() {
        return pros;
    }

    public void setPros(List<String> pros) {
        this.pros = pros;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ItemDetailModel.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null) ? "<null>" : this.description));
        sb.append(',');
        sb.append("icon");
        sb.append('=');
        sb.append(((this.icon == null) ? "<null>" : this.icon));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? "<null>" : this.name));
        sb.append(',');
        sb.append("nutrients");
        sb.append('=');
        sb.append(((this.nutrients == null) ? "<null>" : this.nutrients));
        sb.append(',');
        sb.append("nutritionalContent");
        sb.append('=');
        sb.append(((this.nutritionalContent == null) ? "<null>" : this.nutritionalContent));
        sb.append(',');
        sb.append("pros");
        sb.append('=');
        sb.append(((this.pros == null) ? "<null>" : this.pros));
        sb.append(',');
        sb.append("serving");
        sb.append('=');
        sb.append(((this.serving == null) ? "<null>" : this.serving));
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
        result = ((result * 31) + ((this.nutritionalContent == null) ? 0 : this.nutritionalContent.hashCode()));
        result = ((result * 31) + ((this.pros == null) ? 0 : this.pros.hashCode()));
        result = ((result * 31) + ((this.icon == null) ? 0 : this.icon.hashCode()));
        result = ((result * 31) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((result * 31) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((result * 31) + ((this.nutrients == null) ? 0 : this.nutrients.hashCode()));
        result = ((result * 31) + ((this.serving == null) ? 0 : this.serving.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ItemDetailModel) == false) {
            return false;
        }
        ItemDetailModel rhs = ((ItemDetailModel) other);
        return ((((((((this.nutritionalContent == rhs.nutritionalContent) || ((this.nutritionalContent != null) && this.nutritionalContent.equals(rhs.nutritionalContent))) && ((this.pros == rhs.pros) || ((this.pros != null) && this.pros.equals(rhs.pros)))) && ((this.icon == rhs.icon) || ((this.icon != null) && this.icon.equals(rhs.icon)))) && ((this.name == rhs.name) || ((this.name != null) && this.name.equals(rhs.name)))) && ((this.description == rhs.description) || ((this.description != null) && this.description.equals(rhs.description)))) && ((this.nutrients == rhs.nutrients) || ((this.nutrients != null) && this.nutrients.equals(rhs.nutrients)))) && ((this.serving == rhs.serving) || ((this.serving != null) && this.serving.equals(rhs.serving))));
    }
}
