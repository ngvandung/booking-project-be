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
@Table(name = "home")
@Document(indexName = "home", type = "Home")
public class Home {
	@Id
	@org.springframework.data.annotation.Id
	private long homeId;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "homeTypeId", nullable = false)
	private long homeTypeId;
	@Column(name = "locationId", nullable = false)
	private long locationId;
	@Column(name = "price", nullable = false)
	private double price;
	@Column(name = "countBedroom", nullable = false)
	private int countBedroom;
	@Column(name = "countLivingroom", nullable = false)
	private int countLivingroom;
	@Column(name = "countBathroom", nullable = false)
	private int countBathroom;
	@Column(name = "countPeople", nullable = false)
	private int countPeople;
	@Column(name = "description", nullable = false)
	private String description;
	@Column(name = "isActive", nullable = false)
	private int isActive;
	@Column(name = "ownerHomeId", nullable = false)
	private long ownerHomeId;
	@Column(name = "createDate", nullable = false)
	private Date createDate;
	@Column(name = "modifiedDate", nullable = false)
	private Date modifiedDate;
	@Column(name = "userId", nullable = false)
	private long userId;

	public long getHomeId() {
		return homeId;
	}

	public void setHomeId(long homeId) {
		this.homeId = homeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getHomeTypeId() {
		return homeTypeId;
	}

	public void setHomeTypeId(long homeTypeId) {
		this.homeTypeId = homeTypeId;
	}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCountBedroom() {
		return countBedroom;
	}

	public void setCountBedroom(int countBedroom) {
		this.countBedroom = countBedroom;
	}

	public int getCountLivingroom() {
		return countLivingroom;
	}

	public void setCountLivingroom(int countLivingroom) {
		this.countLivingroom = countLivingroom;
	}

	public int getCountBathroom() {
		return countBathroom;
	}

	public void setCountBathroom(int countBathroom) {
		this.countBathroom = countBathroom;
	}

	public int getCountPeople() {
		return countPeople;
	}

	public void setCountPeople(int countPeople) {
		this.countPeople = countPeople;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public long getOwnerHomeId() {
		return ownerHomeId;
	}

	public void setOwnerHomeId(long ownerHomeId) {
		this.ownerHomeId = ownerHomeId;
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
