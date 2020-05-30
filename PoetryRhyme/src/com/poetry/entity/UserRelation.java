package com.poetry.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="userrelation")
public class UserRelation {
	
	@Id
	@GeneratedValue(generator="increment_generator")
	@GenericGenerator(name="increment_generator",strategy="increment")
	private int id;
	
	@Column(name="myuserid")
	private int myuserid;
	
	@Column(name="foucsid")
	private int foucsid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMyuserid() {
		return myuserid;
	}
	public void setMyuserid(int myuserid) {
		this.myuserid = myuserid;
	}
	public int getFoucsid() {
		return foucsid;
	}
	public void setFoucsid(int foucsid) {
		this.foucsid = foucsid;
	}

}
