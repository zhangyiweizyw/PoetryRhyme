package com.example.a15632.poetrydemo.Entity;

/**
 * 作品的实体类
 */
public class Works {
    public Works(int author_id,String title,String content,String date){
        this.author_id=author_id;
        this.title=title;
        this.date=date;
        this.content=content;
    }

    private int author_id;//作者的id
    private String title;//标题
    private String date;//日期
    private String content;//内容

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
