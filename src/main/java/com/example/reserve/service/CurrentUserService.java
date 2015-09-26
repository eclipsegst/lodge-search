package com.example.reserve.service;

import com.example.reserve.domain.security.CurrentUser;

public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, Long userId);

}
