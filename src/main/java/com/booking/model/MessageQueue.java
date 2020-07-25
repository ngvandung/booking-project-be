/**
 * 
 */
package com.booking.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ddung
 *
 */
@Entity
@Table(name = "message_queue")
public class MessageQueue {
	@Id
	private Long messageQueueId;
	@Column(name = "typeQueue", nullable = false)
	private String typeQueue;
	@Column(name = "className", nullable = true)
	private String className;
	@Column(name = "classPK", nullable = true)
	private Long classPK;
	@Column(name = "payload", columnDefinition = "LONGTEXT", nullable = false)
	private String payload;
	@Column(name = "retry", nullable = false)
	private Integer retry;
	@Column(name = "state", nullable = false)
	private Integer state;
	@Column(name = "createDate", nullable = false)
	private Date createDate;
	@Column(name = "modifiedDate", nullable = false)
	private Date modifiedDate;
	@Column(name = "userId", nullable = true)
	private Long userId;

	public Long getMessageQueueId() {
		return messageQueueId;
	}

	public void setMessageQueueId(Long messageQueueId) {
		this.messageQueueId = messageQueueId;
	}

	public String getTypeQueue() {
		return typeQueue;
	}

	public void setTypeQueue(String typeQueue) {
		this.typeQueue = typeQueue;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getClassPK() {
		return classPK;
	}

	public void setClassPK(Long classPK) {
		this.classPK = classPK;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Integer getRetry() {
		return retry;
	}

	public void setRetry(Integer retry) {
		this.retry = retry;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
