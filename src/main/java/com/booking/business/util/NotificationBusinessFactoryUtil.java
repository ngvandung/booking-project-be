/**
 * 
 */
package com.booking.business.util;

import java.util.List;

import com.booking.business.NotificationBusiness;
import com.booking.model.Notification;
import com.booking.util.BeanUtil;

/**
 * @author ddung
 *
 */
public class NotificationBusinessFactoryUtil {

	private static NotificationBusiness _notificationBusiness;

	public static NotificationBusiness getNotificationBusiness() {

		if (_notificationBusiness == null) {
			_notificationBusiness = BeanUtil.getBean(NotificationBusiness.class);
		}
		return _notificationBusiness;
	}

	public static List<Notification> getNotifications(String receiver, Integer start, Integer end) {
		return getNotificationBusiness().getNotifications(receiver, start, end);
	}

	public static Notification findById(long notificationId) {
		return getNotificationBusiness().findById(notificationId);
	}

	public static Notification updateNotification(long notificationId, String content, String className, long classPK,
			String payload, String receiver) {
		return getNotificationBusiness().updateNotification(notificationId, content, className, classPK, payload,
				receiver);
	}

	public static Notification createNotification(String content, String className, long classPK, String payload,
			String receiver) {
		return getNotificationBusiness().createNotification(content, className, classPK, payload, receiver);
	}

	public static Notification deleteNotification(long notificationId) {
		return getNotificationBusiness().deleteNotification(notificationId);
	}

	public static List<Notification> findAll() {
		return getNotificationBusiness().findAll();
	}

}
