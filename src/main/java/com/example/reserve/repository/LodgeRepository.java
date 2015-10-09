package com.example.reserve.repository;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.reserve.domain.Lodge;

public interface LodgeRepository extends CrudRepository<Lodge, Long> {

    List<Lodge> findByName(String name);
    
    @Query(
            "SELECT lodge FROM Lodge lodge "
                + "WHERE ((lodge.adult >= :adult) OR (lodge.adult is null)) "
                + "AND ((lodge.teenager >= :teenager) OR (lodge.teenager is null)) "
                + "AND ((lodge.infant >= :infant) OR (lodge.infant is null)) "
                + "AND ((LOWER(lodge.location) LIKE LOWER(CONCAT('%', :location, '%'))) OR (:location is null)) "
                + "ORDER BY lodge.name ASC"
    )
    @Nonnull
    public List<Lodge> findLodgeByCriteria(
    		@Param("location") String location,
            @Param("adult") int adult,
            @Param("teenager") int teenager,
            @Param("infant") int infant
            
    );
    
    @Query(
    		value = "SELECT * FROM lodge lodge "
    				+ "ORDER BY lodge.name DESC LIMIT 3",
            nativeQuery = true
    )
    @Nonnull
    public List<Lodge> findThree();
}
