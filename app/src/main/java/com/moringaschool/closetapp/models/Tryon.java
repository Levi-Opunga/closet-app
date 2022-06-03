
package com.moringaschool.closetapp;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Tryon {

    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("outerwear_splittable")
    @Expose
    private Boolean outerwearSplittable;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Tryon() {
    }

    /**
     * 
     * @param outerwearSplittable
     * @param category
     */
    public Tryon(String category, Boolean outerwearSplittable) {
        super();
        this.category = category;
        this.outerwearSplittable = outerwearSplittable;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getOuterwearSplittable() {
        return outerwearSplittable;
    }

    public void setOuterwearSplittable(Boolean outerwearSplittable) {
        this.outerwearSplittable = outerwearSplittable;
    }

}
