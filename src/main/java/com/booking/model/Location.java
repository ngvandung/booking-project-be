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
@Table(name = "location")
@Document(indexName = "location", type = "Location")
public class Location {
	@Id
	@org.springframework.data.annotation.Id
	private long locationId;
	@Column(name = "stateId", nullable = false)
	private long stateId;
	@Column(name = "stateName", nullable = false)
	private long stateName;
	@Column(name = "cityId", nullable = false)
	private long cityId;
	@Column(name = "cityName", nullable = false)
	private long cityName;
	@Column(name = "districtId", nullable = false)
	private long districtId;
	@Column(name = "districtName", nullable = false)
	private long districtName;
	@Column(name = "villageId", nullable = false)
	private long villageId;
	@Column(name = "villageName", nullable = false)
	private long villageName;
	@Column(name = "linkGoogleMap", nullable = false)
	private String linkGoogleMap;
	@Column(name = "isActive", nullable = false)
	private int isActive;
	@Column(name = "createDate", nullable = false)
	private Date createDate;
	@Column(name = "modifiedDate", nullable = false)
	private Date modifiedDate;
	@Column(name = "userId", nullable = false)
	private long userId;

	public long getStateName() {
		return stateName;
	}

	public void setStateName(long stateName) {
		this.stateName = stateName;
	}

	public long getCityName() {
		return cityName;
	}

	public void setCityName(long cityName) {
		this.cityName = cityName;
	}

	public long getDistrictName() {
		return districtName;
	}

	public void setDistrictName(long districtName) {
		this.districtName = districtName;
	}

	public long getVillageName() {
		return villageName;
	}

	public void setVillageName(long villageName) {
		this.villageName = villageName;
	}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public long getStateId() {
		return stateId;
	}

	public void setStateId(long stateId) {
		this.stateId = stateId;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}

	public long getVillageId() {
		return villageId;
	}

	public void setVillageId(long villageId) {
		this.villageId = villageId;
	}

	public String getLinkGoogleMap() {
		return linkGoogleMap;
	}

	public void setLinkGoogleMap(String linkGoogleMap) {
		this.linkGoogleMap = linkGoogleMap;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
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
