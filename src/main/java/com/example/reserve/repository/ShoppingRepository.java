package com.example.reserve.repository;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.reserve.domain.Shopping;

public interface ShoppingRepository extends CrudRepository<Shopping, Long> {

    List<Shopping> findByValid(boolean valid);
    
    @Query(
    		value="SELECT last_insert_id() from shopping LIMIT 1",
    		nativeQuery = true
    		)
    @Nonnull
    public long getLastInsertId();
}