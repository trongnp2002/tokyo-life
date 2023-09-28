package com.webshop.tokyolife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.webshop.tokyolife")
@EnableScheduling()
@EntityScan(basePackages =  {"com.webshop.tokyolife.*"})
@EnableWebMvc
public class TokyoLifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokyoLifeApplication.class, args);
	}

}
