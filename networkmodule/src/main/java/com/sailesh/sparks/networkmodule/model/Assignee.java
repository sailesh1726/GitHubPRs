package com.sailesh.sparks.networkmodule.model;

import com.google.gson.annotations.SerializedName;

public class Assignee {
    @SerializedName("login")
    private String login;
    @SerializedName("id")
    private long id;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("type")
    private String type;
    @SerializedName("site_admin")
    private boolean isSiteAdmin;
}
