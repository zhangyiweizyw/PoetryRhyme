package com.poetry.dao;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.entity.User;

@Repository
@Transactional(readOnly = false)
public class UserDaoImpl {
	@Resource
	private SessionFactory sessionFactory;

	public void saveUser(User user) {
		System.out.println("Dao");
		this.sessionFactory.getCurrentSession().save(user);

	}
//  修改user相关信息

}
