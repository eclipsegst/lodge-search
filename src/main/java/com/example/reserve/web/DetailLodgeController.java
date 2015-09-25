package com.example.reserve.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.reserve.domain.Calendar;
import com.example.reserve.domain.CheckInOut;
import com.example.reserve.domain.Food;
import com.example.reserve.domain.Gallery;
import com.example.reserve.domain.Landlord;
import com.example.reserve.domain.Lodge;
import com.example.reserve.domain.ShoppingCart;
import com.example.reserve.service.CalendarService;
import com.example.reserve.service.FoodService;
import com.example.reserve.service.GalleryService;
import com.example.reserve.service.LandlordService;
import com.example.reserve.service.LodgeService;

@Controller
public class DetailLodgeController {
	
	@Autowired
	private final LodgeService lodgeService;
	
	@Autowired
	private final GalleryService galleryService;

	@Autowired
	private final FoodService foodService;
	
	@Autowired
	private final LandlordService landlordService;
	
	@Autowired
	private final CalendarService calendarService;
	
	@Autowired
	private ShoppingCart shoppingCart;
	
	@Autowired
	public DetailLodgeController(
			@Nonnull final LodgeService lodgeService,
			@Nonnull final GalleryService galleryService,
			@Nonnull final FoodService foodService,
			@Nonnull final LandlordService landlordService,
			@Nonnull final CalendarService calendarService
			) {
		this.lodgeService = lodgeService;
		this.galleryService = galleryService;
		this.foodService = foodService;
		this.landlordService = landlordService;
		this.calendarService = calendarService;
	}
	
	private long lodgeId = -1L;
	private Lodge lodge;
	
	public List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	
	@SuppressWarnings("null")
	@RequestMapping(value="/lodge/detail", method=RequestMethod.GET)
	public String detail(
			@Nonnull @RequestParam(value = "id", required = true) final long lodgeId,
			@ModelAttribute CheckInOut checkinout,
			Model model) {
		model.addAttribute("checkinout", new CheckInOut());
		this.lodgeId = lodgeId;
		
		lodge = lodgeService.findOne(lodgeId);
		
		List<Gallery> gallerys = galleryService.findByFkByCategory(lodgeId, "lodge");
		List<Food> foods = foodService.findByFkByCategory(lodgeId, "lodge");
		List<Calendar> calendars = calendarService.findByFkByCategory(lodgeId, "lodge");
		List<String> closedDate = new ArrayList<String>();
				

		
		Landlord landlord = null;
		
		if (lodge.getFk() != null) {
			landlord = landlordService.findOne(lodge.getFk());
		}
		
		System.out.println("gallerys:" + gallerys.size());
		System.out.println("foods:" + foods.size());
		System.out.println("calendars:" + calendars.size());
		System.out.println("calendars, date:" + calendars.get(0).getCloseddate());
		
		for(int i = 0; i < calendars.size(); i++) {
			System.out.println(calendars.get(i).getCloseddate());
			closedDate.add(String.valueOf(calendars.get(i).getCloseddate()));	
		}
		
		
		if(landlord != null) {
			System.out.println("landlord:" + landlord.getName());
		}
		
		
		// have to set up active carousel item
		Gallery defaultImage = gallerys.get(0);
		model.addAttribute("defaultImage", defaultImage);
		gallerys.remove(0);
		
		// get default landlord image
		List<Gallery> landlordPhotos = galleryService.findByFkByCategory(landlord.getId(), "landlord");
		Gallery defaultLandlordPhoto = landlordPhotos.get(0);
		
		
		model.addAttribute("movie", "Goal");
		
		model.addAttribute("gallerys", gallerys);
		model.addAttribute("foods", foods);
		model.addAttribute("closedDate", closedDate);
		model.addAttribute("landlord", landlord);
		model.addAttribute("defaultLandlordPhoto", defaultLandlordPhoto);
		model.addAttribute("lodge", lodge);
		model.addAttribute("numbers", numbers);
		
		return "detail-lodge";
	}
}
