package com.example.reserve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.reserve.domain.Customer;
import com.example.reserve.domain.Lodge;
import com.example.reserve.service.CustomerRepository;
import com.example.reserve.service.LodgeRepository;

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

public class Application implements CommandLineRunner{

	@Autowired
    CustomerRepository repository;
	@Autowired
	LodgeRepository lodgeRepository;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("start to run...");
    }
    
    @Override
    public void run(String... strings) throws Exception {
        // save a couple of customers
        repository.save(new Customer("Jack", "Bauer"));
        repository.save(new Customer("Chloe", "O'Brian"));
        repository.save(new Customer("Kim", "Bauer"));
        repository.save(new Customer("David", "Palmer"));
        repository.save(new Customer("Michelle", "Dessler"));
        
//        lodgeRepository.save(new Lodge("lodge z", "description z", "location z"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer by ID
        Customer customer = repository.findOne(1L);
        System.out.println("Customer found with findOne(1L):");
        System.out.println("--------------------------------");
        System.out.println(customer);
        System.out.println();

        // fetch customers by last name
        System.out.println("Customer found with findByLastName('Bauer'):");
        System.out.println("--------------------------------------------");
        for (Customer bauer : repository.findByLastName("Bauer")) {
            System.out.println(bauer);
        }
        
        Iterable<Lodge> lodges = lodgeRepository.findAll();
        for(Lodge l : lodges) {
        	System.out.println(l.toString());
        }
    }
}
