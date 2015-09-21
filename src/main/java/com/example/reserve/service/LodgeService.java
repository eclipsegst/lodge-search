package com.example.reserve.service;

import java.util.List;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reserve.domain.Lodge;

@Service
public final class LodgeService {
	private final LodgeRepository lodgeRepository;
	
	@Autowired
	public LodgeService(
			@Nonnull final LodgeRepository lodgeRepository
			) {
		this.lodgeRepository = lodgeRepository;
	}
	
	public final List<Lodge> findAll() {
		final List<Lodge> lodges = (List<Lodge>) lodgeRepository.findAll();
		return lodges;
	}
	
	public final Lodge findOne(Long lodgeId) {
		final Lodge lodge = lodgeRepository.findOne(lodgeId);
		return lodge;
	}
	
	public final void save(Lodge lodge) {
		this.lodgeRepository.save(lodge);
	}
	
	public final void deleteLodge(Long lodgeId) {
		final Lodge lodge = lodgeRepository.findOne(lodgeId);
		lodgeRepository.delete(lodge);
	}
	
	public final List<Lodge> findLodgeByCriteria(String location, int adult, int teenager, int infant) {
		final List<Lodge> lodges = (List<Lodge>) lodgeRepository.findLodgeByCriteria(location, adult, teenager, infant);
		return lodges;
	}
}
