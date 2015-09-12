package com.example.reserve.web;


import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.reserve.domain.Gallery;
import com.example.reserve.domain.Landlord;
import com.example.reserve.service.GalleryService;
import com.example.reserve.service.LandlordRepository;
import com.example.reserve.service.LandlordService;

@Controller
public class LandlordController{
	@Autowired
	private final LandlordService landlordService;
	
	@Autowired
	private final GalleryService galleryService;

	protected long fk = -1L
			;
	@Autowired
	public LandlordController(
			@Nonnull final LandlordService landlordService,
			@Nonnull final GalleryService galleryService
			) {
		this.landlordService = landlordService;
		this.galleryService = galleryService;
	}
	
	@RequestMapping(value="/landlord")
	@Transactional(readOnly = true)
	public String landlord(Model model) {
		List<Landlord> landlords = landlordService.findAll();
		model.addAttribute("landlords", landlords);
		return "landlords";
	}
	
	@RequestMapping(value="/landlord/update")
	@Transactional(readOnly = false)
	public String update(
			@Nonnull @RequestParam(value = "id", required = true) final long landlordId,
			@Nonnull @RequestParam(value = "action", required = true) final String action,
			Model model) {
		
		this.fk = landlordId;
		model.addAttribute("fk", this.fk);
		
		if (action.equalsIgnoreCase("update")) {
			return updateForm(landlordId, model);
		} else if (action.equalsIgnoreCase("delete")) {
			// If there are images associated with this landlord, those images are also be deleted at the same time.
			List<Gallery> gallerys = galleryService.findByFkByCategory(landlordId, "landlord");
			for(Gallery gallery : gallerys) {
				galleryService.deleteGallery(gallery.getId());
			}
			
			landlordService.deleteLandlord(landlordId);
		}
		
		List<Landlord> landlords = landlordService.findAll();
		model.addAttribute("landlords", landlords);
		return "landlords";
	}
	
//	Load landlord to update
	@RequestMapping(value="landlord/updated", method=RequestMethod.GET)
    public String updateForm(
    		@Nonnull @RequestParam(value = "id", required = true) final long landlordId,
    		Model model) {
		Landlord landlord = landlordService.findOne(landlordId);
		
		this.fk = landlordId;
		model.addAttribute("fk", this.fk);
		
		List<Gallery> gallerys = galleryService.findByFkByCategory(landlordId, "landlord");
		if (gallerys != null) {
			System.out.println("size: " + gallerys.size());
		} else {
			System.out.println("cannot find any image by this fk and landlordid");
		}
		
		model.addAttribute("gallerys", gallerys);
        model.addAttribute("landlord", landlord);
        return "update-landlord";
    }
	
	@RequestMapping(value="/landlord/updated", method=RequestMethod.POST)
    public String updateSubmit(@ModelAttribute Landlord landlord, Model model) {
        model.addAttribute("landlord", landlord);
        
        landlordService.save(landlord);
        
        List<Landlord> landlords = landlordService.findAll();
		model.addAttribute("landlords", landlords);
		return "landlords";
    }
	
	
//	Add new landlord
	@RequestMapping(value="/landlord/new", method=RequestMethod.GET)
    public String landlordForm(Model model) {
        model.addAttribute("landlord", new Landlord());
        return "new-landlord";
    }

	@RequestMapping(value="/landlord/new", method=RequestMethod.POST)
    public String landlordSubmit(@ModelAttribute Landlord landlord, Model model) {
        model.addAttribute("landlord", landlord);
        
        landlordService.save(landlord);
        
        return "new-landlord-result";
    }
	
	/*
	 * Update image
	 */
	@RequestMapping(value="/landlord/image")
	@Transactional(readOnly = false)
	public String updateImage(
			@Nonnull @RequestParam(value = "id", required = true) final long imageId,
			@Nonnull @RequestParam(value = "action", required = true) final String action,
			Model model) {
		
		Gallery gallery = galleryService.findOne(imageId);
		
		
		if (action.equalsIgnoreCase("update")) {
			gallery.setActive(true);
			galleryService.save(gallery);
		} else if (action.equalsIgnoreCase("false")) {
			gallery.setActive(false);
			galleryService.save(gallery);
		} else if (action.equalsIgnoreCase("delete")) {
			galleryService.deleteGallery(imageId);
		}
		
		List<Landlord> landlords = landlordService.findAll();
		model.addAttribute("landlords", landlords);
		return "landlords";
	}

}
