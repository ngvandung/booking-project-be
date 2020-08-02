///**
// * 
// */
//package com.booking.model;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
///**
// * @author ddung
// *
// */
//@Entity
//@Table(name = "extension_detail")
//public class ExtensionDetail {
//	@Id
//	private Long extensionDetailId;
//	@Column(name = "extensionName", nullable = false)
//	private String extensionName;
//	@Column(name = "extensionHouseCategoryId", nullable = false)
//	private Long extensionHouseCategoryId;
//	@Column(name = "icon", columnDefinition = "LONGTEXT", nullable = false)
//	private String icon;
//	@Column(name = "createDate", nullable = false)
//	private Date createDate;
//	@Column(name = "modifiedDate", nullable = false)
//	private Date modifiedDate;
//	@Column(name = "userId", nullable = false)
//	private Long userId;
//
//	public Long getExtensionDetailId() {
//		return extensionDetailId;
//	}
//
//	public void setExtensionDetailId(Long extensionDetailId) {
//		this.extensionDetailId = extensionDetailId;
//	}
//
//	public String getExtensionName() {
//		return extensionName;
//	}
//
//	public void setExtensionName(String extensionName) {
//		this.extensionName = extensionName;
//	}
//
//	public Long getExtensionHouseCategoryId() {
//		return extensionHouseCategoryId;
//	}
//
//	public void setExtensionHouseCategoryId(Long extensionHouseCategoryId) {
//		this.extensionHouseCategoryId = extensionHouseCategoryId;
//	}
//
//	public Date getCreateDate() {
//		return createDate;
//	}
//
//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}
//
//	public Date getModifiedDate() {
//		return modifiedDate;
//	}
//
//	public void setModifiedDate(Date modifiedDate) {
//		this.modifiedDate = modifiedDate;
//	}
//
//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//
//}
