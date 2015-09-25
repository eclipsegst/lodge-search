package com.example.reserve.domain;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart {
	private List<Cart> goods;

	public void Good(List<Cart> goods) {
		this.goods = goods;
	}
	
	public List<Cart> getGoods() {
		return goods;
	}

	public void setGoods(List<Cart> goods) {
		this.goods = goods;
	}
}
