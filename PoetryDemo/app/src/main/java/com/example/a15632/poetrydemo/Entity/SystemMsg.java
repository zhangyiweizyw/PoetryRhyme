package com.example.a15632.poetrydemo.Entity;

public class SystemMsg {

    private String title;
    private int img;
    private String content;

    public SystemMsg(String title, int img, String content) {
        this.title=title;
        this.img = img;
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
