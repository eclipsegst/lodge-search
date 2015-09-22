package com.example.reserve.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.reserve.domain.Location;

public interface LocationRepository extends CrudRepository<Location, Long> {

    List<Location> findByName(String name);
    
}