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
	private Long homeId;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "homeTypeId", nullable = false)
	private Long homeTypeId;
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
	@Column(name = "linkGoogleMap", columnDefinition="LONGTEXT", nullable = false)
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
	@Column(name = "isWifi", nullable = true)
	private Integer isWifi;
	@Column(name = "isOven", nullable = true)
	private Integer isOven;
	@Column(name = "isAirConditioning", nullable = true)
	private Integer isAirConditioning;
	@Column(name = "isShampoo", nullable = true)
	private Integer isShampoo;
	@Column(name = "isTowels", nullable = true)
	private Integer isTowels;
	@Column(name = "isToothpaste", nullable = true)
	private Integer isToothpaste;
	@Column(name = "isSoap", nullable = true)
	private Integer isSoap;
	@Column(name = "isHairDryer", nullable = true)
	private Integer isHairDryer;
	@Column(name = "isMicroWave", nullable = true)
	private Integer isMicroWave;
	@Column(name = "isFridge", nullable = true)
	private Integer isFridge;
	@Column(name = "isBalcony", nullable = true)
	private Integer isBalcony;
	@Column(name = "isWindows", nullable = true)
	private Integer isWindows;
	@Column(name = "isSmartTv", nullable = true)
	private Integer isSmartTv;
	@Column(name = "isExtraMattress", nullable = true)
	private Integer isExtraMattress;
	@Column(name = "description", nullable = true)
	private String description;
	@Column(name = "isActive", nullable = false)
	private Integer isActive;
	@Column(name = "ownerHomeId", nullable = false)
	private Long ownerHomeId;
	@Column(name = "createDate", nullable = false)
	private Date createDate;
	@Column(name = "modifiedDate", nullable = false)
	private Date modifiedDate;
	@Column(name = "userId", nullable = false)
	private Long userId;

	public Long getHomeId() {
		return homeId;
	}

	public void setHomeId(Long homeId) {
		this.homeId = homeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getHomeTypeId() {
		return homeTypeId;
	}

	public void setHomeTypeId(Long homeTypeId) {
		this.homeTypeId = homeTypeId;
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

	public Integer getIsWifi() {
		return isWifi;
	}

	public void setIsWifi(Integer isWifi) {
		this.isWifi = isWifi;
	}

	public Integer getIsOven() {
		return isOven;
	}

	public void setIsOven(Integer isOven) {
		this.isOven = isOven;
	}

	public Integer getIsAirConditioning() {
		return isAirConditioning;
	}

	public void setIsAirConditioning(Integer isAirConditioning) {
		this.isAirConditioning = isAirConditioning;
	}

	public Integer getIsShampoo() {
		return isShampoo;
	}

	public void setIsShampoo(Integer isShampoo) {
		this.isShampoo = isShampoo;
	}

	public Integer getIsTowels() {
		return isTowels;
	}

	public void setIsTowels(Integer isTowels) {
		this.isTowels = isTowels;
	}

	public Integer getIsToothpaste() {
		return isToothpaste;
	}

	public void setIsToothpaste(Integer isToothpaste) {
		this.isToothpaste = isToothpaste;
	}

	public Integer getIsSoap() {
		return isSoap;
	}

	public void setIsSoap(Integer isSoap) {
		this.isSoap = isSoap;
	}

	public Integer getIsHairDryer() {
		return isHairDryer;
	}

	public void setIsHairDryer(Integer isHairDryer) {
		this.isHairDryer = isHairDryer;
	}

	public Integer getIsMicroWave() {
		return isMicroWave;
	}

	public void setIsMicroWave(Integer isMicroWave) {
		this.isMicroWave = isMicroWave;
	}

	public Integer getIsFridge() {
		return isFridge;
	}

	public void setIsFridge(Integer isFridge) {
		this.isFridge = isFridge;
	}

	public Integer getIsBalcony() {
		return isBalcony;
	}

	public void setIsBalcony(Integer isBalcony) {
		this.isBalcony = isBalcony;
	}

	public Integer getIsWindows() {
		return isWindows;
	}

	public void setIsWindows(Integer isWindows) {
		this.isWindows = isWindows;
	}

	public Integer getIsSmartTv() {
		return isSmartTv;
	}

	public void setIsSmartTv(Integer isSmartTv) {
		this.isSmartTv = isSmartTv;
	}

	public Integer getIsExtraMattress() {
		return isExtraMattress;
	}

	public void setIsExtraMattress(Integer isExtraMattress) {
		this.isExtraMattress = isExtraMattress;
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

	public Long getOwnerHomeId() {
		return ownerHomeId;
	}

	public void setOwnerHomeId(Long ownerHomeId) {
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
