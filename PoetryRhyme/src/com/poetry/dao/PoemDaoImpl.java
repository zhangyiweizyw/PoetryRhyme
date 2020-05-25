package com.poetry.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.entity.Poem;
import com.poetry.entity.User;

@Repository
@Transactional(readOnly = false)
public class PoemDaoImpl {
	@Resource
	private SessionFactory sessionFactory;

//首页获取随机诗句
	public List<Poem> select() {

		Query query = this.sessionFactory.getCurrentSession().createQuery("from Poem order by rand() limit 10");
		List list = query.list();
		return list;

	}

}
