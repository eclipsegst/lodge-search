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

import com.example.reserve.domain.Calendar;
import com.example.reserve.service.CalendarService;

@Controller
public class CalendarController{
	@Autowired
	private final CalendarService calendarService;

	protected long fk = -1L;
	protected String category;
	
	@Autowired
	public CalendarController(
			@Nonnull final CalendarService calendarService
			) {
		this.calendarService = calendarService;
	}
	
	@RequestMapping(value="/calendar")
	@Transactional(readOnly = true)
	public String calendar(Model model) {
		List<Calendar> calendars = calendarService.findAll();
		model.addAttribute("calendars", calendars);
		return "calendar";
	}
	
	@RequestMapping(value="/calendar/update")
	@Transactional(readOnly = false)
	public String update(
			@Nonnull @RequestParam(value = "id", required = true) final long calendarId,
			@Nonnull @RequestParam(value = "action", required = true) final String action,
			Model model) {
		
		this.fk = calendarId;
		model.addAttribute("fk", this.fk);
		
		if (action.equalsIgnoreCase("update")) {
			return updateForm(calendarId, model);
		} else if (action.equalsIgnoreCase("delete")) {
			calendarService.deleteCalendar(calendarId);
		}
		
		List<Calendar> calendars = calendarService.findAll();
		model.addAttribute("calendars", calendars);
		return "calendar";
	}
	
//	Load calendar to update
	@RequestMapping(value="calendar/updated", method=RequestMethod.GET)
    public String updateForm(
    		@Nonnull @RequestParam(value = "id", required = true) final long calendarId,
    		Model model) {
		Calendar calendar = calendarService.findOne(calendarId);
		
		this.fk = calendarId;
		model.addAttribute("fk", this.fk);
		
        model.addAttribute("calendar", calendar);
        return "update-calendar";
    }
	
	@RequestMapping(value="/calendar/updated", method=RequestMethod.POST)
    public String updateSubmit(@ModelAttribute Calendar calendar, Model model) {
        model.addAttribute("calendar", calendar);
        
        calendarService.save(calendar);
        
        List<Calendar> calendars = calendarService.findAll();
		model.addAttribute("calendars", calendars);
		return "calendar";
    }
	
//	Add new calendar
	@RequestMapping(value="/calendar/new", method=RequestMethod.GET)
	public String image(
			@Nonnull @RequestParam(value = "fk", required = true) final long fk,
			@Nonnull @RequestParam(value = "category", required = true) final String category,
			Model model
			) {
		model.addAttribute("calendar", new Calendar());
		model.addAttribute("fk", fk);
		model.addAttribute("category", category);
		this.fk = fk;
		this.category = category;
		return "new-calendar";
	}
	
	@RequestMapping(value="/calendar/new", method=RequestMethod.POST)
    public String calendarSubmit(@ModelAttribute Calendar calendar, Model model) {
        model.addAttribute("calendar", calendar);
        
        calendar.setFk(fk);
        calendar.setCategory(category);
        calendarService.save(calendar);
        
        return "new-calendar-result";
    }

}
