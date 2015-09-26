package com.example.reserve.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.web.http.HttpSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.reserve.domain.Cart;
import com.example.reserve.domain.ShoppingCart;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
@Controller
@RequestMapping(value="/test")
public class TestController {
	
	@Autowired private ShoppingCart shoppingCart;
	
	@RequestMapping
	public String test() {
		
		List list = shoppingCart.getCarts();
		
		if (!list.isEmpty() && list != null) {
			System.out.println(list.size());
			for(int i = 0; i < list.size(); i++) {
				Cart good = (Cart) list.get(i);
				System.out.println(good.getFk() + ":" + good.getCategory());
				
			}
			Iterator iterater = list.iterator();
			if (iterater.hasNext()) {
				Cart good = (Cart) iterater.next();
				System.out.println(good.getFk() + ":" + good.getCategory());
			}
		}
		
		return "test";
	}
}
