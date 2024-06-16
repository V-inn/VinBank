package com.viniciusfk.bankApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.viniciusfk.bankApi")
public class VinBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(VinBankApplication.class, args);
	}
}

