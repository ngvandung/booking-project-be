/**
 * 
 */
package com.booking.service;

import java.util.List;

import com.booking.model.MessageQueue;

/**
 * @author ddung
 *
 */
public interface MessageQueueService {
	public MessageQueue findById(long messageQueueId);

	public List<MessageQueue> findMessageQueues(Integer state);

	public MessageQueue createMessageQueue(String className, Long classPK, String typeQueue, String payload);
	
	public MessageQueue updateMessageQueue(MessageQueue messageQueue);
}
