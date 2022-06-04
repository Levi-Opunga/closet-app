
package com.moringaschool.closetapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ShoePathsDict {

    @SerializedName("model_13300343")
    @Expose
    private Map<String,String> map;


    /**
     * No args constructor for use in serialization
     * 
     */
    public ShoePathsDict() {
    }

    /**
     * 
     * @param map
     */
    public ShoePathsDict(Map<String,String> map) {
        super();
        this.map = map;

    }

    public Map<String,String> getMap() {
        return map;
    }

    public void setMap(Map<String,String> map) {
        this.map = map;
    }

}
