package com.example.reserve.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.reserve.domain.Food;

public interface FoodRepository extends CrudRepository<Food, Long> {

    List<Food> findByTitle(String title);
    
    List<Food> findByFkEqualsAndCategoryEquals(long fk, String categroy);
}