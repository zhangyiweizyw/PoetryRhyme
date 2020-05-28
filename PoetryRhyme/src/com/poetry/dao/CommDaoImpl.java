package com.poetry.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.entity.CommunityTopic;

@Repository
@Transactional(readOnly=true)
public class CommDaoImpl {
	
	@Resource
	private SessionFactory sessionFactory;
	
	//查询所有社区话题及对应作者姓名
	public List selectCommunityTopic(){
		System.out.println("communitytopic");
		
		Query query = this.sessionFactory.getCurrentSession().createQuery("from CommunityTopic");
		return query.list();
	}
	
	//根据ID查询单条社区话题
	public CommunityTopic selectCommunityTopicById(int id) {
		CommunityTopic comTopic = this.sessionFactory.getCurrentSession().get(CommunityTopic.class, new Integer(id));
		return comTopic;
	}
}
