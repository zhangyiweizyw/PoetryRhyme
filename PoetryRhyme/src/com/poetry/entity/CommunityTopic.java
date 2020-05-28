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

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "communitytopic")
public class CommunityTopic {
	@Expose
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator",strategy="increment")
	private int id;
	@Expose
	@Column(name="title")
	private String title;//标题
	@Column(name="content")
	@Expose
    private String content;//文本
	@Column(name="likequantity")
	@Expose
    private int likequantity;//喜欢数量
	@Column(name="commentquantity")
	@Expose
    private int commentquantity;//评论数量
	@Column(name="pageview")
	@Expose
    private int pageview;//浏览数量
	@Column(name="issuedate")
	@Expose
    private Date issuedate;//发布时间
	@Column(name="type")
	@Expose
    private int type;//区别原创诗词or诗词赏析
	@Expose
	@ManyToOne
	@JoinColumn(name="userId")
	//社区话题与用户为多对一关系，映射到表中外键为userId
	private User user;
	
	
	
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
	@Override
	public String toString() {
		return "CommunityTopic [id=" + id + ", title=" + title + ", content=" + content + ", likequantity="
				+ likequantity + ", commentquantity=" + commentquantity + ", pageview=" + pageview + ", issuedate="
				+ issuedate + ", type=" + type + ", user=" + user + "]";
	}
	
	
}
