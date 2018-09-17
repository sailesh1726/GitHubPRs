package com.sailesh.sparks.networkmodule.model;

import com.google.gson.annotations.SerializedName;

public class Label {
    @SerializedName("name")
    private String name;
    @SerializedName("color")
    private String color;
    @SerializedName("default")
    private boolean isDefault;

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
