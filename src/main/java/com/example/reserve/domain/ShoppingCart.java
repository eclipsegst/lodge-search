package com.example.reserve.domain;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart {
	private List<Cart> cards;

	public void Good(List<Cart> cards) {
		this.cards = cards;
	}

	public List<Cart> getCards() {
		return cards;
	}

	public void setCards(List<Cart> cards) {
		this.cards = cards;
	}
	
}
