package com.example.reserve.service;

import java.util.List;
import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reserve.domain.Calendar;

@Service
public class CalendarService {
	private final CalendarRepository calendarRepository;
	
	@Autowired
	public CalendarService(
			@Nonnull final CalendarRepository calendarRepository
			) {
		this.calendarRepository = calendarRepository;
	}
	
	public final List<Calendar> findAll() {
		final List<Calendar> calendars = (List<Calendar>) calendarRepository.findAll();
		return calendars;
	}
	
	public final Calendar findOne(Long calendarId) {
		final Calendar calendar = calendarRepository.findOne(calendarId);
		return calendar;
	}
	
	public final List<Calendar> findByFkByCategory(Long fk, String category) {
		final List<Calendar> calendars = (List<Calendar>) calendarRepository.findByFkEqualsAndCategoryEquals(fk, category);
		return calendars;
	}
	
	public final void save(Calendar calendar) {
		this.calendarRepository.save(calendar);
	}
	
	public final void deleteCalendar(Long calendarId) {
		final Calendar calendar = calendarRepository.findOne(calendarId);
		calendarRepository.delete(calendar);
	}
}
