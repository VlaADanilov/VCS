package org.models;

import java.io.InputStream;

public class Image {
    private int id;
    private int auto_id;
    private InputStream image;

    public Image(int id, int auto_id, InputStream image) {
        this.id = id;
        this.auto_id = auto_id;
        this.image = image;
    }

    public Image(InputStream image, int auto_id) {
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

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
