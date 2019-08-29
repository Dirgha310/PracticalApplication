package com.arinspect.practicalapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//model class for first json object
public class RowsList {

    private String title;

    @SerializedName("rows")
    private List<Rows> rowsList;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<Rows> getList() {
        return rowsList;
    }
    public void setRowsList(List<Rows> rowsList) {
        this.rowsList = rowsList;
    }

}
