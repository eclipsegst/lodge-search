package com.example.reserve.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.web.http.HttpSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.reserve.AppConfig;
import com.example.reserve.domain.Cart;
import com.example.reserve.domain.ShoppingCart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

@Controller
public class ShoppingCartController {
	
	@Autowired private ShoppingCart shoppingCart;
	
	@RequestMapping(value="/shoppingcart")
	public String mycart(Model model) {
		
		List list = shoppingCart.getCards();
		
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
		carts = shoppingCart.getCards();
		
		if (carts != null) {
			System.out.println("cart size:" + carts.size());
		} else {
			System.out.println("cart empty");
		}
		
		model.addAttribute("carts", carts);
		return "cart";
	}
	
	@RequestMapping(value="/shoppingcart/checkout")
	public String checkout(Model model) throws MessagingException {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
		JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
		mailMsg.setFrom("atrappedlife@gmail.com");
		mailMsg.setTo("zztg2@mail.missouri.edu");
		mailMsg.setSubject("Check out");
		mailMsg.setText("Your order has been placed. Thanks!");
		mailSender.send(mimeMessage);
		
		System.out.println("---Done---");
		
		JavaMailSenderImpl mailToAdminSender = ctx.getBean(JavaMailSenderImpl.class);
		MimeMessage mimeMessageToAdmin = mailToAdminSender.createMimeMessage();
		MimeMessageHelper mailMsgToAdmin = new MimeMessageHelper(mimeMessageToAdmin);
		mailMsgToAdmin.setFrom("atrappedlife@gmail.com");
		mailMsgToAdmin.setTo("zztg2@missouri.edu");
		mailMsgToAdmin.setSubject("New Order");
		mailMsgToAdmin.setText("We have a new order.");
		mailSender.send(mimeMessageToAdmin);
		
		System.out.println("---Done---");

		return "checkout-success";
	}
}
