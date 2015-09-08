package com.example.reserve.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.reserve.domain.Gallery;

public interface GalleryRepository extends CrudRepository<Gallery, Long> {

    List<Gallery> findByTitle(String title);
    
    List<Gallery> findByFkEqualsAndCategoryEquals(long fk, String categroy);
}