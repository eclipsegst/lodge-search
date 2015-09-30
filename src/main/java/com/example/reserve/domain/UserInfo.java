package com.example.reserve.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userinfo")
public class UserInfo {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long userid;
	private String name;
	private String phone;
	private String address;
	private String zipcode;
	private String email;
	
	public UserInfo(){};
	
	public UserInfo(
			Long userid, 
			String name,
			String phone,
			String address,
			String zipcode,
			String email) {
		this.userid = userid;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.zipcode = zipcode;
        this.email = email;
	}
	
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
