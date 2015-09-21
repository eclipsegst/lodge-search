package com.example.reserve.domain;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lodge")
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
	
	@Column(nullable = false)
    private String price;
	
	@Column(nullable = false)
    private String food;
	
	@Column(nullable = false)
    private String capacity;
	
	@Column(nullable = false)
    private String equipment;
	
	@Column(nullable = false)
    private String traffic;
	
	@Column(nullable = false)
    private String pickup;
	
	@Column(nullable = false)
    private Integer adult;
	
	@Column(nullable = false)
    private Integer teenager;
	
	@Column(nullable = false)
    private Integer infant;
    
    public Lodge() {}

    public Lodge(String name, String description, String location, String price, String food, String capacity, String equipment, String traffic, String pickup, Integer adult, Integer teenager, Integer infant) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.price = price;
        this.food = food;
        this.capacity = capacity;
        this.equipment = equipment;
        this.traffic = traffic;
        this.pickup = pickup;
        this.adult = adult;
        this.teenager = teenager;
        this.infant = infant;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getPickup() {
		return pickup;
	}

	public void setPickup(String pickup) {
		this.pickup = pickup;
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
}
