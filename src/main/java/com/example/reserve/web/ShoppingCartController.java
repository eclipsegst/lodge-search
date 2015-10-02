package com.example.reserve.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.web.http.HttpSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.example.reserve.domain.Cart;
import com.example.reserve.domain.Gallery;
import com.example.reserve.domain.Lodge;
import com.example.reserve.domain.Shopping;
import com.example.reserve.domain.ShoppingCart;
import com.example.reserve.domain.UserInfo;
import com.example.reserve.service.CalendarService;
import com.example.reserve.service.CartService;
import com.example.reserve.service.ExperienceService;
import com.example.reserve.service.FoodService;
import com.example.reserve.service.GalleryService;
import com.example.reserve.service.LandlordService;
import com.example.reserve.service.LodgeService;
import com.example.reserve.service.ShoppingService;
import com.example.reserve.service.UserInfoService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

@Controller
public class ShoppingCartController {
	
	@Autowired
	private CartService cartService;

	@Autowired
	private ExperienceService experienceService;
	
	@Autowired
	private FoodService foodService;
	
	@Autowired
	private LodgeService lodgeService;
	
	@Autowired
	private ShoppingService shoppingService;
	
	@Autowired
	private UserInfoService userinfoService;
	
	@Autowired
	private ShoppingCart shoppingCart;
	
	@Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;
	
	@Autowired
	public ShoppingCartController(
			@Nonnull final CartService cartService,
			@Nonnull final ExperienceService experienceService,
			@Nonnull final FoodService foodService,
			@Nonnull final LodgeService lodgeService,
			@Nonnull final ShoppingService shoppingService,
			@Nonnull final UserInfoService userinfoService
			) {
		this.cartService = cartService;
		this.experienceService = experienceService;
		this.foodService = foodService;
		this.lodgeService = lodgeService;
		this.shoppingService = shoppingService;
		this.userinfoService = userinfoService;
	}
	
	private String error;
	private long shoppingid;
	private Map<Long, String> cartName = new HashMap<Long, String>();
	private Map<Long, String> cartFoodName = new HashMap<Long, String>();
	
	@RequestMapping(value="/shoppingcart")
	public String mycart(@ModelAttribute UserInfo userinfo, Model model) {
		model.addAttribute("userinfo", new UserInfo());
		
		List list = shoppingCart.getCarts();
		
		if (list != null && !list.isEmpty()) {
			
			shoppingid = ((Cart)list.get(0)).getShoppingid();
			System.out.println("shoppingid:" + shoppingid);
			
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
		
		List<Cart> carts = new ArrayList<Cart>();
		carts = shoppingCart.getCarts();
		
		if (carts != null) {
			System.out.println("cart size:" + carts.size());
		} else {
			System.out.println("cart empty");
		}
		
		if (carts != null) {
			for(int i = 0; i < carts.size(); i++) {
				Cart cart = carts.get(i);
				
				String name = "";
				String foodName ="";
				
				// get lodge or experience name
				if (cart.getCategory().equals("lodge")) {
					long lodgeId = cart.getFk();
					name = lodgeService.findOne(lodgeId).getName();
					
				} else if (cart.getCategory().equals("experience")) {
					long experienceId = cart.getFk();
					name = experienceService.findOne(experienceId).getName();
				}
				
				cartName.put(cart.getId(), name);
				
				// get food name
				long foodId = cart.getFoodfk();
				foodName = foodService.findOne(foodId).getTitle();
				
				cartFoodName.put(cart.getId(), foodName);
				
			}
		}
		
		model.addAttribute("cartname", cartName);
		model.addAttribute("foodname", cartFoodName);
		model.addAttribute("carts", carts);
		return "cart";
	}
	
	@RequestMapping(value="/shoppingcart/checkout", method=RequestMethod.POST)
	public String checkout(@ModelAttribute UserInfo userinfo, Model model) throws MessagingException {
		model.addAttribute("userinfo", userinfo);
        
		System.out.println("user info: name=" + userinfo.getName() + ", phone=" + userinfo.getPhone() + ", address=" + 
				userinfo.getAddress() + ", zipcode=" + userinfo.getZipcode() + ", email=" + userinfo.getEmail());
		
		String emailUser = userinfo.getEmail();
		// TODO: when user login
		// String email = shoppingCart.getEmail();
		if (emailUser == null || emailUser.isEmpty() || emailUser == "") {
			error = "Please input valid email.";
			return mycart(userinfo, model);
		}
        
		List<Cart> carts = new ArrayList<Cart>();
		carts = shoppingCart.getCarts();
		
		if (carts != null) {
			
			BigDecimal total = new BigDecimal("0");
			for (int i = 0; i < carts.size(); i++) {
				total = total.add(carts.get(i).getPayment());
			}
			
			System.out.println("total:" + total);
			
			// insert user info
			userinfoService.save(userinfo);
			
			
			System.out.println("carts size:" + carts.size());
			
			// insert detail to cart
			for(int i = 0; i < carts.size(); i++) {
				System.out.println("saving item #:" + i);
				cartService.save(carts.get(i));
				
				
			}
			
			List<String> rows = new ArrayList<String>();
			
			for(int i = 0; i < carts.size(); i++) {
				Cart cart = carts.get(i);
				String row =  cart.getCheckin()+
						" " + cart.getCategory() +
						" " + cartName.get(cart.getId()) +
						" " + "大人" + cart.getAdult() + "名" +
						" " + "子供人" + cart.getAdult() + "名" +
						" " + "幼児" + cart.getAdult() + "名";
				rows.add(row + " \t\n");
			}
			
			rows.add("合計 " + total + "円" + "\t\n");
			
			// insert shopping session id to shopping
			Shopping shopping = new Shopping();
			shopping.setId(shoppingid);
			shopping.setPayment(total);
			shopping.setValid(true);
			shoppingService.save(shopping);
			
			Locale locale = new Locale("ja");
			
			final Context ctx = new Context(locale);
			ctx.setVariable("name", userinfo.getName());
			ctx.setVariable("subscriptionDate", new Date());
			ctx.setVariable("rows", rows);
			ctx.setVariable("userinfo", userinfo);
//			ctx.setVariable("imageResourceName", imageResourceName); 
			 
			final String htmlContent = this.templateEngine.process("email-inlineimage", ctx);
			
			
			// send email to user
			MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
			mailMsg.setFrom("tsushimarevive@gmail.com");
			mailMsg.setTo(emailUser);
			mailMsg.setSubject("Thanks!");
			mailMsg.setText(htmlContent, true);
//			mailMsg.setText("Your order has been placed. Thanks!");
			mailSender.send(mimeMessage);
			
			System.out.println("---send email to user success---");
			
			// send email to admin
			MimeMessage mimeMessageToAdmin = this.mailSender.createMimeMessage();
			MimeMessageHelper mailMsgToAdmin = new MimeMessageHelper(mimeMessageToAdmin);
			mailMsg.setFrom("tsushimarevive@gmail.com");
			mailMsgToAdmin.setTo("tsushimarevive@gmail.com");
			mailMsgToAdmin.setSubject("New Order");
			mailMsgToAdmin.setText("We have a new order.");
			mailSender.send(mimeMessageToAdmin);
			
			System.out.println("---send email to admin success---");
		
//			// use the true flag to indicate the text included is HTML
//			helper.setText("<html><body><img src=''cid:identifier1234''></body></html>", true);
		} else {
			error = "Sorry. You cannot check out with an empty cart.";
			return mycart(userinfo, model);
		}

		return "checkout-success";
	}
}
