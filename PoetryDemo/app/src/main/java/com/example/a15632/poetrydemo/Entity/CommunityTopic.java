package com.example.a15632.poetrydemo.Entity;

public class CommunityTopic {

    private int id;

    private String title;// 标题

    private String content;// 文本

    private int likequantity;// 喜欢数量

    private int commentquantity;// 评论数量

    private int pageview;// 浏览数量

    private String issuedate;// 发布时间

    private int type;// 区别原创诗词or诗词赏析

    private User user;


    public CommunityTopic() {
        super();
    }

    public CommunityTopic(int id, String title, String content, int likequantity, int commentquantity, int pageview,
                          String issuedate, int type) {
        super();
        this.id = id;
        this.title = title;
        this.content = content;
        this.likequantity = likequantity;
        this.commentquantity = commentquantity;
        this.pageview = pageview;
        this.issuedate = issuedate;
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CommunityTopic [id=" + id + ", title=" + title + ", content=" + content + ", likequantity="
                + likequantity + ", commentquantity=" + commentquantity + ", pageview=" + pageview + ", issuedate="
                + issuedate + ", type=" + type + ", user=" + user + "]";
    }


}
