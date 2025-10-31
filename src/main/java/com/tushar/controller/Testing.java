package com.tushar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testing {

	@GetMapping("/hello")
	String Checking() {
		return "I am just checking";
	}
}
