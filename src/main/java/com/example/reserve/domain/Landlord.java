package com.example.reserve.domain;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Landlord {
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
    private String email;
	
	@Column(nullable = true)
    private int lodgenumber;
	
	@Column(nullable = true)
    private int experiencenumber;
    
    public Landlord() {}

    public Landlord(String name, String description, String location, String email, int lodgenumber, int experiencenumber) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.email = email;
        this.lodgenumber = lodgenumber;
        this.experiencenumber = experiencenumber;
    }

    @Override
    public String toString() {
        return String.format(
                "Landlord[id=%d, name='%s', description='%s', location='%s']",
                id, name, description, location);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLodgenumber() {
		return lodgenumber;
	}

	public void setLodgenumber(int lodgenumber) {
		this.lodgenumber = lodgenumber;
	}

	public int getExperiencenumber() {
		return experiencenumber;
	}

	public void setExperiencenumber(int experiencenumber) {
		this.experiencenumber = experiencenumber;
	}
}
