package com.dooservice.safework;

import org.springframework.boot.SpringApplication;

public class TestSafeworkApplication {

	public static void main(String[] args) {
		SpringApplication.from(SafeworkApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
