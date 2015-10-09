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
import com.example.reserve.domain.Landlord;
import com.example.reserve.domain.Lodge;
import com.example.reserve.service.ExperienceService;
import com.example.reserve.service.GalleryService;
import com.example.reserve.service.LandlordService;
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
	private final LandlordService landlordService;
	
	@Autowired
    private JavaMailSender mailSender;
	 	
	@Autowired
	public HomeController(
			@Nonnull final LodgeService lodgeService,
			@Nonnull final ExperienceService experienceService,
			@Nonnull final GalleryService galleryService,
			@Nonnull final LandlordService landlordService
			) {
		this.lodgeService = lodgeService;
		this.experienceService = experienceService;
		this.galleryService = galleryService;
		this.landlordService = landlordService;
	}
	
	public List<String> locations = Arrays.asList("厳原港近辺", "比田勝港近辺", "対馬空港近辺");
	public List<String> categories = Arrays.asList("郷土料理体験", "農林業体験", "漁業体験", "ものづくり体験", "自然探索体験", "その他");
	public List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	
	@RequestMapping(value="/")
	@Transactional(readOnly = true)
	public String home(Model model) {
		model.addAttribute("email", new Email());
		
		model.addAttribute("locations", locations);
		model.addAttribute("categories", categories);
		model.addAttribute("numbers", numbers);
		
		List<Lodge> lodges = lodgeService.findThree();
		
		System.out.println("lodges size: " + lodges.size());
		Map<Long, Long> lodgeGallery = new HashMap<Long, Long>();
		Map<Long, Landlord> lodgeLandlord = new HashMap<Long, Landlord>();
		Map<Long, Long> landlordGallery = new HashMap<Long, Long>();
		for(int i = 0; i < lodges.size(); i++) {
			List<Gallery> galleries = galleryService.findByFkByCategory(lodges.get(i).getId(), "lodge");
			if (!galleries.isEmpty()) {
				long imageId = galleries.get(0).getId();
				lodgeGallery.put(lodges.get(i).getId(), galleries.get(0).getId());
			}
			
			Landlord landlord = null;
			
			if (lodges.get(i).getFk() != null) {
				landlord = landlordService.findOne(lodges.get(i).getFk());
				lodgeLandlord.put(lodges.get(i).getId(), landlord);
				
				List<Gallery> landlordGalleries = galleryService.findByFkByCategory(landlord.getId(), "landlord");
				if (!landlordGalleries.isEmpty()) {
					long landlordImageId = landlordGalleries.get(0).getId();
					landlordGallery.put(lodges.get(i).getId(), landlordImageId);
				}
			}
		}
		
		
		List<Gallery> carousels = galleryService.findByFkByCategory(-1L, "carousel");
		model.addAttribute("carousels", carousels);
		
		// have to set up active carousel item
		Gallery defaultImage = carousels.get(0);
		model.addAttribute("defaultImage", defaultImage);
		carousels.remove(0);
		
		model.addAttribute("lodgeLandlord", lodgeLandlord);
		model.addAttribute("landlordGallery", landlordGallery);
		
		model.addAttribute("lodges", lodges);
		model.addAttribute("lodgeGallery", lodgeGallery);
		
		// experience and gallery
		List<Experience> experiences = experienceService.findThree();
		
		Map<Long, Long> experienceGallery = new HashMap<Long, Long>();
		Map<Long, Landlord> experienceLandlord = new HashMap<Long, Landlord>();
		Map<Long, Long> experienceLandlordGallery = new HashMap<Long, Long>();
		for(int i = 0; i < experiences.size(); i++) {
			List<Gallery> galleries = galleryService.findByFkByCategory(experiences.get(i).getId(), "experience");
			if (!galleries.isEmpty()) {
				long imageId = galleries.get(0).getId();
				experienceGallery.put(experiences.get(i).getId(), galleries.get(0).getId());
			}
			
			Landlord landlord = null;
			
			if (experiences.get(i).getFk() != null) {
				landlord = landlordService.findOne(experiences.get(i).getFk());
				experienceLandlord.put(experiences.get(i).getId(), landlord);
				
				List<Gallery> landlordGalleries = galleryService.findByFkByCategory(landlord.getId(), "landlord");
				if (!landlordGalleries.isEmpty()) {
					long landlordImageId = landlordGalleries.get(0).getId();
					experienceLandlordGallery.put(experiences.get(i).getId(), landlordImageId);
				}
			}
		}
		
		model.addAttribute("experienceLandlord", experienceLandlord);
		model.addAttribute("experienceLandlordGallery", experienceLandlordGallery);
		
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
		model.addAttribute("email", new Email());
		return "howto";
	}
	
	@RequestMapping(value="/question")
	@Transactional(readOnly = true)
	public String question(Model model) {
		model.addAttribute("email", new Email());
		return "question";
	}
	
	@RequestMapping(value="/inquiry")
	@Transactional(readOnly = true)
	public String inquiry(Model model) {
		model.addAttribute("email", new Email());
		return "inquiry";
	}
	
	@RequestMapping(value="/pagenotfound")
	@Transactional(readOnly = true)
	public String pagenotfound(Model model) {
		return "pagenotfound";
	}
	
	@RequestMapping(value="/about")
	@Transactional(readOnly = true)
	public String about(Model model) {
		model.addAttribute("email", new Email());
		return "about";
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
