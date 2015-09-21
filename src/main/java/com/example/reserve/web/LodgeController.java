package com.example.reserve.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.reserve.domain.Experience;
import com.example.reserve.domain.Gallery;
import com.example.reserve.domain.Lodge;
import com.example.reserve.service.GalleryService;
import com.example.reserve.service.LodgeRepository;
import com.example.reserve.service.LodgeService;

@Controller
public class LodgeController{
	@Autowired
	private final LodgeService lodgeService;
	
	@Autowired
	private final GalleryService galleryService;

	protected long fk = -1L
			;
	@Autowired
	public LodgeController(
			@Nonnull final LodgeService lodgeService,
			@Nonnull final GalleryService galleryService
			) {
		this.lodgeService = lodgeService;
		this.galleryService = galleryService;
	}
	
	public List<String> locations = Arrays.asList("厳原港近辺", "比田勝港近辺", "対馬空港近辺");
	public List<String> categories = Arrays.asList("boating", "climbing", "cooking", "fishing");
	public List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	
	@RequestMapping(value="/lodge")
	@Transactional(readOnly = true)
	public String lodge(Model model) {
		List<Lodge> lodges = lodgeService.findAll();
		model.addAttribute("lodges", lodges);
		return "lodges";
	}
	
	@RequestMapping(value="/lodge/update")
	@Transactional(readOnly = false)
	public String update(
			@Nonnull @RequestParam(value = "id", required = true) final long lodgeId,
			@Nonnull @RequestParam(value = "action", required = true) final String action,
			Model model) {
		
		this.fk = lodgeId;
		model.addAttribute("fk", this.fk);
		
		if (action.equalsIgnoreCase("update")) {
			return updateForm(lodgeId, model);
		} else if (action.equalsIgnoreCase("delete")) {
			// If there are images associated with this lodge, those images are also be deleted at the same time.
			List<Gallery> gallerys = galleryService.findByFkByCategory(lodgeId, "lodge");
			for(Gallery gallery : gallerys) {
				galleryService.deleteGallery(gallery.getId());
			}
			
			lodgeService.deleteLodge(lodgeId);
		}
		
		List<Lodge> lodges = lodgeService.findAll();
		model.addAttribute("lodges", lodges);
		return "lodges";
	}
	
//	Load lodge to update
	@RequestMapping(value="lodge/updated", method=RequestMethod.GET)
    public String updateForm(
    		@Nonnull @RequestParam(value = "id", required = true) final long lodgeId,
    		Model model) {
		Lodge lodge = lodgeService.findOne(lodgeId);
		
		this.fk = lodgeId;
		model.addAttribute("fk", this.fk);
		
		List<Gallery> gallerys = galleryService.findByFkByCategory(lodgeId, "lodge");
		if (gallerys != null) {
			System.out.println("size: " + gallerys.size());
		} else {
			System.out.println("cannot find any image by this fk and lodgeid");
		}
		
		model.addAttribute("gallerys", gallerys);
        model.addAttribute("lodge", lodge);
        return "update-lodge";
    }
	
	@RequestMapping(value="/lodge/updated", method=RequestMethod.POST)
    public String updateSubmit(@ModelAttribute Lodge lodge, Model model) {
        model.addAttribute("lodge", lodge);
        
        lodgeService.save(lodge);
        
        List<Lodge> lodges = lodgeService.findAll();
		model.addAttribute("lodges", lodges);
		return "lodges";
    }
	
	
//	Add new lodge
	@RequestMapping(value="/lodge/new", method=RequestMethod.GET)
    public String lodgeForm(Model model) {
        model.addAttribute("lodge", new Lodge());
        return "new-lodge";
    }

	@RequestMapping(value="/lodge/new", method=RequestMethod.POST)
    public String lodgeSubmit(@ModelAttribute Lodge lodge, Model model) {
        model.addAttribute("lodge", lodge);
        
        lodgeService.save(lodge);
        
        return "new-lodge-result";
    }
	
	/*
	 * Update image
	 */
	@RequestMapping(value="/lodge/image")
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
		
		List<Lodge> lodges = lodgeService.findAll();
		model.addAttribute("lodges", lodges);
		return "lodges";
	}
	
	@RequestMapping(value="/lodge/search", method=RequestMethod.GET)
	@Transactional(readOnly = true)
	public String searchLodge(
			Model model) {
		model.addAttribute("lodge", new Lodge());
		List<Lodge> lodges = lodgeService.findAll();
		model.addAttribute("lodges", lodges);
		model.addAttribute("locations", locations);
		model.addAttribute("categories", categories);
		model.addAttribute("numbers", numbers);
		
		Map<Long, Long> lodgeGallery = new HashMap<Long, Long>();
		for(int i = 0; i < lodges.size(); i++) {
			List<Gallery> galleries = galleryService.findByFkByCategory(lodges.get(i).getId(), "lodge");
			if (!galleries.isEmpty()) {
				lodgeGallery.put(lodges.get(i).getId(), galleries.get(0).getId());
			}
		}
		
		model.addAttribute("lodges", lodges);
		model.addAttribute("lodgeGallery", lodgeGallery);
		
		return "lodge-search";
	}
	
	@RequestMapping(value="/lodge/search", method=RequestMethod.POST)
	@Transactional(readOnly = true)
	public String searchLodgeResult(@ModelAttribute Lodge lodge, Model model) {
        model.addAttribute("lodge", lodge);
        
        String location = lodge.getLocation();
        
        int adult = lodge.getAdult();
        int teenager = lodge.getTeenager();
        int infant = lodge.getInfant();
        if (location.isEmpty() || location == "") {
			location = null;
		}
         
		List<Lodge> lodges = lodgeService.findLodgeByCriteria(location, adult, teenager, infant);
		System.out.println("lodge search criteria:" + location + adult + teenager + infant);
		model.addAttribute("lodges", lodges);
		model.addAttribute("locations", locations);
		model.addAttribute("numbers", numbers);
		
		Map<Long, Long> lodgeGallery = new HashMap<Long, Long>();
		for(int i = 0; i < lodges.size(); i++) {
			List<Gallery> galleries = galleryService.findByFkByCategory(lodges.get(i).getId(), "lodge");
			if (!galleries.isEmpty()) {
				lodgeGallery.put(lodges.get(i).getId(), galleries.get(0).getId());
			}
		}
		
		model.addAttribute("lodges", lodges);
		model.addAttribute("lodgeGallery", lodgeGallery);
		return "lodge-search";
	}

}
