package com.ecom.ecomDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EcomDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomDemoApplication.class, args);
	}

}
