package com.example.reserve.web;

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

import com.example.reserve.domain.Food;
import com.example.reserve.service.FoodService;

@Controller
public class FoodController{
	@Autowired
	private final FoodService foodService;

	protected long fk = -1L;
	
	@Autowired
	public FoodController(
			@Nonnull final FoodService foodService
			) {
		this.foodService = foodService;
	}
	
	@RequestMapping(value="/food")
	@Transactional(readOnly = true)
	public String food(Model model) {
		List<Food> foods = foodService.findAll();
		model.addAttribute("foods", foods);
		return "food";
	}
	
	@RequestMapping(value="/food/update")
	@Transactional(readOnly = false)
	public String update(
			@Nonnull @RequestParam(value = "id", required = true) final long foodId,
			@Nonnull @RequestParam(value = "action", required = true) final String action,
			Model model) {
		
		this.fk = foodId;
		model.addAttribute("fk", this.fk);
		
		if (action.equalsIgnoreCase("update")) {
			return updateForm(foodId, model);
		} else if (action.equalsIgnoreCase("delete")) {
			foodService.deleteFood(foodId);
		}
		
		List<Food> foods = foodService.findAll();
		model.addAttribute("foods", foods);
		return "food";
	}
	
//	Load food to update
	@RequestMapping(value="food/updated", method=RequestMethod.GET)
    public String updateForm(
    		@Nonnull @RequestParam(value = "id", required = true) final long foodId,
    		Model model) {
		Food food = foodService.findOne(foodId);
		
		this.fk = foodId;
		model.addAttribute("fk", this.fk);
		
        model.addAttribute("food", food);
        return "update-food";
    }
	
	@RequestMapping(value="/food/updated", method=RequestMethod.POST)
    public String updateSubmit(@ModelAttribute Food food, Model model) {
        model.addAttribute("food", food);
        
        foodService.save(food);
        
        List<Food> foods = foodService.findAll();
		model.addAttribute("foods", foods);
		return "food";
    }
	
//	Add new food
	@RequestMapping(value="/food/new", method=RequestMethod.GET)
    public String foodForm(Model model) {
        model.addAttribute("food", new Food());
        return "new-food";
    }

	@RequestMapping(value="/food/new", method=RequestMethod.POST)
    public String foodSubmit(@ModelAttribute Food food, Model model) {
        model.addAttribute("food", food);
        
        foodService.save(food);
        
        return "new-food-result";
    }

}
