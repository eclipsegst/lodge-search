package com.example.reserve.web;


import java.util.List;
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

}
