package com.example.a15632.poetrydemo.Entity;

public class Author {
    public Author(int img, String title){
        this.img=img;
        this.name=title;
    }

    private int id;
    private String dynasty;
    private String name;
    private String intro;
    private int img;

    public Author() {
    }

    public Author(int id, String dynasty, String name, String intro) {
        this.id = id;
        this.dynasty = dynasty;
        this.name = name;
        this.intro = intro;
    }

    public Author(int id, String dynasty, String name, String intro, int img) {
        this.id = id;
        this.dynasty = dynasty;
        this.name = name;
        this.intro = intro;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
