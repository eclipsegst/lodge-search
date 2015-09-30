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

import com.example.reserve.domain.UserInfo;
import com.example.reserve.service.UserInfoService;

@Controller
public class UserInfoController{
	@Autowired
	private final UserInfoService userInfoService;

	protected long userid = -1L;
	protected String category;
	
	@Autowired
	public UserInfoController(
			@Nonnull final UserInfoService userInfoService
			) {
		this.userInfoService = userInfoService;
	}
	
	@RequestMapping(value="/userInfo")
	@Transactional(readOnly = true)
	public String userInfo(Model model) {
		List<UserInfo> userInfos = userInfoService.findAll();
		model.addAttribute("userInfos", userInfos);
		return "userInfo";
	}
	
	@RequestMapping(value="/userInfo/update")
	@Transactional(readOnly = false)
	public String update(
			@Nonnull @RequestParam(value = "id", required = true) final long userInfoId,
			@Nonnull @RequestParam(value = "action", required = true) final String action,
			Model model) {
		
		this.userid = userInfoId;
		model.addAttribute("userid", this.userid);
		
		if (action.equalsIgnoreCase("update")) {
			return updateForm(userInfoId, model);
		} else if (action.equalsIgnoreCase("delete")) {
			userInfoService.deleteUserInfo(userInfoId);
		}
		
		List<UserInfo> userInfos = userInfoService.findAll();
		model.addAttribute("userInfos", userInfos);
		return "userInfo";
	}
	
//	Load userInfo to update
	@RequestMapping(value="userInfo/updated", method=RequestMethod.GET)
    public String updateForm(
    		@Nonnull @RequestParam(value = "id", required = true) final long userInfoId,
    		Model model) {
		UserInfo userInfo = userInfoService.findOne(userInfoId);
		
		this.userid = userInfoId;
		model.addAttribute("userid", this.userid);
		
        model.addAttribute("userInfo", userInfo);
        return "update-userInfo";
    }
	
	@RequestMapping(value="/userInfo/updated", method=RequestMethod.POST)
    public String updateSubmit(@ModelAttribute UserInfo userInfo, Model model) {
        model.addAttribute("userInfo", userInfo);
        
        userInfoService.save(userInfo);
        
        List<UserInfo> userInfos = userInfoService.findAll();
		model.addAttribute("userInfos", userInfos);
		return "userInfo";
    }
	
//	Add new userInfo
	@RequestMapping(value="/userInfo/new", method=RequestMethod.GET)
	public String image(
			@Nonnull @RequestParam(value = "userid", required = true) final long userid,
			Model model
			) {
		model.addAttribute("userInfo", new UserInfo());
		model.addAttribute("userid", userid);
		this.userid = userid;
		return "new-userInfo";
	}
	
	@RequestMapping(value="/userInfo/new", method=RequestMethod.POST)
    public String userInfoSubmit(@ModelAttribute UserInfo userInfo, Model model) {
        model.addAttribute("userInfo", userInfo);
        
        userInfo.setUserid(userid);
        userInfoService.save(userInfo);
        
        return "new-userInfo-result";
    }

}
