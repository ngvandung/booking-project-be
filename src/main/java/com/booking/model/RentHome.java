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
@Table(name = "rent_home")
@Document(indexName = "rent_home", type = "RentHome")
public class RentHome {
	@Id
	@org.springframework.data.annotation.Id
	private long rentId;
	@Column(name = "rentPeople", nullable = false)
	private int rentPeople;
	@Column(name = "fromDate", nullable = false)
	private Date fromDate;
	@Column(name = "toDate", nullable = false)
	private Date toDate;
	@Column(name = "homeId", nullable = false)
	private long homeId;
	@Column(name = "rentUserId", nullable = false)
	private long rentUserId;
	@Column(name = "isExpired", nullable = false)
	private int isExpired;
	@Column(name = "createDate", nullable = false)
	private Date createDate;
	@Column(name = "modifiedDate", nullable = false)
	private Date modifiedDate;
	@Column(name = "userId", nullable = false)
	private long userId;

	public long getRentId() {
		return rentId;
	}

	public void setRentId(long rentId) {
		this.rentId = rentId;
	}

	public int getRentPeople() {
		return rentPeople;
	}

	public void setRentPeople(int rentPeople) {
		this.rentPeople = rentPeople;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public long getHomeId() {
		return homeId;
	}

	public void setHomeId(long homeId) {
		this.homeId = homeId;
	}

	public long getRentUserId() {
		return rentUserId;
	}

	public void setRentUserId(long rentUserId) {
		this.rentUserId = rentUserId;
	}

	public int getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(int isExpired) {
		this.isExpired = isExpired;
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
