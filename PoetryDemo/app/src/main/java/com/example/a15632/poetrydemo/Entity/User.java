package com.example.a15632.poetrydemo.Entity;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String password;
    private int headimg;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHeadimg() {
        return headimg;
    }

    public void setHeadimg(int headimg) {
        this.headimg = headimg;
    }

    public User(String username, String password, int headimg) {
        this.username = username;
        this.password = password;
        this.headimg = headimg;
    }
}
