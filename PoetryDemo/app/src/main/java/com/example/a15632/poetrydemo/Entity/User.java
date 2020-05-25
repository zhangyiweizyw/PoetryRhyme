package com.example.a15632.poetrydemo.Entity;

public class User {
    private int id;
    private String username;
    private String password;
    private int headimg;

    public int getId() {
        return id;
    }

    public User(String username, String password, int headimg) {
        this.username = username;
        this.password = password;
        this.headimg = headimg;
    }

    public User(int id, String username, String password, int headimg) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.headimg = headimg;
    }

    public User() {

    }

    public void setId(int id) {
        this.id = id;
    }

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


}
