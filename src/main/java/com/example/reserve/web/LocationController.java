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

import com.example.reserve.domain.Location;
import com.example.reserve.service.LocationService;

@Controller
public class LocationController{
	@Autowired
	private final LocationService locationService;

	@Autowired
	public LocationController(
			@Nonnull final LocationService locationService
			) {
		this.locationService = locationService;
	}
	
	@RequestMapping(value="/location")
	@Transactional(readOnly = true)
	public String location(Model model) {
		List<Location> locations = locationService.findAll();
		model.addAttribute("locations", locations);
		return "locations";
	}
	
	@RequestMapping(value="/location/update")
	@Transactional(readOnly = false)
	public String update(
			@Nonnull @RequestParam(value = "id", required = true) final long locationId,
			@Nonnull @RequestParam(value = "action", required = true) final String action,
			Model model) {
		
		if (action.equalsIgnoreCase("update")) {
			return updateForm(locationId, model);
		} else if (action.equalsIgnoreCase("delete")) {
			locationService.deleteLocation(locationId);
		}
		
		List<Location> locations = locationService.findAll();
		model.addAttribute("locations", locations);
		return "locations";
	}
	
//	Load location to update
	@RequestMapping(value="location/updated", method=RequestMethod.GET)
    public String updateForm(
    		@Nonnull @RequestParam(value = "id", required = true) final long locationId,
    		Model model) {
		Location location = locationService.findOne(locationId);
		
        model.addAttribute("location", location);
        return "update-location";
    }
	
	@RequestMapping(value="/location/updated", method=RequestMethod.POST)
    public String updateSubmit(@ModelAttribute Location location, Model model) {
        model.addAttribute("location", location);
        
        locationService.save(location);
        
        List<Location> locations = locationService.findAll();
		model.addAttribute("locations", locations);
		return "locations";
    }
	
//	Add new location
	@RequestMapping(value="/location/new", method=RequestMethod.GET)
    public String locationForm(Model model) {
        model.addAttribute("location", new Location());
        return "new-location";
    }

	@RequestMapping(value="/location/new", method=RequestMethod.POST)
    public String locationSubmit(@ModelAttribute Location location, Model model) {
        model.addAttribute("location", location);
        
        locationService.save(location);
        
        return "new-location-result";
    }

}
