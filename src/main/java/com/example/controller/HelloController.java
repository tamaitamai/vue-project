package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.HelloService;


@RestController
public class HelloController {
	@Autowired
	private HelloService helloService;
	
	@GetMapping("/hello")
	public String hello() {
		return "hello World";
	}
}
