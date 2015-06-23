package common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableAutoConfiguration
@SpringBootApplication
public class LodgeSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(LodgeSearchApplication.class, args);
        System.out.println("start to run...");
    }
}
