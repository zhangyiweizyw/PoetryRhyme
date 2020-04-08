package com.example.a15632.poetrydemo.Entity;

public class Sorts {
    public Sorts(int img,String title){
        this.img=img;
        this.title=title;
    }
    private int img;
    private String title;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
