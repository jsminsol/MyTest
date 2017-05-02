package com.sample.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	/*private static EntityManagerFactory emFactory;
	
	static{
		emFactory = Persistence.createEntityManagerFactory("com.sample");
	}
	
	public static CriteriaBuilder getCriteriaBuilder(){
		CriteriaBuilder builder = emFactory.getCriteriaBuilder();
		return builder;
	}
	
	public static EntityManager getEntityManager(){
		return emFactory.createEntityManager();
	}*/
	
	private static SessionFactory factory;
	
	public static SessionFactory getSessionFactory() {
		factory = new Configuration().configure().buildSessionFactory();
		
		return factory;
	}
}
