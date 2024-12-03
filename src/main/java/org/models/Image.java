package org.models;

import java.io.InputStream;

public class Image {
    private int id;
    private int auto_id;
    private String image;

    public Image(int id, int auto_id, String image) {
        this.id = id;
        this.auto_id = auto_id;
        this.image = image;
    }

    public Image(String image, int auto_id) {
        this.image = image;
        this.auto_id = auto_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(int auto_id) {
        this.auto_id = auto_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
