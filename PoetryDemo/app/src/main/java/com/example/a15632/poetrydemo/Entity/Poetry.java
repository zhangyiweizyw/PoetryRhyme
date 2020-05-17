package com.example.a15632.poetrydemo.Entity;

import java.io.Serializable;

public class Poetry implements Serializable {
    public Poetry(String name,String author,String content){
        this.name=name;
        this.author=author;
        this.content=content;
    }
    private String name;
    private String author;
    private String content;
    private String dynasty;//朝代

    public Poetry(String name, String author, String content, String dynasty) {
        this.name = name;
        this.author = author;
        this.content = content;
        this.dynasty = dynasty;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
