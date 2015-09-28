package com.example.reserve.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("/")
    public String getHomePage() {
        LOGGER.debug("Getting admin page");
        return "home";
    }

}