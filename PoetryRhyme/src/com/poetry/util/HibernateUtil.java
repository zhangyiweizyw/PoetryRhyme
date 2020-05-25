package com.poetry.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	static {
		final StandardServiceRegistry registry=new StandardServiceRegistryBuilder().configure().build();
		try {
			sessionFactory=new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			sessionFactory=null;
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
	
	//实例化返回SessionFactory
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	//关闭SessionFactory
	public static void closeSessionFactory() {
		sessionFactory.close();
	}
	
}
