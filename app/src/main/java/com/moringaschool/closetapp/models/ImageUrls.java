
package com.moringaschool.closetapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.io.Serializable;

@Parcel
public class ImageUrls implements Serializable {

    @SerializedName("product_image")
    @Expose
    private String productImage;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ImageUrls() {
    }

    /**
     * 
     * @param productImage
     */
    public ImageUrls(String productImage) {
        super();
        this.productImage = productImage;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

}
