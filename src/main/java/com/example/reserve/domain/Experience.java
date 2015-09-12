package com.example.reserve.domain;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
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
	
	@Column(nullable = false)
	private int adult;
	
	@Column(nullable = false)
	private int teenager;
	
	@Column(nullable = false)
	private int infant;
    
    public Experience() {}

    public Experience(String name, String description, String location, String course, String pickup, int adult, int teenager, int infant) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.course = course;
        this.pickup = pickup;
        this.adult = adult;
        this.teenager = teenager;
        this.infant = infant;
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

	public int getAdult() {
		return adult;
	}

	public void setAdult(int adult) {
		this.adult = adult;
	}

	public int getTeenager() {
		return teenager;
	}

	public void setTeenager(int teenager) {
		this.teenager = teenager;
	}

	public int getInfant() {
		return infant;
	}

	public void setInfant(int infant) {
		this.infant = infant;
	}
}
