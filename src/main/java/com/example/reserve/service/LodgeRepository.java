package com.example.reserve.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.reserve.domain.Lodge;

public interface LodgeRepository extends CrudRepository<Lodge, Long> {

    List<Lodge> findByName(String name);
}
