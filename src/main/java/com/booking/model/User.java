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
@Table(name = "user")
@Document(indexName = "user", type = "User")
public class User {
	@Id
	@org.springframework.data.annotation.Id
	private long userId;
	@Column(name = "username", nullable = false)
	private String username;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "email", nullable = true)
	private String email;
	@Column(name = "phone", nullable = true)
	private String phone;
	@Column(name = "firstName", nullable = false)
	private String firstName;
	@Column(name = "lastName", nullable = false)
	private String lastName;
	@Column(name = "age", nullable = true)
	private int age;
	@Column(name = "address", nullable = true)
	private String address;
	@Column(name = "birthDay", nullable = true)
	private Date birthDay;
	@Column(name = "description", nullable = true)
	private String description;
	@Column(name = "isHost", nullable = false)
	private int isHost;
	@Column(name = "isEnabled", nullable = false)
	private int isEnabled;
	@Column(name = "tmnCode", nullable = true)
	private String tmnCode;
	@Column(name = "hashSecret", nullable = true)
	private String hashSecret;
	@Column(name = "createDate", nullable = false)
	private Date createDate;
	@Column(name = "modifiedDate", nullable = false)
	private Date modifiedDate;

	public String getTmnCode() {
		return tmnCode;
	}

	public void setTmnCode(String tmnCode) {
		this.tmnCode = tmnCode;
	}

	public String getHashSecret() {
		return hashSecret;
	}

	public void setHashSecret(String hashSecret) {
		this.hashSecret = hashSecret;
	}

	public int getIsHost() {
		return isHost;
	}

	public void setIsHost(int isHost) {
		this.isHost = isHost;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(int isEnabled) {
		this.isEnabled = isEnabled;
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
}
