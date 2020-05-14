package com.example.a15632.poetrydemo.Entity;

import java.sql.Date;

public class ComAndLike {

    private User u;
    private Date date;
    private Community community;

    public ComAndLike(User u, Date date, Community community) {
        this.u = u;
        this.date = date;
        this.community = community;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }
}
