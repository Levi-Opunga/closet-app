
package com.moringaschool.closetapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("shoe_model_ids")
    @Expose
    private List<String> shoeModelIds = null;
    @SerializedName("shoe_paths_dict")
    @Expose
    private ShoePathsDict shoePathsDict;
    @SerializedName("success")
    @Expose
    private Boolean success;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Example() {
    }

    /**
     * 
     * @param success
     * @param shoePathsDict
     * @param shoeModelIds
     */
    public Example(List<String> shoeModelIds, ShoePathsDict shoePathsDict, Boolean success) {
        super();
        this.shoeModelIds = shoeModelIds;
        this.shoePathsDict = shoePathsDict;
        this.success = success;
    }

    public List<String> getShoeModelIds() {
        return shoeModelIds;
    }

    public void setShoeModelIds(List<String> shoeModelIds) {
        this.shoeModelIds = shoeModelIds;
    }

    public ShoePathsDict getShoePathsDict() {
        return shoePathsDict;
    }

    public void setShoePathsDict(ShoePathsDict shoePathsDict) {
        this.shoePathsDict = shoePathsDict;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
