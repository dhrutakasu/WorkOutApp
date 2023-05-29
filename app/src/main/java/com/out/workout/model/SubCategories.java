package com.out.workout.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public final class SubCategories implements Serializable {
    @SerializedName("icon")
    private String icon;
    @SerializedName("isSingleItem")
    private boolean isSingleItem;
    @SerializedName("layout_type")
    private int layoutType;
    @SerializedName("name")
    private String name;
    @SerializedName("properties")
    private ArrayList<String> properties;
    @SerializedName("slug")
    private String slug;

    public void setIcon(String str) {
        this.icon = str;
    }

    public void setSingleItem(boolean z) {
        this.isSingleItem = z;
    }

    public void setLayoutType(int i) {
        this.layoutType = i;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setSlug(String str) {
        this.slug = str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubCategories that = (SubCategories) o;
        return isSingleItem == that.isSingleItem && layoutType == that.layoutType && Objects.equals(icon, that.icon) && Objects.equals(name, that.name) && Objects.equals(properties, that.properties) && Objects.equals(slug, that.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icon, isSingleItem, layoutType, name, properties, slug);
    }

    public final String getIcon() {
        return this.icon;
    }

    public final String getName() {
        return this.name;
    }

    public final ArrayList<String> getProperties() {
        return this.properties;
    }

    public final String getSlug() {
        return this.slug;
    }

    public final boolean isSingleItem() {
        return this.isSingleItem;
    }

    public String toString() {
        return "SubCategories(name=" + ((Object) this.name) + ", icon=" + ((Object) this.icon) + ", layoutType=" + this.layoutType + ", slug=" + ((Object) this.slug) + ", isSingleItem=" + this.isSingleItem + ", properties=" + this.properties + ')';
    }
}
