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
@Table(name = "extension_category_detail")
public class ExtensionCategoryDetail {
	@Id
	private Long extensionCategoryDetailId;
	@Column(name = "extensionName", nullable = false)
	private String extensionName;
	@Column(name = "extensionCategoryId", nullable = false)
	private Long extensionCategoryId;
	@Column(name = "icon", columnDefinition = "LONGTEXT", nullable = false)
	private String icon;
	@Column(name = "isActive", nullable = false)
	private int isActive;
	@Column(name = "createDate", nullable = false)
	private Date createDate;
	@Column(name = "modifiedDate", nullable = false)
	private Date modifiedDate;
	@Column(name = "userId", nullable = false)
	private Long userId;

	public Long getExtensionCategoryDetailId() {
		return extensionCategoryDetailId;
	}

	public void setExtensionCategoryDetailId(Long extensionCategoryDetailId) {
		this.extensionCategoryDetailId = extensionCategoryDetailId;
	}

	public String getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public Long getExtensionCategoryId() {
		return extensionCategoryId;
	}

	public void setExtensionCategoryId(Long extensionCategoryId) {
		this.extensionCategoryId = extensionCategoryId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
