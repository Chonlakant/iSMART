package com.mncomunity1.model;

import java.io.Serializable;

public class Message implements Serializable {
    String id, message, createdAt,imagUrl,status;
    User user;

    public Message() {
    }

    public Message(String id, String message, String createdAt, User user , String imagUrl, String status) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
        this.user = user;
        this.imagUrl = imagUrl;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImagUrl() {
        return imagUrl;
    }

    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
