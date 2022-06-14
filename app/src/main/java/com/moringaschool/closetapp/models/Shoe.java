package com.moringaschool.closetapp.models;

public class Shoe {
    private  String url;
    private String modelId;
private String pushId;


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
