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
public class CommunityTopicDaoImpl {
	
	@Resource
	private SessionFactory sessionFactory;
	
	//查询所有社区话题及对应作者姓名
	public List<Object> selectCommunityTopic(){
		System.out.println("communitytopic");
		String hql="select u.userName，c.title,c.content,c.likequantity,c.commentquantity,c.pageview,c.issuedate from User u,CommunityTopic c where u.id=c.userId";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
	//根据ID查询单条社区话题
	public CommunityTopic selectCommunityTopicById(int id) {
		CommunityTopic comTopic = this.sessionFactory.getCurrentSession().get(CommunityTopic.class, new Integer(id));
		return comTopic;
	}
}
