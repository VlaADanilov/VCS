package org.models;

public class Like {
    private int id;
    private int userId;
    private int autoId;

    public Like(int userId, int autoId) {
        this.userId = userId;
        this.autoId = autoId;
    }

    public Like(int id, int userId, int autoId) {
        this.id = id;
        this.userId = userId;
        this.autoId = autoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }
}
