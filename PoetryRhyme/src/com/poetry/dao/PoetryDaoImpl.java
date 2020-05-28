package com.poetry.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.entity.Poem;
import com.poetry.entity.Poetry;

@Repository
@Transactional(readOnly = false)
public class PoetryDaoImpl {
	@Resource
	private SessionFactory sessionFactory;

	// 首页获取随机诗句
	public List<Poetry> select() {

		Query query = this.sessionFactory.getCurrentSession().createQuery("from Poetry order by rand()");
		query.setMaxResults(10);
		List list = query.list();
		return list;

	}

	// 查询获取诗句
	public List<Poetry> find(String str) {

		Query query = this.sessionFactory.getCurrentSession()
				.createQuery("from Poetry p where p.name like :name or p.content like :content or p.author like :author or p.dynasty like :dynasty order by p.id");
		query.setParameter("name", "%"+str+"%");
		query.setParameter("content", "%"+str+"%");
		query.setParameter("author", "%"+str+"%");
		query.setParameter("dynasty", "%"+str+"%");
		query.setMaxResults(10);
		List list = query.list();
		System.out.println(list.size());
		return list;

	}
	
	// 查询天气诗句
		public Poetry findWeather(String weather) {

			Query query = this.sessionFactory.getCurrentSession()
					.createQuery("from Poetry p where p.name like :name or p.content like :content order by rand()");
			query.setParameter("name", "%"+weather+"%");
			query.setParameter("content", "%"+weather+"%");
			query.setMaxResults(1);
			Poetry p = (Poetry)query.uniqueResult();
			System.out.println(p.getContent());
			return p;

		}
}
