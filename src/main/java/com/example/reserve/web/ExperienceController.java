package com.example.reserve.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.reserve.domain.Gallery;
import com.example.reserve.domain.Experience;
import com.example.reserve.service.GalleryService;
import com.example.reserve.service.ExperienceService;

@Controller
public class ExperienceController{
	@Autowired
	private final ExperienceService experienceService;
	
	@Autowired
	private final GalleryService galleryService;

	protected long fk = -1L
			;
	@Autowired
	public ExperienceController(
			@Nonnull final ExperienceService experienceService,
			@Nonnull final GalleryService galleryService
			) {
		this.experienceService = experienceService;
		this.galleryService = galleryService;
	}
	
	public List<String> locations = Arrays.asList("厳原港近辺", "比田勝港近辺", "対馬空港近辺");
	public List<String> categories = Arrays.asList("boating", "climbing", "cooking", "fishing");
	public List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	
	@RequestMapping(value="/experience")
	@Transactional(readOnly = true)
	public String experience(Model model) {
		List<Experience> experiences = experienceService.findAll();
		model.addAttribute("experiences", experiences);
		return "experiences";
	}
	
	@RequestMapping(value="/experience/update")
	@Transactional(readOnly = false)
	public String update(
			@Nonnull @RequestParam(value = "id", required = true) final long experienceId,
			@Nonnull @RequestParam(value = "action", required = true) final String action,
			Model model) {
		
		this.fk = experienceId;
		model.addAttribute("fk", this.fk);
		
		if (action.equalsIgnoreCase("update")) {
			return updateForm(experienceId, model);
		} else if (action.equalsIgnoreCase("delete")) {
			// If there are images associated with this experience, those images are also be deleted at the same time.
			List<Gallery> gallerys = galleryService.findByFkByCategory(experienceId, "experience");
			for(Gallery gallery : gallerys) {
				galleryService.deleteGallery(gallery.getId());
			}
			
			experienceService.deleteExperience(experienceId);
		}
		
		List<Experience> experiences = experienceService.findAll();
		model.addAttribute("experiences", experiences);
		return "experiences";
	}
	
//	Load experience to update
	@RequestMapping(value="experience/updated", method=RequestMethod.GET)
    public String updateForm(
    		@Nonnull @RequestParam(value = "id", required = true) final long experienceId,
    		Model model) {
		Experience experience = experienceService.findOne(experienceId);
		
		this.fk = experienceId;
		model.addAttribute("fk", this.fk);
		
		List<Gallery> gallerys = galleryService.findByFkByCategory(experienceId, "experience");
		if (gallerys != null) {
			System.out.println("size: " + gallerys.size());
		} else {
			System.out.println("cannot find any image by this fk and experienceid");
		}
		
		
		model.addAttribute("categories", categories);
		
		model.addAttribute("gallerys", gallerys);
        model.addAttribute("experience", experience);
        return "update-experience";
    }
	
	@RequestMapping(value="/experience/updated", method=RequestMethod.POST)
    public String updateSubmit(@ModelAttribute Experience experience, Model model) {
        model.addAttribute("experience", experience);
        
        experienceService.save(experience);
        
        List<Experience> experiences = experienceService.findAll();
		model.addAttribute("experiences", experiences);
		return "experiences";
    }
	
//	Add new experience
	@RequestMapping(value="/experience/new", method=RequestMethod.GET)
    public String experienceForm(Model model) {
        model.addAttribute("experience", new Experience());
        return "new-experience";
    }

	@RequestMapping(value="/experience/new", method=RequestMethod.POST)
    public String experienceSubmit(@ModelAttribute Experience experience, Model model) {
        model.addAttribute("experience", experience);
        
        experienceService.save(experience);
        
        return "new-experience-result";
    }
	
	/*
	 * Update image
	 */
	@RequestMapping(value="/experience/image")
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
		
		List<Experience> experiences = experienceService.findAll();
		model.addAttribute("experiences", experiences);
		return "experiences";
	}
	
	@RequestMapping(value="/experience/search", method=RequestMethod.GET)
	@Transactional(readOnly = true)
	public String searchExperience(
			Model model) {
		model.addAttribute("experience", new Experience());
		List<Experience> experiences = experienceService.findAll();
		model.addAttribute("experiences", experiences);
		model.addAttribute("locations", locations);
		model.addAttribute("categories", categories);
		model.addAttribute("numbers", numbers);
		
		Map<Long, Long> experienceGallery = new HashMap<Long, Long>();
		for(int i = 0; i < experiences.size(); i++) {
			List<Gallery> galleries = galleryService.findByFkByCategory(experiences.get(i).getId(), "experience");
			if (!galleries.isEmpty()) {
				experienceGallery.put(experiences.get(i).getId(), galleries.get(0).getId());
			}
		}
		
		model.addAttribute("experiences", experiences);
		model.addAttribute("experienceGallery", experienceGallery);
		
		return "experience-search";
	}
	
	@RequestMapping(value="/experience/search", method=RequestMethod.POST)
	@Transactional(readOnly = true)
	public String searchExperienceResult(@ModelAttribute Experience experience, Model model) {
        model.addAttribute("experience", experience);
        
        String location = experience.getLocation();
        String category = experience.getCategory();
        
        int adult = experience.getAdult();
        int teenager = experience.getTeenager();
        int infant = experience.getInfant();
        if (location.isEmpty() || location == "") {
			location = null;
		}
        if (category.isEmpty() || category == "") {
        	category = null;
		}
        
		List<Experience> experiences = experienceService.findByCapacity(location, category, adult, teenager, infant);
		
		model.addAttribute("experiences", experiences);
		model.addAttribute("locations", locations);
		model.addAttribute("categories", categories);
		model.addAttribute("numbers", numbers);
		
		Map<Long, Long> experienceGallery = new HashMap<Long, Long>();
		for(int i = 0; i < experiences.size(); i++) {
			List<Gallery> galleries = galleryService.findByFkByCategory(experiences.get(i).getId(), "experience");
			if (!galleries.isEmpty()) {
				experienceGallery.put(experiences.get(i).getId(), galleries.get(0).getId());
			}
		}
		
		model.addAttribute("experiences", experiences);
		model.addAttribute("experienceGallery", experienceGallery);
		return "experience-search";
	}
}
