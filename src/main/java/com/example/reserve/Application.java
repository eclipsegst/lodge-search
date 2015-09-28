package com.example.reserve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.example.reserve.domain.Lodge;
import com.example.reserve.repository.LodgeRepository;

import org.springframework.boot.CommandLineRunner;
import java.util.*;

@EnableWebMvc
//@EnableAutoConfiguration
@SpringBootApplication
//@ComponentScan({"controller", "com.example.reserve"})
@EntityScan()

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class Application extends SpringBootServletInitializer implements CommandLineRunner{

	@Autowired
	LodgeRepository lodgeRepository;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("start to run...");
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    @Override
    public void run(String... strings) throws Exception {

    }
}
