package com.example.LoginBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LoginBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginBoardApplication.class, args);
	}

}
