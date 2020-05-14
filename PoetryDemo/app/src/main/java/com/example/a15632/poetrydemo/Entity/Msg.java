package com.example.a15632.poetrydemo.Entity;

import java.sql.Date;

public class Msg {

    private String username;
    private int headpsth;
    private String latestnews;
    private Date date;

    public Msg(String username, int headpsth, String latestnews, Date date) {
        this.username = username;
        this.headpsth = headpsth;
        this.latestnews = latestnews;
        this.date = date;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHeadpsth() {
        return headpsth;
    }

    public void setHeadpsth(int headpsth) {
        this.headpsth = headpsth;
    }

    public String getLatestnews() {
        return latestnews;
    }

    public void setLatestnews(String latestnews) {
        this.latestnews = latestnews;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
