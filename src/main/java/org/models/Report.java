package org.models;

public class Report {
    private int id;
    private int auto_id;
    private String comment;
    private int user_id;

    public Report(int auto_id, String comment, int user_id) {
        this.auto_id = auto_id;
        this.comment = comment;
        this.user_id = user_id;
    }

    public Report() {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
