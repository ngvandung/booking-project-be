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
	private Long votingId;
	@Column(name = "star", nullable = false)
	private Integer star;
	@Column(name = "classPK", nullable = false)
	private Long classPK;
	@Column(name = "className", nullable = false)
	private String className;
	@Column(name = "createDate", nullable = false)
	private Date createDate;
	@Column(name = "modifiedDate", nullable = false)
	private Date modifiedDate;
	@Column(name = "userId", nullable = false)
	private Long userId;

	public Long getVotingId() {
		return votingId;
	}

	public void setVotingId(Long votingId) {
		this.votingId = votingId;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Long getClassPK() {
		return classPK;
	}

	public void setClassPK(Long classPK) {
		this.classPK = classPK;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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
