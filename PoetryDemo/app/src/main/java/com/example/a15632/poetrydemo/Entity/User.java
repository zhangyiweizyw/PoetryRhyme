package com.example.a15632.poetrydemo.Entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String password;
    private String phone;
    private int headImg;


    public User(String name, String password, int headImg) {
        this.name = name;
        this.password = password;
        this.headImg = headImg;
    }

    public User(int id, String name, String password, String phone, int headImg) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.headImg = headImg;
    }

    public int getHeadImg() {
        return headImg;
    }

    public void setHeadImg(int headImg) {
        this.headImg = headImg;
    }

    public User() {

    }

    public User(int id, String phone) {
        this.id = id;
        this.phone = phone;
    }

    public User(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public User(int id, String name, String password, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
