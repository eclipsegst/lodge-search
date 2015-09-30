package com.example.reserve.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.reserve.domain.UserInfo;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

    List<UserInfo> findByName(String name);
    
    List<UserInfo> findByUseridEquals(long userid);
}