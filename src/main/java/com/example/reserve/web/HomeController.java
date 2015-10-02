package com.example.reserve.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.reserve.domain.Email;
import com.example.reserve.domain.Experience;
import com.example.reserve.domain.Gallery;
import com.example.reserve.domain.Lodge;
import com.example.reserve.service.ExperienceService;
import com.example.reserve.service.GalleryService;
import com.example.reserve.service.LodgeService;

@Controller
public class HomeController {
	
	@Autowired
	private final LodgeService lodgeService;
	
	@Autowired
	private final ExperienceService experienceService;
	
	@Autowired
	private final GalleryService galleryService;

	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	public HomeController(
			@Nonnull final LodgeService lodgeService,
			@Nonnull final ExperienceService experienceService,
			@Nonnull final GalleryService galleryService
			) {
		this.lodgeService = lodgeService;
		this.experienceService = experienceService;
		this.galleryService = galleryService;
	}
	
	public List<String> locations = Arrays.asList("厳原港近辺", "比田勝港近辺", "対馬空港近辺");
	public List<String> categories = Arrays.asList("boating", "climbing", "cooking", "fishing");
	public List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	
	@RequestMapping(value="/")
	@Transactional(readOnly = true)
	public String home(Model model) {
		model.addAttribute("email", new Email());
		
		model.addAttribute("locations", locations);
		model.addAttribute("categories", categories);
		model.addAttribute("numbers", numbers);
		
		List<Lodge> lodges = lodgeService.findAll();
		
		System.out.println("lodges size: " + lodges.size());
		Map<Long, Long> lodgeGallery = new HashMap<Long, Long>();
		for(int i = 0; i < lodges.size(); i++) {
			List<Gallery> galleries = galleryService.findByFkByCategory(lodges.get(i).getId(), "lodge");
			if (!galleries.isEmpty()) {
				System.out.println(galleries.size());
				long imageId = galleries.get(0).getId();
				System.out.println("image id: " + imageId);
				lodgeGallery.put(lodges.get(i).getId(), galleries.get(0).getId());
			}
		}
		
		model.addAttribute("lodges", lodges);
		model.addAttribute("lodgeGallery", lodgeGallery);
		
		// experience and gallery
		List<Experience> experiences = experienceService.findAll();
		
		Map<Long, Long> experienceGallery = new HashMap<Long, Long>();
		for(int i = 0; i < experiences.size(); i++) {
			List<Gallery> galleries = galleryService.findByFkByCategory(experiences.get(i).getId(), "experience");
			if (!galleries.isEmpty()) {
				System.out.println(galleries.size());
				long imageId = galleries.get(0).getId();
				System.out.println("image id: " + imageId);
				experienceGallery.put(experiences.get(i).getId(), galleries.get(0).getId());
			}
		}
		
		model.addAttribute("experiences", experiences);
		model.addAttribute("experienceGallery", experienceGallery);
		
		return "home";
	}
	
//	@RequestMapping(value="/shoppingcart/")
//	@Transactional(readOnly = true)
//	public String cart(Model model) {
//		return "cart";
//	}
	
	@RequestMapping(value="/howto")
	@Transactional(readOnly = true)
	public String howto(Model model) {
		return "howto";
	}
	
	@RequestMapping(value="/question")
	@Transactional(readOnly = true)
	public String question(Model model) {
		return "question";
	}
	
	@RequestMapping(value="/inquiry")
	@Transactional(readOnly = true)
	public String inquiry(Model model) {
		return "inquiry";
	}
	
	@RequestMapping(value="/pagenotfound")
	@Transactional(readOnly = true)
	public String pagenotfound(Model model) {
		return "pagenotfound";
	}
	
	@RequestMapping(value="/email/new", method=RequestMethod.POST)
	@Transactional(readOnly = true)
	public String sendEmail(@ModelAttribute Email email, Model model ) throws MessagingException {
		model.addAttribute("email", email);
		
		MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
		mailMsg.setFrom("tsushimarevive@gmail.com");
		mailMsg.setTo("tsushimarevive@gmail.com");
		mailMsg.setSubject("CONTACT US");
		
		String message = email.getName() + "\t\n" + email.getEmail() + "\t\n" + email.getMessage();
		mailMsg.setText(message);
		mailSender.send(mimeMessage);
		
		System.out.println("---send query email to success---");
		
		return "redirect:/";
	}
}
