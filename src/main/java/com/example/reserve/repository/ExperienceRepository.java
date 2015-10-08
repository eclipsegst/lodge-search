package com.example.reserve.repository;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.reserve.domain.Experience;

public interface ExperienceRepository extends CrudRepository<Experience, Long> {
	
    List<Experience> findByName(String name);
    
    @Query(
            "SELECT experience FROM Experience experience "
                + "WHERE experience.adult >= :adult "
                + "AND experience.teenager >= :teenager "
                + "AND experience.infant >= :infant "
                + "AND ((LOWER(experience.location) LIKE LOWER(CONCAT('%', :location, '%'))) OR (:location is null)) "
                + "AND ((LOWER(experience.category) LIKE LOWER(CONCAT('%', :category, '%'))) OR (:category is null)) "
//                + "AND (experience.location =:location OR experience.location = null) "
//                + "AND (experience.category =:category OR experience.category = null) "
                + "ORDER BY experience.name ASC"
    )
    @Nonnull
    public List<Experience> findExperienceByCriteria(
    		@Param("location") String location,
    		@Param("category") String category,
            @Param("adult") int adult,
            @Param("teenager") int teenager,
            @Param("infant") int infant
            
    );  
    
    @Query(
    		value = "SELECT * FROM Experience experience "
    				+ "ORDER BY experience.name DESC LIMIT 3",
            nativeQuery = true
    )
    @Nonnull
    public List<Experience> findThree();
}
