package com.sailesh.sparks.networkmodule.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("login")
    private String login;

    public String getLogin() {
        return login;
    }
}
