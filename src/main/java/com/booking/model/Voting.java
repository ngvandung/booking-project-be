/**
 * 
 */
package com.booking.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author ddung
 *
 */
@Entity
@Table(name = "voting")
@Document(indexName = "voting", type = "Voting")
public class Voting {
	@Id
	@org.springframework.data.annotation.Id
	private long votingId;
	@Column(name = "star", nullable = false)
	private int star;
	@Column(name = "resourceVotingId", nullable = false)
	private long resourceVotingId;
	@Column(name = "createDate", nullable = false)
	private Date createDate;
	@Column(name = "modifiedDate", nullable = false)
	private Date modifiedDate;
	@Column(name = "userId", nullable = false)
	private long userId;

	public long getVotingId() {
		return votingId;
	}

	public void setVotingId(long votingId) {
		this.votingId = votingId;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public long getResourceVotingId() {
		return resourceVotingId;
	}

	public void setResourceVotingId(long resourceVotingId) {
		this.resourceVotingId = resourceVotingId;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
