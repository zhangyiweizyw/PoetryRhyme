package com.example.a15632.poetrydemo.Entity;

import java.sql.Date;

public class Community {

    private String title;//标题
    private String content;//文本
    private int likecount;//喜欢数量
    private int commentcount;//评论数量
    private int seecount;//浏览数量
    private int type;//类型
    private Date time;//发布时间
    // private int userid;//用户id
    private User user;

    public Community(String title, String content, int likecount, int commentcount, int seecount, int type,Date time, User user) {
        this.title = title;
        this.content = content;
        this.likecount = likecount;
        this.commentcount = commentcount;
        this.seecount = seecount;
        this.type=type;
        this.time = time;
        this.user = user;
    }

    public Community(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }



    public void setContent(String content) {
        this.content = content;
    }

    public int getLikecount() {
        return likecount;
    }

    public void setLikecount(int likecount) {
        this.likecount = likecount;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    public int getSeecount() {
        return seecount;
    }

    public void setSeecount(int seecount) {
        this.seecount = seecount;
    }



}
