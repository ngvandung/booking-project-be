/**
 * 
 */
package com.booking.business;

import java.util.List;

import com.booking.model.Notification;

/**
 * @author ddung
 *
 */
public interface NotificationBusiness {
	public List<Notification> getNotifications(String receiver, Integer start, Integer end);

	public Notification findById(long notificationId);

	public Notification updateNotification(long notificationId, String content, String className, long classPK,
			String payload, String receiver);

	public Notification createNotification(String content, String className, long classPK, String payload,
			String receiver);

	public Notification deleteNotification(long notificationId);

	public List<Notification> findAll();
}
