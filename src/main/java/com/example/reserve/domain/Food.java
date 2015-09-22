package com.example.reserve.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Food {
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
	private Integer adult;
	
	@Column(nullable = false)
    private Integer teenager;

	public Food() {}

    public Food(long fk, String title, String category, int adult, int teenager) {
        this.fk = fk;
    	this.title = title;
        this.category = category;
        this.adult = adult;
        this.teenager = teenager;
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

	
}
