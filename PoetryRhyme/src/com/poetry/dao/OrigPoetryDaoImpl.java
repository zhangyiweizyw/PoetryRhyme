package com.poetry.dao;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.entity.OriginalPoetry;
import com.poetry.entity.User;

@Repository
@Transactional(readOnly = true)
public class OrigPoetryDaoImpl {
	
	@Resource
	private SessionFactory sessionFactory;
	
	//查询所有原创诗词及对应作者姓名
	public List<Object> selectOriginalPeotry() {
		System.out.println("originalpoetry");
		String hql = "select u.userName,o.title,o.content,o.likequantity,o.commentquantity,o.pageview,o.issuedate from User u,OriginalPoetry o where u.id=o.userId";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
		
	}
	
	//根据ID查询原创诗词
	public OriginalPoetry selectOriginalPoetryById(int id) {
		OriginalPoetry oriPeotry = this.sessionFactory.getCurrentSession().get(OriginalPoetry.class,new Integer(id));
		return oriPeotry;
	}
	
	
}
