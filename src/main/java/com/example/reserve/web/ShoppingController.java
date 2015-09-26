package com.example.reserve.web;

import java.util.Date;
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
import com.example.reserve.domain.Shopping;
import com.example.reserve.service.CartService;
import com.example.reserve.service.ShoppingService;

@Controller
public class ShoppingController{
	@Autowired
	private final ShoppingService shoppingService;
	
	@Autowired
	private final CartService cartService;

	protected long fk = -1L
			;
	@Autowired
	public ShoppingController(
			@Nonnull final ShoppingService shoppingService,
			@Nonnull final CartService cartService
			) {
		this.shoppingService = shoppingService;
		this.cartService = cartService;
	}
	
	@RequestMapping(value="/shopping")
	@Transactional(readOnly = true)
	public String shopping(Model model) {
		List<Shopping> shoppings = shoppingService.findAll();
		model.addAttribute("shoppings", shoppings);
		return "shopping";
	}
	
	@RequestMapping(value="/shopping/update")
	@Transactional(readOnly = false)
	public String update(
			@Nonnull @RequestParam(value = "id", required = true) final long shoppingId,
			@Nonnull @RequestParam(value = "action", required = true) final String action,
			Model model) {
		
		this.fk = shoppingId;
		model.addAttribute("fk", this.fk);
		
		if (action.equalsIgnoreCase("update")) {
			return updateForm(shoppingId, model);
		} else if (action.equalsIgnoreCase("delete")) {
			List<Cart> carts = cartService.findByShoppingid(shoppingId);
			for(Cart cart : carts) {
				cartService.deleteCart(cart.getId());
			}
			
			shoppingService.deleteShopping(shoppingId);
		}
		
		List<Shopping> shoppings = shoppingService.findAll();
		model.addAttribute("shoppings", shoppings);
		return "shopping";
	}
	
//	Load shopping to update
	@RequestMapping(value="shopping/updated", method=RequestMethod.GET)
    public String updateForm(
    		@Nonnull @RequestParam(value = "id", required = true) final long shoppingId,
    		Model model) {
		Shopping shopping = shoppingService.findOne(shoppingId);
		
		this.fk = shoppingId;
		model.addAttribute("fk", this.fk);
		
		List<Cart> carts = cartService.findByShoppingid(shoppingId);
		
		model.addAttribute("carts", carts);
        model.addAttribute("shopping", shopping);
        return "update-shopping";
    }
	
	@RequestMapping(value="/shopping/updated", method=RequestMethod.POST)
    public String updateSubmit(@ModelAttribute Shopping shopping, Model model) {
        model.addAttribute("shopping", shopping);
        
        Date created = new Date();
        shopping.setCreated(created);
        
        shoppingService.save(shopping);
        
        
        List<Shopping> shoppings = shoppingService.findAll();
		model.addAttribute("shoppings", shoppings);
		return "shopping";
    }
	
	
//	Add new shopping
	@RequestMapping(value="/shopping/new", method=RequestMethod.GET)
    public String shoppingForm(Model model) {
        model.addAttribute("shopping", new Shopping());
        return "new-shopping";
    }

	@Transactional
	@RequestMapping(value="/shopping/new", method=RequestMethod.POST)
    public String shoppingSubmit(@ModelAttribute Shopping shopping, Model model) {
        model.addAttribute("shopping", shopping);
        
        Date created = new Date();
        shopping.setCreated(created);
        
        shoppingService.save(shopping);
        System.out.println("last insert id:" + shoppingService.getLastInsertId());
        return "new-shopping-result";
    }
}
