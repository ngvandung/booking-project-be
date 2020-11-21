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
@Table(name = "house")
@Document(indexName = "house", type = "House")
public class House {
	@Id
	@org.springframework.data.annotation.Id
	private Long houseId;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "houseTypeId", nullable = false)
	private Long houseTypeId;
	@Column(name = "typeName", nullable = false)
	private String typeName;
	@Column(name = "stateId", nullable = false)
	private Long stateId;
	@Column(name = "stateName", nullable = false)
	private String stateName;
	@Column(name = "cityId", nullable = false)
	private Long cityId;
	@Column(name = "cityName", nullable = false)
	private String cityName;
	@Column(name = "districtId", nullable = false)
	private Long districtId;
	@Column(name = "districtName", nullable = false)
	private String districtName;
	@Column(name = "villageId", nullable = false)
	private Long villageId;
	@Column(name = "villageName", nullable = false)
	private String villageName;
	@Column(name = "linkGoogleMap", columnDefinition = "LONGTEXT", nullable = false)
	private String linkGoogleMap;
	@Column(name = "price", nullable = false)
	private Double price;
	@Column(name = "bedroom", nullable = false)
	private Integer bedroom;
	@Column(name = "livingroom", nullable = false)
	private Integer livingroom;
	@Column(name = "bathroom", nullable = false)
	private Integer bathroom;
	@Column(name = "maxGuest", nullable = false)
	private Integer maxGuest;
	@Column(name = "extensionCategoryDetailIds", nullable = true)
	private String extensionCategoryDetailIds;
	@Column(name = "description", columnDefinition = "LONGTEXT", nullable = true)
	private String description;
	@Column(name = "isActive", nullable = false)
	private Integer isActive;
	@Column(name = "ownerHouseId", nullable = false)
	private Long ownerHouseId;
	@Column(name = "createDate", nullable = false)
	private Date createDate;
	@Column(name = "modifiedDate", nullable = false)
	private Date modifiedDate;
	@Column(name = "userId", nullable = false)
	private Long userId;

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(Long houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Long getVillageId() {
		return villageId;
	}

	public void setVillageId(Long villageId) {
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getBedroom() {
		return bedroom;
	}

	public void setBedroom(Integer bedroom) {
		this.bedroom = bedroom;
	}

	public Integer getLivingroom() {
		return livingroom;
	}

	public void setLivingroom(Integer livingroom) {
		this.livingroom = livingroom;
	}

	public Integer getBathroom() {
		return bathroom;
	}

	public void setBathroom(Integer bathroom) {
		this.bathroom = bathroom;
	}

	public Integer getMaxGuest() {
		return maxGuest;
	}

	public void setMaxGuest(Integer maxGuest) {
		this.maxGuest = maxGuest;
	}

	public String getExtensionCategoryDetailIds() {
		return extensionCategoryDetailIds;
	}

	public void setExtensionCategoryDetailIds(String extensionCategoryDetailIds) {
		this.extensionCategoryDetailIds = extensionCategoryDetailIds;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Long getOwnerHouseId() {
		return ownerHouseId;
	}

	public void setOwnerHouseId(Long ownerHouseId) {
		this.ownerHouseId = ownerHouseId;
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
