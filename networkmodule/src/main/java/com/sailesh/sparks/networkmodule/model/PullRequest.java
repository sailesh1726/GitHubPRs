package com.sailesh.sparks.networkmodule.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class PullRequest {
    @SerializedName("url")
    private String url;
    @SerializedName("id")
    private long id;
    @SerializedName("diff_url")
    private String diffUrl;
    @SerializedName("state")
    private String state;
    @SerializedName("title")
    private String title;
    @SerializedName("created_at")
    private Date createdDate;
    @SerializedName("assignee")
    private Assignee assignee;
    @SerializedName("labels")
    private List<Label> labels;
    @SerializedName("user")
    private User user;

    public String getUrl() {
        return url;
    }

    public long getId() {
        return id;
    }

    public String getDiffUrl() {
        return diffUrl;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public User getUser() {
        return user;
    }
}
