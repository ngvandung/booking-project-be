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
	@Column(name = "categoryId", nullable = false)
	private long categoryId;
	@Column(name = "homeTypeId", nullable = false)
	private long homeTypeId;
	@Column(name = "typeName", nullable = false)
	private String typeName;
	@Column(name = "stateId", nullable = false)
	private long stateId;
	@Column(name = "stateName", nullable = false)
	private String stateName;
	@Column(name = "cityId", nullable = false)
	private long cityId;
	@Column(name = "cityName", nullable = false)
	private String cityName;
	@Column(name = "districtId", nullable = false)
	private long districtId;
	@Column(name = "districtName", nullable = false)
	private String districtName;
	@Column(name = "villageId", nullable = false)
	private long villageId;
	@Column(name = "villageName", nullable = false)
	private String villageName;
	@Column(name = "linkGoogleMap", nullable = false)
	private String linkGoogleMap;
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

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getHomeTypeId() {
		return homeTypeId;
	}

	public void setHomeTypeId(long homeTypeId) {
		this.homeTypeId = homeTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public long getStateId() {
		return stateId;
	}

	public void setStateId(long stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public long getVillageId() {
		return villageId;
	}

	public void setVillageId(long villageId) {
		this.villageId = villageId;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getLinkGoogleMap() {
		return linkGoogleMap;
	}

	public void setLinkGoogleMap(String linkGoogleMap) {
		this.linkGoogleMap = linkGoogleMap;
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
