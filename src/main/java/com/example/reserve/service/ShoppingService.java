package com.example.reserve.service;

import java.util.List;
import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reserve.domain.Shopping;

@Service
public class ShoppingService {
	private final ShoppingRepository shoppingRepository;
	
	@Autowired
	public ShoppingService(
			@Nonnull final ShoppingRepository shoppingRepository
			) {
		this.shoppingRepository = shoppingRepository;
	}
	
	public final List<Shopping> findAll() {
		final List<Shopping> shoppings = (List<Shopping>) shoppingRepository.findAll();
		return shoppings;
	}
	
	public final Shopping findOne(Long shoppingId) {
		final Shopping shopping = shoppingRepository.findOne(shoppingId);
		return shopping;
	}
	
	public final List<Shopping> findByValid(boolean valid) {
		final List<Shopping> shoppings = (List<Shopping>) shoppingRepository.findByValid(valid);
		return shoppings;
	}
	
	public final void save(Shopping shopping) {
		this.shoppingRepository.save(shopping);
	}
	
	public final void deleteShopping(Long shoppingId) {
		final Shopping shopping = shoppingRepository.findOne(shoppingId);
		shoppingRepository.delete(shopping);
	}
	
	public final long getLastInsertId(){
		return this.shoppingRepository.getLastInsertId();
	}
	
}
