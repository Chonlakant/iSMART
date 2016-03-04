package com.mncomunity1.model;

/**
 * Created by root1 on 1/26/16.
 */
public class listMain {
    String title;
    String urlImage;

    public listMain(String title, String urlImage) {
        this.title = title;
        this.urlImage = urlImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
