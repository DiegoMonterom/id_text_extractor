package com.innovatio_software;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IdReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdReaderApplication.class, args);
	}

}
