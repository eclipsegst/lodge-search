package com.example.reserve.service;

import java.util.List;
import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reserve.domain.UserInfo;
import com.example.reserve.repository.UserInfoRepository;

@Service
public class UserInfoService {
	private final UserInfoRepository userInfoRepository;
	
	@Autowired
	public UserInfoService(
			@Nonnull final UserInfoRepository userInfoRepository
			) {
		this.userInfoRepository = userInfoRepository;
	}
	
	public final List<UserInfo> findAll() {
		final List<UserInfo> userInfos = (List<UserInfo>) userInfoRepository.findAll();
		return userInfos;
	}
	
	public final UserInfo findOne(Long userInfoId) {
		final UserInfo userInfo = userInfoRepository.findOne(userInfoId);
		return userInfo;
	}
	
	public final List<UserInfo> findByFkByCategory(Long userid) {
		final List<UserInfo> userInfos = (List<UserInfo>) userInfoRepository.findByUseridEquals(userid);
		return userInfos;
	}
	
	public final void save(UserInfo userInfo) {
		this.userInfoRepository.save(userInfo);
	}
	
	public final void deleteUserInfo(Long userInfoId) {
		final UserInfo userInfo = userInfoRepository.findOne(userInfoId);
		userInfoRepository.delete(userInfo);
	}
}
