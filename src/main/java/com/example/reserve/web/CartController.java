package com.example.reserve.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.reserve.domain.Cart;
import com.example.reserve.domain.ShoppingCart;
import com.example.reserve.service.CartService;

@Controller
public class CartController{
	@Autowired
	private final CartService cartService;

	@Autowired 
	private ShoppingCart shoppingCart;
	
	protected long fk = -1L;
	protected String category;
	
	@Autowired
	public CartController(
			@Nonnull final CartService cartService
			) {
		this.cartService = cartService;
	}
	
	@RequestMapping(value="/cart")
	public String test2() {
		
		List list = shoppingCart.getCards();
		
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
//	public String cart(Model model) {
//		List<Cart> carts = new ArrayList<Cart>();
//		carts = shoppingCart.getCards();
//		model.addAttribute("carts", carts);
//		if (carts != null) {
//			System.out.println("cart size:" + carts.size());
//		} else {
//			System.out.println("cart empty");
//		}
//		
//		
//		List list = shoppingCart.getCards();
//		
//		if (!list.isEmpty() && list != null) {
//			System.out.println(list.size());
//			for(int i = 0; i < list.size(); i++) {
//				Cart good = (Cart) list.get(i);
//				System.out.println(good.getFk() + ":" + good.getCategory());
//				
//			}
//			Iterator iterater = list.iterator();
//			if (iterater.hasNext()) {
//				Cart good = (Cart) iterater.next();
//				System.out.println(good.getFk() + ":" + good.getCategory());
//			}
//		}
//		
//		return "cart";
//	}
	
	@RequestMapping(value="/cart/update")
	@Transactional(readOnly = false)
	public String update(
			@Nonnull @RequestParam(value = "id", required = true) final long cartId,
			@Nonnull @RequestParam(value = "action", required = true) final String action,
			Model model) {
		
		this.fk = cartId;
		model.addAttribute("fk", this.fk);
		
		if (action.equalsIgnoreCase("update")) {
			return updateForm(cartId, model);
		} else if (action.equalsIgnoreCase("delete")) {
			cartService.deleteCart(cartId);
		}
		
		List<Cart> carts = cartService.findAll();
		model.addAttribute("carts", carts);
		return "cart";
	}
	
//	Load cart to update
	@RequestMapping(value="cart/updated", method=RequestMethod.GET)
    public String updateForm(
    		@Nonnull @RequestParam(value = "id", required = true) final long cartId,
    		Model model) {
		Cart cart = cartService.findOne(cartId);
		
		this.fk = cartId;
		model.addAttribute("fk", this.fk);
		
        model.addAttribute("cart", cart);
        return "update-cart";
    }
	
	@RequestMapping(value="/cart/updated", method=RequestMethod.POST)
    public String updateSubmit(@ModelAttribute Cart cart, Model model) {
        model.addAttribute("cart", cart);
        
        cartService.save(cart);
        
        List<Cart> carts = cartService.findAll();
		model.addAttribute("carts", carts);
		return "cart";
    }
	
//	Add new cart
	@RequestMapping(value="/cart/new", method=RequestMethod.GET)
	public String image(
			@Nonnull @RequestParam(value = "fk", required = true) final long fk,
			@Nonnull @RequestParam(value = "category", required = true) final String category,
			Model model
			) {
		model.addAttribute("cart", new Cart());
		model.addAttribute("fk", fk);
		model.addAttribute("category", category);
		this.fk = fk;
		this.category = category;
		return "new-cart";
	}
	
	@RequestMapping(value="/cart/new", method=RequestMethod.POST)
    public String cartSubmit(@ModelAttribute Cart cart, Model model) {
        model.addAttribute("cart", cart);
        
        cart.setFk(fk);
        cart.setCategory(category);
        cartService.save(cart);
        
        return "new-cart-result";
    }

}
