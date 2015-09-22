package com.example.reserve.domain;

import javax.persistence.Entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "experience")
public class Experience {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = true)
    private String description;
	
	@Column(nullable = true)
    private String location;
	
	@Column(nullable = true)
	private String course;
	
	@Column(nullable = true)
	private String pickup;
	
	@Column(nullable = true)
	private String category;

	@Column(nullable = false)
	private Integer adult;
	
	@Column(nullable = false)
	private Integer teenager;
	
	@Column(nullable = false)
	private Integer infant;
    
	@Column(nullable = false)
	private BigDecimal latitude;

	@Column(nullable = false)
	private BigDecimal longitude;
	
    public Experience() {}

    public Experience(String name, String description, String location, String course, String pickup, String category, Integer adult, Integer teenager, Integer infant, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.course = course;
        this.pickup = pickup;
        this.category = category;
        this.adult = adult;
        this.teenager = teenager;
        this.infant = infant;
        this.latitude = latitude;
        this.longitude = longitude;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getPickup() {
		return pickup;
	}

	public void setPickup(String pickup) {
		this.pickup = pickup;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
	
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
}
