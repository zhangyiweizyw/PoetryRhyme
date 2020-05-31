package com.example.a15632.poetrydemo.Entity;

import java.io.Serializable;
import java.util.Date;

public class Community implements Serializable {

    private int id;
    private String title;//标题
    private String content;//文本
    private int likequantity;//喜欢数量
    private int commentquantity;//评论数量
    private int pageview;//浏览数量
    private Date issuedate;//发布时间
    private int type;//类型1:原创诗词 2:社区话题
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Community() {
    }

    public Community(String title, String content, int likecount, int commentcount, int seecount, int type, Date time, User user) {
        this.title = title;
        this.content = content;
        this.likequantity = likecount;
        this.commentquantity = commentcount;
        this.pageview = seecount;
        this.type=type;
        this.issuedate = time;
        this.user = user;
    }

    public Community(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public Community(String title, String content,Date date,int type,User u) {
        this.user=u;
        this.issuedate=date;
        this.type=type;
        this.title = title;
        this.content = content;
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

    public int getLikequantity() {
        return likequantity;
    }

    public void setLikequantity(int likequantity) {
        this.likequantity = likequantity;
    }

    public int getCommentquantity() {
        return commentquantity;
    }

    public void setCommentquantity(int commentquantity) {
        this.commentquantity = commentquantity;
    }

    public int getPageview() {
        return pageview;
    }

    public void setPageview(int pageview) {
        this.pageview = pageview;
    }

    public Date getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(Date issuedate) {
        this.issuedate = issuedate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Community{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", likecount=" + likequantity +
                ", commentcount=" + commentquantity +
                ", seecount=" + pageview +
                ", type=" + type +
                ", time=" + issuedate +
                ", user=" + user +
                '}';
    }
}
