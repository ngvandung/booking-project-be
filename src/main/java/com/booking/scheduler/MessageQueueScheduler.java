/**
 * 
 */
package com.booking.scheduler;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.booking.constant.SystemConstant;
import com.booking.model.MessageQueue;
import com.booking.service.MessageQueueService;
import com.booking.util.DateFormat;
import com.booking.util.EmailSender;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ddung
 *
 */
@Configuration
@EnableScheduling
public class MessageQueueScheduler {
	private static final Logger _log = Logger.getLogger(BookingScheduler.class);

	@Autowired
	private MessageQueueService messageQueueService;

	public MessageQueueScheduler() {
	}

	public MessageQueueScheduler(MessageQueueService messageQueueService) {
		super();
		this.messageQueueService = messageQueueService;
	}

	public MessageQueueService getMessageQueueService() {
		return messageQueueService;
	}

	public void setMessageQueueService(MessageQueueService messageQueueService) {
		this.messageQueueService = messageQueueService;
	}

	@Scheduled(fixedRate = 30000)
	public void messageQueueScheduler() {
		Date now = new Date();
		_log.info("Message Queue Scheduler: " + DateFormat.formatDateToString_ddMMyyyy_HHmmss(now));

		List<MessageQueue> messageQueues = messageQueueService.findMessageQueues(SystemConstant.PENDING);
		messageQueues.parallelStream().filter(message -> message.getRetry() <= 5).forEach(message -> {
			if (message.getTypeQueue().equals("email")) {
				String payload = message.getPayload();
				ObjectMapper mapper = new ObjectMapper();
				try {
					JsonNode jPayload = mapper.readValue(payload, JsonNode.class);
					boolean hasBoolean = EmailSender.sendEmail(
							String.valueOf(jPayload.get("toEmail")).replace("\"", "").trim(),
							String.valueOf(jPayload.get("homeName")).replace("\"", "").trim(),
							String.valueOf(jPayload.get("pathQRCode")).replace("\"", "").trim());

					if (hasBoolean) {
						message.setModifiedDate(now);
						message.setState(SystemConstant.DONE);
					} else {
						message.setModifiedDate(now);
						int curRetry = message.getRetry();
						message.setRetry(++curRetry);
					}
					messageQueueService.updateMessageQueue(message);
				} catch (Exception e) {
				}
			}
		});
	}
}
