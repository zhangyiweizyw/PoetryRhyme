package com.example.a15632.poetrydemo.Entity;

import java.sql.Date;

public class Comment {

    private User user;
    private String content;
    private Date date;

    public Comment(User user, String content, Date date) {
        this.user = user;
        this.content = content;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
