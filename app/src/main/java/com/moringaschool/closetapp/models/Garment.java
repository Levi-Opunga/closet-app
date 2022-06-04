
package com.moringaschool.closetapp.models;

import com.moringaschool.closetapp.models.ImageUrls;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.moringaschool.closetapp.models.Tryon;

import org.parceler.Parcel;

import java.io.Serializable;

@Parcel
public class Garment implements Serializable {

    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image_urls")
    @Expose
    private ImageUrls imageUrls;
    @SerializedName("tryon")
    @Expose
    private Tryon tryon;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Garment() {
    }

    /**
     * 
     * @param tryon
     * @param gender
     * @param imageUrls
     * @param id
     */
    public Garment(String gender, String id, ImageUrls imageUrls, Tryon tryon) {
        super();
        this.gender = gender;
        this.id = id;
        this.imageUrls = imageUrls;
        this.tryon = tryon;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ImageUrls getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ImageUrls imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Tryon getTryon() {
        return tryon;
    }

    public void setTryon(Tryon tryon) {
        this.tryon = tryon;
    }

}
