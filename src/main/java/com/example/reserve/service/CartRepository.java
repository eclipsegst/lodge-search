package com.example.reserve.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.reserve.domain.Cart;

public interface CartRepository extends CrudRepository<Cart, Long> {

    List<Cart> findByShoppingid(long shoppingid);

}