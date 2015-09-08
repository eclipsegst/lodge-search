package com.example.reserve.domain;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Lodge {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
    private String description;
	
	@Column(nullable = false)
    private String location;
    
    public Lodge() {}

    public Lodge(String name, String description, String location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    @Override
    public String toString() {
        return String.format(
                "Lodge[id=%d, name='%s', description='%s', location='%s']",
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
    
}
