package com.moringaschool.closetapp.models;

import java.io.Serializable;

public class Shoe implements Serializable {
    private  String url;
    private String modelId;
private String pushId;

    public Shoe(String url, String modelId, String pushId) {
        this.url = url;
        this.modelId = modelId;
        this.pushId = pushId;
    }

    public  Shoe (){

    }
    public Shoe(String url, String modelId) {
        this.url = url;
        this.modelId = modelId;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
