package com.out.workout.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class DietTipsCategory  implements Serializable {
    @SerializedName("icon")
    private String icon;
    @SerializedName("layout_type")
    private int layoutType;
    @SerializedName("name")
    private String name;
    @SerializedName("slug")
    private String slug;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DietTipsCategory that = (DietTipsCategory) o;
        return layoutType == that.layoutType && Objects.equals(icon, that.icon) && Objects.equals(name, that.name) && Objects.equals(slug, that.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icon, layoutType, name, slug);
    }

    public final String getIcon() {
        return this.icon;
    }

    public final int getLayoutType() {
        return this.layoutType;
    }

    public final String getName() {
        return this.name;
    }

    public final String getSlug() {
        return this.slug;
    }

    public String toString() {
        return "DietTipsCategory(name=" + ((Object) this.name) + ", icon=" + ((Object) this.icon) + ", layoutType=" + this.layoutType + ", slug=" + this.slug + ')';
    }
}

