package com.example.reserve.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.reserve.domain.Gallery;
import com.example.reserve.domain.Lodge;
import com.example.reserve.service.GalleryService;
import com.example.reserve.service.LodgeService;

@Controller
public class HomeController {

	@Autowired
	private final LodgeService lodgeService;
	
	@Autowired
	private final GalleryService galleryService;
	
	@Autowired
	public HomeController(
			@Nonnull final LodgeService lodgeService,
			@Nonnull final GalleryService galleryService
			) {
		this.lodgeService = lodgeService;
		this.galleryService = galleryService;
	}
	
	@RequestMapping(value="/")
	@Transactional(readOnly = true)
	public String home(Model model) {
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
		
		System.out.println(lodgeGallery.get(111L));
		
		model.addAttribute("lodges", lodges);
		model.addAttribute("lodgeGallery", lodgeGallery);
		
		return "index";
	}
	
	@RequestMapping(value="/cart/")
	@Transactional(readOnly = true)
	public String cart(Model model) {
		return "cart";
	}
	
	@RequestMapping(value="/howto/")
	@Transactional(readOnly = true)
	public String howto(Model model) {
		return "howto";
	}
	
	@RequestMapping(value="/question/")
	@Transactional(readOnly = true)
	public String question(Model model) {
		return "question";
	}
	
	@RequestMapping(value="/inquiry/")
	@Transactional(readOnly = true)
	public String inquiry(Model model) {
		return "inquiry";
	}
}
