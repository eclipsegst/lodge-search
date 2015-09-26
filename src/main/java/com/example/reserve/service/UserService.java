package com.example.reserve.service;

import java.util.Collection;
import java.util.Optional;

import com.example.reserve.domain.security.User;
import com.example.reserve.domain.security.UserCreateForm;

public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(UserCreateForm form);
}

