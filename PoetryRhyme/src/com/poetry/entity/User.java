package com.poetry.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	@Column(name="password")
    private String password;
	@Column(name="head_img")
    private String headimg;
	
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
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", headimg=" + headimg + "]";
	}

	
	
    
	
}
