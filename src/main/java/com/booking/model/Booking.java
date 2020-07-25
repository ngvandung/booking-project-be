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
@Table(name = "booking")
@Document(indexName = "booking", type = "Booking")
public class Booking {
	@Id
	@org.springframework.data.annotation.Id
	private Long bookingId;
	@Column(name = "numberOfGuest", nullable = false)
	private Integer numberOfGuest;
	@Column(name = "fromDate", nullable = false)
	private Date fromDate;
	@Column(name = "toDate", nullable = false)
	private Date toDate;
	@Column(name = "classPK", nullable = false)
	private Long classPK;
	@Column(name = "className", nullable = false)
	private String className;
	@Column(name = "totalAmount", nullable = false)
	private Double totalAmount;
	@Column(name = "bookingStatus", nullable = false)
	private String bookingStatus;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "phone", nullable = false)
	private String phone;
	@Column(name = "fullName", nullable = false)
	private String fullName;
	@Column(name = "stateId", nullable = false)
	private Long stateId;
	@Column(name = "stateName", nullable = false)
	private String stateName;
	@Column(name = "ipAddress", nullable = false)
	private String ipAddress;
	@Column(name = "qrCode", columnDefinition = "LONGTEXT", nullable = true)
	private String qrCode;
	@Column(name = "createDate", nullable = false)
	private Date createDate;
	@Column(name = "modifiedDate", nullable = false)
	private Date modifiedDate;
	@Column(name = "userId", nullable = false)
	private Long userId;

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Integer getNumberOfGuest() {
		return numberOfGuest;
	}

	public void setNumberOfGuest(Integer numberOfGuest) {
		this.numberOfGuest = numberOfGuest;
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

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
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
