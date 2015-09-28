package com.example.reserve.service;

import java.util.List;
import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reserve.domain.Food;
import com.example.reserve.repository.FoodRepository;

@Service
public class FoodService {
	private final FoodRepository foodRepository;
	
	@Autowired
	public FoodService(
			@Nonnull final FoodRepository foodRepository
			) {
		this.foodRepository = foodRepository;
	}
	
	public final List<Food> findAll() {
		final List<Food> foods = (List<Food>) foodRepository.findAll();
		return foods;
	}
	
	public final Food findOne(Long foodId) {
		final Food food = foodRepository.findOne(foodId);
		return food;
	}
	
	public final List<Food> findByFkByCategory(Long fk, String category) {
		final List<Food> foods = (List<Food>) foodRepository.findByFkEqualsAndCategoryEquals(fk, category);
		return foods;
	}
	
	public final void save(Food food) {
		this.foodRepository.save(food);
	}
	
	public final void deleteFood(Long foodId) {
		final Food food = foodRepository.findOne(foodId);
		foodRepository.delete(food);
	}
}
