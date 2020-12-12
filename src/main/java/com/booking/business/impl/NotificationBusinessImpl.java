/**
 * 
 */
package com.booking.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.NotificationBusiness;
import com.booking.model.Notification;
import com.booking.service.NotificationService;

/**
 * @author ddung
 *
 */
public class NotificationBusinessImpl implements NotificationBusiness {

	@Autowired
	private NotificationService notificationService;

	@Override
	public List<Notification> getNotifications(String receiver, Integer start, Integer end) {
		return notificationService.getNotifications(receiver, start, end);
	}

	@Override
	public Notification findById(long notificationId) {
		return notificationService.findById(notificationId);
	}

	@Override
	public Notification updateNotification(long notificationId, String content, String className, long classPK,
			String payload, String receiver) {
		return notificationService.updateNotification(notificationId, content, className, classPK, payload, receiver);
	}

	@Override
	public Notification createNotification(String content, String className, long classPK, String payload,
			String receiver) {
		return notificationService.createNotification(content, className, classPK, payload, receiver);
	}

	@Override
	public Notification deleteNotification(long notificationId) {
		return notificationService.deleteNotification(notificationId);
	}

	@Override
	public List<Notification> findAll() {
		return notificationService.findAll();
	}

}
