package com.example.reserve;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
//@ComponentScan
@ComponentScan("com.example")

public class ServerConfiguration extends WebMvcAutoConfiguration{
//	@Bean
//	public FilterRegistrationBean filterRegistrationBean() {
//	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//	    registrationBean.setFilter(characterEncodingFilter);
//	    characterEncodingFilter.setEncoding("UTF-8");
//	    characterEncodingFilter.setForceEncoding(true);
//	    registrationBean.setOrder(Integer.MIN_VALUE);
//	    registrationBean.addUrlPatterns("/*");
//	    return registrationBean;
//	}
}