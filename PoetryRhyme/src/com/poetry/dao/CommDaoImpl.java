package com.poetry.dao;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.poetry.entity.CommunityTopic;
import com.poetry.entity.User;
import com.poetry.util.HibernateUtil;

@Repository
@Transactional(readOnly=false)
public class CommDaoImpl {
	
	@Resource
	private SessionFactory sessionFactory;
	
	//查询所有社区话题及对应作者信息
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
	//新增一条社区话题
	public void addCommunityTopic(CommunityTopic ct){
		System.out.println(ct.getTitle());
		System.out.println(ct.getUser().getId());
		String sql = "insert into communitytopic(title,content,userId,pageview,likequantity,commentquantity,issuedate,type) "
				+ "values("+ct.getTitle()+","+ct.getContent()+","+ct.getUser().getId()+","+ct.getPageview()+","+
						ct.getLikequantity()+","+ct.getCommentquantity()+","+ct.getIssuedate()+","+ct.getType()+")";
		this.sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();

		/*this.sessionFactory.getCurrentSession().save(ct);*/
	}
	//根据id修改社区话题
	public void updateCommunityTopic(CommunityTopic ct){
		CommunityTopic comTopic = this.sessionFactory.getCurrentSession().get(CommunityTopic.class, new Integer(ct.getId()));
		comTopic.setTitle(ct.getTitle());
		comTopic.setContent(ct.getContent());
		comTopic.setIssuedate(ct.getIssuedate());
		comTopic.setLikequantity(ct.getLikequantity());
		comTopic.setCommentquantity(ct.getCommentquantity());
		comTopic.setPageview(ct.getPageview());
		comTopic.setType(ct.getType());
		//user是不可能变的
		this.sessionFactory.getCurrentSession().save(ct);
	}
}
