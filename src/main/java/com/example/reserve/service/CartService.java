package com.example.reserve.service;

import java.util.List;
import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reserve.domain.Cart;
import com.example.reserve.repository.CartRepository;

@Service
public class CartService {
	private final CartRepository cartRepository;
	
	@Autowired
	public CartService(
			@Nonnull final CartRepository cartRepository
			) {
		this.cartRepository = cartRepository;
	}
	
	public final List<Cart> findAll() {
		final List<Cart> carts = (List<Cart>) cartRepository.findAll();
		return carts;
	}
	
	public final Cart findOne(Long cartId) {
		final Cart cart = cartRepository.findOne(cartId);
		return cart;
	}
	
	public final List<Cart> findByShoppingid(Long shoppingid) {
		final List<Cart> carts = (List<Cart>) cartRepository.findByShoppingid(shoppingid);
		return carts;
	}
	
	public final void save(Cart cart) {
		this.cartRepository.save(cart);
	}
	
	public final void deleteCart(Long cartId) {
		final Cart cart = cartRepository.findOne(cartId);
		cartRepository.delete(cart);
	}
}
