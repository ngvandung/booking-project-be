/**
 * 
 */
package com.booking.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.User;
import com.booking.repository.UserDetailsRepository;

/**
 * @author ddung
 *
 */
@Repository("UserDetailsRepository")
public class UserDetailsRepositoryImpl implements UserDetailsRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public User findUserByUsername(String username) {
		Transaction transaction = null;
		User user = null;
		try {
			Session session = sessionFactory.getSessionFactory().openSession();
			if (session != null) {
				// start a transaction
				transaction = session.beginTransaction();

				// get an student object
				String hql = " FROM User S WHERE S.username = :username";
				Query query = session.createQuery(hql);
				query.setParameter("username", username);
				List<User> users = query.getResultList();

				if (users != null && !users.isEmpty()) {
					user = users.get(0);
				}

				// commit transaction
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return user;
	}

}
