package com.example.reserve.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart {
	
	private String email;
	private String name;
	
	private List<Cart> carts;
	
	public void shoppingCart(String email, String name, List<Cart> carts) {
		this.carts = carts;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}
	
	public void clearCarts() {
		this.carts = null;
	}
	
	public void remove(UUID uuid) {
		if (this.carts != null) {
			for(Cart cart : this.carts) {
				if (cart.getUuid().equals(uuid)) {
					this.carts.remove(cart);
					break;
				}
			}
		}
	}
	
}
