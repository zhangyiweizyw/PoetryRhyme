package com.poetry.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator",strategy="increment")
	private int id;

	@Column(name="user_name")
	private String userName;
	@Column(name="phone")
	private String phone;
	@Column(name="password")
    private String password;
	@Column(name="head_img")
    private String headimg;
	
	//每个用户拥有多个原创诗词集合
	@OneToMany(mappedBy="user",targetEntity=OriginalPoetry.class,
			cascade=CascadeType.ALL)
	@OrderColumn(name="originalpoetryindex")
	private List<OriginalPoetry> origianlPoetrys = new ArrayList<>();
	
	//每个用户都有多个社区话题集合
	@OneToMany(mappedBy="user",targetEntity=CommunityTopic.class,
			cascade=CascadeType.ALL)
	@OrderColumn(name="communitytopicindex")
	private List<CommunityTopic> communityTopics = new ArrayList<>();
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public List<OriginalPoetry> getOrigianlPoetrys() {
		return origianlPoetrys;
	}
	public void setOrigianlPoetrys(List<OriginalPoetry> origianlPoetrys) {
		this.origianlPoetrys = origianlPoetrys;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", headimg=" + headimg + "]";
	}

}
