package com.example.reserve.service;

import java.util.List;
import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reserve.domain.Location;

@Service
public class LocationService {
	private final LocationRepository locationRepository;
	
	@Autowired
	public LocationService(
			@Nonnull final LocationRepository locationRepository
			) {
		this.locationRepository = locationRepository;
	}
	
	public final List<Location> findAll() {
		final List<Location> locations = (List<Location>) locationRepository.findAll();
		return locations;
	}
	
	public final Location findOne(Long locationId) {
		final Location location = locationRepository.findOne(locationId);
		return location;
	}
	
	public final void save(Location location) {
		this.locationRepository.save(location);
	}
	
	public final void deleteLocation(Long locationId) {
		final Location location = locationRepository.findOne(locationId);
		locationRepository.delete(location);
	}
}
