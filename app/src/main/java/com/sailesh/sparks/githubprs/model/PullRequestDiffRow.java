package com.sailesh.sparks.githubprs.model;

import java.util.List;

public class PullRequestDiffRow {
    private String rowTitle;
    private List<String[]> rowDataList;

    public String getRowTitle() {
        return rowTitle;
    }

    public void setRowTitle(String rowTitle) {
        this.rowTitle = rowTitle;
    }

    public List<String[]> getRowDataList() {
        return rowDataList;
    }

    public void setRowDataList(List<String[]> rowDataList) {
        this.rowDataList = rowDataList;
    }
}
