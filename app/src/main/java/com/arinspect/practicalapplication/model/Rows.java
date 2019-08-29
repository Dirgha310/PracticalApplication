package com.arinspect.practicalapplication.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//model class to get object from jsonArray

//Entity to bind class for Room
@Entity
public class Rows {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String imageHref;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }
    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

}
