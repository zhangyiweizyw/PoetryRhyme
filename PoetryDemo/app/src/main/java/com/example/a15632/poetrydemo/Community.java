package com.example.a15632.poetrydemo;

import java.sql.Date;

public class Community {

    private String title;//标题
    private String content;//文本
    private int likecount;//喜欢数量
    private int commentcount;//评论数量
    private int seecount;//浏览数量
    private Date time;//发布时间
    private int userid;//用户id

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public Community(String title, String content, int likecount, int commentcount, Date time) {
        this.title = title;
        this.content = content;
        this.likecount = likecount;
        this.commentcount = commentcount;
        this.time = time;
    }
}
