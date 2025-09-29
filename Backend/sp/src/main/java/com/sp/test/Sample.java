package com.sp.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Sample {

	@GetMapping("/get")
	public String getResult() {
		return "Welcome to Spring Boot";
	}
}
