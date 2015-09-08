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
		
		Lodge lodge = lodgeService.findOne(lodgeId);
		
		this.fk = lodgeId;
		model.addAttribute("fk", this.fk);
		
		if (action.equalsIgnoreCase("update")) {
			return updateForm(lodgeId, model);
		} else if (action.equalsIgnoreCase("delete")) {
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
		
		System.out.println("lodgeId: " + lodgeId);
		Gallery gallery = galleryService.findOne(1L);
		System.out.println(gallery.getTitle());
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

}
