/**
 * 
 */
package com.booking.repository;

import java.util.List;

import com.booking.model.Notification;

/**
 * @author ddung
 *
 */
public interface NotificationRepository {

	public List<Notification> getNotifications(String receiver, Integer start, Integer end);

	public Notification findById(long notificationId);

	public Notification updateNotification(Notification notification);

	public Notification createNotification(Notification notification);

	public Notification deleteNotification(long notificationId);

	public List<Notification> findAll();
}
