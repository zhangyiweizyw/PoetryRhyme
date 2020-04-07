package com.example.a15632.poetrydemo;

public class User {

    private String username;
    private String password;
    private String headimg;

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

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public User(String username, String password, String headimg) {
        this.username = username;
        this.password = password;
        this.headimg = headimg;
    }
}
