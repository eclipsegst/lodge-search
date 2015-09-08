package com.example.reserve.domain;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Gallery {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private long fk;
	
	@Column(nullable = true)
	private String title;
	
	@Column(nullable = false)
	private String category;
	
	@Column(nullable = false)
	private boolean active;
	
	@Column(nullable = false)
    private Blob image;

	protected Gallery() {}

    public Gallery(long fk, String title, String category, boolean active, Blob image) {
        this.fk = fk;
    	this.title = title;
        this.image = image;
        this.active = active;
        this.category = category;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}
}
