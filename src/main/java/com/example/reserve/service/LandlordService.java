package com.example.reserve.service;

import java.util.List;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.reserve.domain.Landlord;

@Service
public final class LandlordService {
	private final LandlordRepository landlordRepository;
	
	@Autowired
	public LandlordService(
			@Nonnull final LandlordRepository landlordRepository
			) {
		this.landlordRepository = landlordRepository;
	}
	
	public final List<Landlord> findAll() {
		final List<Landlord> landlords = (List<Landlord>) landlordRepository.findAll();
		return landlords;
	}
	
	public final Landlord findOne(Long landlordId) {
		final Landlord landlord = landlordRepository.findOne(landlordId);
		return landlord;
	}
	
	public final void save(Landlord landlord) {
		this.landlordRepository.save(landlord);
	}
	
	public final void deleteLandlord(Long landlordId) {
		final Landlord landlord = landlordRepository.findOne(landlordId);
		landlordRepository.delete(landlord);
	}
}
