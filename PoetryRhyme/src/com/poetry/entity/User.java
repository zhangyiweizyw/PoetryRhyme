package com.poetry.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "user")

public class User {
	@Expose
	@Id
	@GeneratedValue(generator = "increment_generator")
	@GenericGenerator(name = "increment_generator", strategy = "increment")
	private int id;
	@Expose
	@Column(name = "name")
	private String name;
	@Expose
	@Column(name = "phone")
	private String phone;
	@Expose
	@Column(name = "password")
	private String password;

	// 每个用户拥有多个原创诗词集合
	@OneToMany(mappedBy = "user", targetEntity = OriginalPoetry.class, cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<OriginalPoetry> origianlPoetrys = new HashSet<>();

	// 每个用户都有多个社区话题集合
	@OneToMany(mappedBy = "user", targetEntity = CommunityTopic.class, cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<CommunityTopic> communityTopics = new HashSet<>();
	

	
	
	public Set<OriginalPoetry> getOrigianlPoetrys() {
		return origianlPoetrys;
	}

	public void setOrigianlPoetrys(Set<OriginalPoetry> origianlPoetrys) {
		this.origianlPoetrys = origianlPoetrys;
	}
	
	
	public Set<CommunityTopic> getCommunityTopics() {
		return communityTopics;
	}

	public void setCommunityTopics(Set<CommunityTopic> communityTopics) {
		this.communityTopics = communityTopics;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phone=" + phone + ", password=" + password + "]";
	}

}
