package com.example.reserve.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "calendar")
public class Calendar {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private long fk;
	
	@Column(nullable = false)
	private String category;
	
	@Column(nullable = false)
	private Date closeddate;

	public Calendar() {}

    public Calendar(long fk, String category, Date closeddate) {
        this.fk = fk;
        this.category = category;
        this.closeddate = closeddate;
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

	public Date getCloseddate() {
		return closeddate;
	}

	public void setCloseddate(Date closeddate) {
		this.closeddate = closeddate;
	}
	
}
