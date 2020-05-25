package com.poetry.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.dao.UserDaoImpl;
import com.poetry.entity.User;

@Service
@Transactional(readOnly = false)
public class UserServiceImpl {

	@Resource
	private UserDaoImpl userDaoImpl;

	public void addUser(User user) {
		System.out.println("Service");
		
		this.userDaoImpl.saveUser(user);

	}
	
	public List<User> back(){
		
		return this.userDaoImpl.back();
	}



}
