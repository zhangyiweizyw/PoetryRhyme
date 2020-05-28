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

	public User findUser(int id) {

		return this.userDaoImpl.find(id);
	}

	public User findUser(String phone) {

		return this.userDaoImpl.find(phone);
	}

	public User findUser(String name, String pwd) {

		return this.userDaoImpl.find(name, pwd);
	}

}
