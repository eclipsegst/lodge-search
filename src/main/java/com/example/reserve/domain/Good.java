package com.example.reserve.domain;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class Good {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private long fk;
	
	@Column(nullable = false)
	private String category;
	
	@Column(nullable = false)
	private long foodfk;
	
	@Column(nullable = false)
	private Date checkin;
	
	@Column(nullable = false)
    private Date checkout;
	
	@Column(nullable = false)
    private Integer adult;
	
	@Column(nullable = false)
    private Integer teenager;
	
	@Column(nullable = false)
    private Integer infant;

	@Column(nullable = false)
    private BigInteger payment;
	
	@Column(nullable = false)
    private Timestamp created;
	
	public Good() {}

    public Good(long fk, String category, long foodfk, Date checkin, Date checkout, int adult, int teenager, int infant, BigInteger payment, Timestamp created) {
        this.fk = fk;
        this.category = category;
        this.foodfk = foodfk;
        this.checkin = checkin;
        this.checkout = checkout;
        this.adult = adult;
        this.teenager = teenager;
        this.infant = infant;
        this.payment = payment;
        this.created = created;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getFk() {
		return fk;
	}
	
	public void setFk(long fk) {
		this.fk = fk;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getFoodfk() {
		return foodfk;
	}

	public void setFoodfk(long foodfk) {
		this.foodfk = foodfk;
	}

	public Date getCheckin() {
		return checkin;
	}

	public void setCheckin(Date checkin) {
		this.checkin = checkin;
	}

	public Date getCheckout() {
		return checkout;
	}

	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}

	public Integer getAdult() {
		return adult;
	}

	public void setAdult(Integer adult) {
		this.adult = adult;
	}

	public Integer getTeenager() {
		return teenager;
	}

	public void setTeenager(Integer teenager) {
		this.teenager = teenager;
	}

	public Integer getInfant() {
		return infant;
	}

	public void setInfant(Integer infant) {
		this.infant = infant;
	}

	public BigInteger getPayment() {
		return payment;
	}

	public void setPayment(BigInteger payment) {
		this.payment = payment;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}
}
