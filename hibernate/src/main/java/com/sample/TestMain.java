package com.sample;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.sample.util.HibernateUtil;

public class TestMain {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		Transaction tx = 
		session.beginTransaction();
		Criteria crit = session.createCriteria(Test.class);
		crit.addOrder(Order.asc("id"));
		
		List<Test> list = crit.list();
		for(Test t : list){
			System.out.println("id : "+t.getId()+", content : "+t.getContent());	
		}
		
		tx.commit();
		
		/*CriteriaBuilder builder = HibernateUtil.getCriteriaBuilder();
		EntityManager em = HibernateUtil.getEntityManager();
		CriteriaQuery<Test> query = builder.createQuery(Test.class);
		Root<Test> root = query.from(Test.class);
		query.select(root);
		List<Test> test = em.createQuery(query).getResultList();
		for(Test t : test){
			System.out.println("id:"+t.getId()+", title:"+t.getTitle());
		}*/
	}
}
