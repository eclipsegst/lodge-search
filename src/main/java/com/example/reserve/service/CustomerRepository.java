package com.example.reserve.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.reserve.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}
