package com.poetry.dao;


import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.entity.Author;
import com.poetry.entity.Poetry;


@Repository
@Transactional(readOnly = false)
public class AuthorDaoImpl {
	@Resource
	private SessionFactory sessionFactory;

	// 首页获取随机诗句
	public List<Author> select() {

		Query query = this.sessionFactory.getCurrentSession().createQuery("from Author order by rand()");
		query.setMaxResults(9);
		List list = query.list();
		return list;

	}
}
