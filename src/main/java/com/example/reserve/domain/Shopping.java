package com.example.reserve.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Shopping {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private long userid;
	
	@Column(nullable = true)
	private BigDecimal payment;
	
	@Column(nullable = false)
	private boolean valid;
	
	@Column(nullable = false)
	private Timestamp datetime;

	public Shopping() {}

    public Shopping(long userid, BigDecimal payment, boolean valid, Timestamp datetime) {
    	this.userid = userid;
    	this.payment = payment;
    	this.valid = valid;
    	this.datetime = datetime;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Timestamp getDatetime() {
		return datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}
    
}
