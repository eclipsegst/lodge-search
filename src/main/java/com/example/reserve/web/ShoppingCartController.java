package com.example.reserve.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.web.http.HttpSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.reserve.AppConfig;
import com.example.reserve.domain.Cart;
import com.example.reserve.domain.Lodge;
import com.example.reserve.domain.Shopping;
import com.example.reserve.domain.ShoppingCart;
import com.example.reserve.domain.UserInfo;
import com.example.reserve.service.CalendarService;
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
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

@Controller
public class ShoppingCartController {
	
	@Autowired
	private ShoppingService shoppingService;
	
	@Autowired
	private UserInfoService userinfoService;
	
	@Autowired
	private ShoppingCart shoppingCart;
	
	@Autowired
	public ShoppingCartController(
			@Nonnull final ShoppingService shoppingService,
			@Nonnull final UserInfoService userinfoService
			) {
		this.shoppingService = shoppingService;
		this.userinfoService = userinfoService;
	}
	
	@RequestMapping(value="/shoppingcart")
	public String mycart(Model model) {
		model.addAttribute("userinfo", new UserInfo());
//		model.addAttribute("name", "name");
//		model.addAttribute("address", "address");
//		model.addAttribute("zipcode", "zipcode");
//		model.addAttribute("phone", "phone");
//		model.addAttribute("email", "email");
		
		List list = shoppingCart.getCarts();
		
		if (list != null && !list.isEmpty()) {
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
		
		model.addAttribute("carts", carts);
		return "cart";
	}
	
	@RequestMapping(value="/shoppingcart/checkout", method=RequestMethod.POST)
	public String checkout(@ModelAttribute UserInfo userinfo, Model model) throws MessagingException {
		model.addAttribute("userinfo", userinfo);
        
		System.out.println("user info: name=" + userinfo.getName() + ", phone=" + userinfo.getPhone() + ", address=" + 
				userinfo.getAddress() + ", zipcode=" + userinfo.getZipcode() + ", email=" + userinfo.getEmail());
		
		if (userinfo.getName() != null) {
			userinfoService.save(userinfo);
		}
        
		List<Cart> carts = new ArrayList<Cart>();
		carts = shoppingCart.getCarts();
		
		if (carts != null) {
			
			String email = shoppingCart.getEmail();
			if (email != null && !email.isEmpty()) {
			
				BigDecimal total = new BigDecimal("0");
				for (int i = 0; i < carts.size(); i++) {
					total = total.add(carts.get(i).getPayment());
				}
				
				System.out.println("total:" + total);
				
				Shopping shopping = new Shopping();
				shopping.setPayment(total);
				
			} else {
				// insert email and name to user table
				// get the user id to set shopping userid
				
				return "register-simple";
			}
			
			System.out.println("cart size:" + carts.size());
			// get the total amount
			
			
			
			
			
//			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//			ctx.register(AppConfig.class);
//			ctx.refresh();
//			JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);
//			MimeMessage mimeMessage = mailSender.createMimeMessage();
//			MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
//			mailMsg.setFrom("atrappedlife@gmail.com");
//			mailMsg.setTo("zztg2@mail.missouri.edu");
//			mailMsg.setSubject("Check out");
//			mailMsg.setText("Your order has been placed. Thanks!");
//			mailSender.send(mimeMessage);
//			
//			System.out.println("---Done---");
//			
//			JavaMailSenderImpl mailToAdminSender = ctx.getBean(JavaMailSenderImpl.class);
//			MimeMessage mimeMessageToAdmin = mailToAdminSender.createMimeMessage();
//			MimeMessageHelper mailMsgToAdmin = new MimeMessageHelper(mimeMessageToAdmin);
//			mailMsgToAdmin.setFrom("atrappedlife@gmail.com");
//			mailMsgToAdmin.setTo("zztg2@missouri.edu");
//			mailMsgToAdmin.setSubject("New Order");
//			mailMsgToAdmin.setText("We have a new order.");
//			mailSender.send(mimeMessageToAdmin);
//			
//			System.out.println("---Done---");
		
		} else {
			//to do
		}

		return "checkout-success";
	}
}
