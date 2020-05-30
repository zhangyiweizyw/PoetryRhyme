package com.poetry.dao;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.entity.CommunityTopic;
import com.poetry.entity.OriginalPoetry;
import com.poetry.entity.User;

@Repository
@Transactional(readOnly = false)
public class OrigPoetryDaoImpl {

	@Resource
	private SessionFactory sessionFactory;

	// 查询所有原创诗词及对应作者姓名
	public List<OriginalPoetry> selectOriginalPeotry() {
		System.out.println("originalpoetry");
		// String hql = "select
		// u.userName,o.title,o.content,o.likequantity,o.commentquantity,o.pageview,o.issuedate
		// from User u,OriginalPoetry o where u.id=o.userId";
		String hql = "from OriginalPoetry";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();

	}

	// 根据ID查询原创诗词
	public OriginalPoetry selectOriginalPoetryById(int id) {
		OriginalPoetry oriPeotry = this.sessionFactory.getCurrentSession().get(OriginalPoetry.class, new Integer(id));
		return oriPeotry;
	}

	// 新增一条原创诗词
	public void addOriginalPoetry(OriginalPoetry op) {
		this.sessionFactory.getCurrentSession().save(op);
	}

	// 根据id修改社区话题
	public void updateOriginalPoetry(OriginalPoetry op) {
		OriginalPoetry opoetry = this.sessionFactory.getCurrentSession().get(OriginalPoetry.class,
				new Integer(op.getId()));
		opoetry .setTitle(op.getTitle());
		opoetry .setContent(op.getContent());
		opoetry .setIssuedate(op.getIssuedate());
		opoetry .setLikequantity(op.getLikequantity());
		opoetry .setCommentquantity(op.getCommentquantity());
		opoetry .setPageview(op.getPageview());
		opoetry .setType(op.getType());
		// user是不可能变的
		this.sessionFactory.getCurrentSession().save(op);
	}

}
