package com.poetry.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "originalpoetry")
public class OriginalPoetry {
	
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator",strategy="increment")
	private int id;
	@Column(name="title")
	private String title;//标题
	@Column(name="content")
    private String content;//文本
	@Column(name="likequantity")
    private int likequantity;//喜欢数量
	@Column(name="commentquantity")
    private int commentquantity;//评论数量
	@Column(name="pageview")
    private int pageview;//浏览数量
	@Column(name="issuedate")
    private Date issuedate;//发布时间
	@Column(name="type")
    private int type;//区别原创诗词or诗词赏析
	
	//原创诗词与用户为多对一关系，映射到表中外键为userId
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	@Override
	public String toString() {
		return "OriginalPoetry [id=" + id + ", title=" + title + ", content=" + content + ", likequantity="
				+ likequantity + ", commentquantity=" + commentquantity + ", pageview=" + pageview + ", issuedate="
				+ issuedate + ", type=" + type + ", user=" + user + "]";
	}
	
	
    
}
