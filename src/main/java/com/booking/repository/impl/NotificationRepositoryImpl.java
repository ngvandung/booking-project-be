/**
 * 
 */
package com.booking.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.Notification;
import com.booking.repository.NotificationRepository;
import com.booking.util.HibernateUtil;

/**
 * @author ddung
 *
 */
@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Notification> getNotifications(String receiver, Integer start, Integer end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Notification findById(long notificationId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Notification notification = session.get(Notification.class, notificationId);
		transaction.commit();
		session.close();
		return notification;
	}

	@Override
	public Notification updateNotification(Notification notification) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(notification);
		transaction.commit();
		session.close();
		return notification;
	}

	@Override
	public Notification createNotification(Notification notification) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(notification);
		transaction.commit();
		session.close();
		return notification;
	}

	@Override
	public Notification deleteNotification(long notificationId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Notification notification = session.get(Notification.class, notificationId);
		session.delete(notification);
		transaction.commit();
		session.close();
		return notification;
	}

	@Override
	public List<Notification> findAll() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		List<Notification> data = HibernateUtil.loadAllData(Notification.class, session);
		transaction.commit();
		session.close();
		return data;
	}

}
