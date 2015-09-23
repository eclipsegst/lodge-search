package com.example.reserve.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.reserve.domain.Calendar;

public interface CalendarRepository extends CrudRepository<Calendar, Long> {

    List<Calendar> findByCloseddate(Date closeddate);
    
    List<Calendar> findByFkEqualsAndCategoryEquals(long fk, String categroy);
}