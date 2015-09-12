package com.example.reserve.service;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.reserve.domain.Experience;

public interface ExperienceRepository extends CrudRepository<Experience, Long> {
	
    List<Experience> findByName(String name);
}
