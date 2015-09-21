package com.example.reserve.service;

import java.util.List;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.reserve.domain.Experience;

@Service
public final class ExperienceService {

	private final ExperienceRepository experienceRepository;
	
	@Autowired
	public ExperienceService(
			@Nonnull final ExperienceRepository experienceRepository
			) {
		this.experienceRepository = experienceRepository;
	}
	
	public final List<Experience> findAll() {
		final List<Experience> experiences = (List<Experience>) experienceRepository.findAll();
		return experiences;
	}
	
	public final Experience findOne(Long experienceId) {
		final Experience experience = experienceRepository.findOne(experienceId);
		return experience;
	}
	
	public final void save(Experience experience) {
		this.experienceRepository.save(experience);
	}
	
	public final void deleteExperience(Long experienceId) {
		final Experience experience = experienceRepository.findOne(experienceId);
		experienceRepository.delete(experience);
	}
	
	public final List<Experience> findByCapacity(String location, String category, int adult, int teenager, int infant) {
		final List<Experience> experiences = (List<Experience>) experienceRepository.findByCapacity(location, category, adult, teenager, infant);
		return experiences;
	}
}
