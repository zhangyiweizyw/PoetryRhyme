package com.example.a15632.poetrydemo.Entity;

public class Poetry {
    public Poetry(String name,String author,String content){
        this.name=name;
        this.author=author;
        this.content=content;
    }
    private String name;
    private String author;
    private String content;

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
