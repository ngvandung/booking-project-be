/**
 * 
 */
package com.booking.repository;

import java.util.List;

import com.booking.model.MessageQueue;

/**
 * @author ddung
 *
 */
public interface MessageQueueRepository {
	public MessageQueue findById(long messageQueueId);

	public List<MessageQueue> findMessageQueues(Integer state);

	public MessageQueue createMessageQueue(MessageQueue messageQueue);
	
	public MessageQueue updateMessageQueue(MessageQueue messageQueue);
}
