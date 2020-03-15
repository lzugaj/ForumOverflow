package com.luv2code.forumoverflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ForumOverflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumOverflowApplication.class, args);
	}

}
