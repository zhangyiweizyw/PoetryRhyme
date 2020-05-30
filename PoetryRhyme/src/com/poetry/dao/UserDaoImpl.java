package com.poetry.dao;

import java.util.List;

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

	public List<User> back() {

		Query query = this.sessionFactory.getCurrentSession().createQuery("from User");
		List list = query.list();
		return list;

	}
	
	public User find(int id) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from User u where u.id = :id");
		query.setParameter("id", id);
		System.out.println(id);
		User user = (User) query.uniqueResult();
		System.out.println(user.toString());
		return user;
	}

	public User find(String phone) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from User u where u.phone = :phone");
		query.setParameter("phone", phone);
		User user = (User) query.uniqueResult();
		return user;
	}

	public User find(String name,String pwd) {

		Query query = this.sessionFactory.getCurrentSession().createQuery("from User u where u.name = :name and u.password = :password");
		query.setParameter("name", name);
		query.setParameter("password", pwd);
		User user = (User) query.uniqueResult();
		System.out.println("DAO"+user.toString());
		return user;

	}
	public List<User> findFoucsUser(int myuserid){
		String hql="select u from User u where u.id in(select ur.foucsid from UserRelation ur where ur.myuserid=?)";
		Query query=this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, myuserid);
		List<User>users=query.list();
		return users;
		
	}

}
