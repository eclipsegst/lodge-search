package com.example.reserve.web;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.reserve.domain.Calendar;
import com.example.reserve.domain.Cart;
import com.example.reserve.domain.CheckInOut;
import com.example.reserve.domain.Email;
import com.example.reserve.domain.Food;
import com.example.reserve.domain.Gallery;
import com.example.reserve.domain.Landlord;
import com.example.reserve.domain.Lodge;
import com.example.reserve.domain.Shopping;
import com.example.reserve.domain.ShoppingCart;
import com.example.reserve.service.CalendarService;
import com.example.reserve.service.FoodService;
import com.example.reserve.service.GalleryService;
import com.example.reserve.service.LandlordService;
import com.example.reserve.service.LodgeService;
import com.example.reserve.service.ShoppingService;

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
	private ShoppingService shoppingService;
	
	@Autowired
	private ShoppingCart shoppingCart;
	
	@Autowired
	public DetailLodgeController(
			@Nonnull final LodgeService lodgeService,
			@Nonnull final GalleryService galleryService,
			@Nonnull final FoodService foodService,
			@Nonnull final LandlordService landlordService,
			@Nonnull final CalendarService calendarService,
			@Nonnull final ShoppingService shoppingService
			) {
		this.lodgeService = lodgeService;
		this.galleryService = galleryService;
		this.foodService = foodService;
		this.landlordService = landlordService;
		this.calendarService = calendarService;
		this.shoppingService = shoppingService;
	}
	
	private long lodgeId = -1L;
	private Lodge lodge;
	
	public List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	
	@RequestMapping(value="/lodge/detail", method=RequestMethod.GET)
	public String detail(
			@Nonnull @RequestParam(value = "id", required = true) final long lodgeId,
			Model model) {
		model.addAttribute("email", new Email());
		model.addAttribute("cart", new Cart());
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
		model.addAttribute("cart", new Cart());
		
		return "detail-lodge";
	}
	
	@Transactional
	@RequestMapping(value="/lodge/addtocart", method=RequestMethod.POST)
	public String addToCart(
			@ModelAttribute Cart cart,
			@ModelAttribute CheckInOut checkinout,
			Model model
			) throws ParseException {
		
		model.addAttribute("cart", new Cart());
		model.addAttribute("checkinout", new CheckInOut());
		
		java.sql.Date checkin = null;
		java.sql.Date checkout = null;
		
		try {
			checkin = convertStringToSqlDate(checkinout.getCheckin());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			checkout = convertStringToSqlDate(checkinout.getCheckout());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int numberofday = checkout.getDate() - checkin.getDate();
		
		System.out.println("checkin:" + checkin);
		System.out.println("checkout:" + checkout);
		System.out.println("numberofday:" + numberofday);

		cart.setFk(lodge.getId());
		cart.setCategory("lodge");
	
		cart.setCheckin(checkin);
		cart.setCheckout(checkout);
		
		UUID uniqueKey = UUID.randomUUID(); 
		cart.setUuid(uniqueKey);
		
//		// calculate the payment by food id and the number of customer
		System.out.println("foodId:" + cart.getFoodfk());
		long foodId = cart.getFoodfk();
		Food food = foodService.findOne(foodId);
		int adultPayment= food.getAdult() * cart.getAdult();
		int childrenPayment = food.getTeenager() * (cart.getTeenager() + cart.getInfant());
		int payment = (adultPayment + childrenPayment) * numberofday;
		cart.setPayment(new BigDecimal(payment));
	
		List<Cart> cards = new ArrayList<Cart>();
		cards = shoppingCart.getCarts();
		
		if (cards == null || cards.isEmpty() ) {
			// create shopping cart
			shoppingService.save(new Shopping());
			long shoppingid = shoppingService.getLastInsertId();
			cards = new ArrayList<Cart>(); // assign a new array since it's null
			// add item to cart
			System.out.println("new shopping id:" + shoppingid);
			cart.setShoppingid(shoppingid);
			System.out.println("cart shopping id:" + cart.getShoppingid());
			cards.add(cart);
		} else {
			long shoppingid = cards.get(0).getShoppingid();
			System.out.println("exist shopping id:" + shoppingid);
			cart.setShoppingid(shoppingid);
			cards.add(cart);
		}

		shoppingCart.setCarts(cards);
		
		cards = shoppingCart.getCarts();
		System.out.println("shopping card size:" + cards.size());
		System.out.println("shopping id:" + cards.get(0).getShoppingid() + 
				", fk:" + cards.get(0).getFk() + 
				", category:" + cards.get(0).getCategory() + 
				", checkin:" + cards.get(0).getCheckin() +
				", checkout:" + cards.get(0).getCheckout() + 
				", foodfk:" + cards.get(0).getFoodfk() + 
				", adult:" + cards.get(0).getAdult() + 
				", teenager:" + cards.get(0).getTeenager() + 
				", infant:" + cards.get(0).getInfant() + 
				", payment:" + cards.get(0).getPayment());
		
		model.addAttribute("carts", cards);
		return "redirect:/shoppingcart";
	}
	
	private static java.sql.Date convertToSqlDate(java.util.Date date) {
	    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	    return sqlDate;
	}
	
	private static java.sql.Date convertStringToSqlDate(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = format.parse(date);
        java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
        return sqlDate;
	}
}
