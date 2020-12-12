/**
 * 
 */
package com.booking.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.model.Notification;
import com.booking.repository.NotificationRepository;
import com.booking.service.CounterService;
import com.booking.service.NotificationService;

/**
 * @author ddung
 *
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private CounterService counterService;

	@Override
	public List<Notification> getNotifications(String receiver, Integer start, Integer end) {
		return notificationRepository.getNotifications(receiver, start, end);
	}

	@Override
	public Notification findById(long notificationId) {
		return notificationRepository.findById(notificationId);
	}

	@Override
	public Notification updateNotification(long notificationId, String content, String className, long classPK,
			String payload, String receiver) {
		Notification notification = findById(notificationId);
		if (notification != null) {
			notification.setContent(content);
			notification.setClassName(className);
			notification.setClassPK(classPK);
			notification.setPayload(payload);
			notification.setReceiver(receiver);
			notification.setModifiedDate(new Date());

			return notification = notificationRepository.updateNotification(notification);
		}
		return null;
	}

	@Override
	public Notification createNotification(String content, String className, long classPK, String payload,
			String receiver) {

		long notificationId = counterService.increment(Notification.class.getName());

		Date now = new Date();
		Notification notification = new Notification();
		notification.setNotificationId(notificationId);
		notification.setContent(content);
		notification.setClassName(className);
		notification.setClassPK(classPK);
		notification.setPayload(payload);
		notification.setReceiver(receiver);
		notification.setModifiedDate(now);
		notification.setCreateDate(now);

		return notification = notificationRepository.updateNotification(notification);

	}

	@Override
	public Notification deleteNotification(long notificationId) {
		return notificationRepository.deleteNotification(notificationId);
	}

	@Override
	public List<Notification> findAll() {
		return notificationRepository.findAll();
	}

}
