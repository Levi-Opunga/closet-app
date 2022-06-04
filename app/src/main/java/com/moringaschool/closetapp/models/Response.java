
package com.moringaschool.closetapp.models;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Response implements Serializable {

    @SerializedName("total_page")
    @Expose
    private Integer totalPage;
    @SerializedName("garments")
    @Expose
    private List<Garment> garments = null;
    @SerializedName("success")
    @Expose
    private Boolean success;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Response() {
    }

    /**
     * 
     * @param garments
     * @param totalPage
     * @param success
     */
    public Response(Integer totalPage, List<Garment> garments, Boolean success) {
        super();
        this.totalPage = totalPage;
        this.garments = garments;
        this.success = success;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<Garment> getGarments() {
        return garments;
    }

    public void setGarments(List<Garment> garments) {
        this.garments = garments;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
