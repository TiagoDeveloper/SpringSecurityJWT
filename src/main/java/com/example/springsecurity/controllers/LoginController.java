package com.example.springsecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.services.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping
	public Authentication login(@RequestHeader("Authorization") String authorization) {
		return this.loginService.authenticate(authorization);
	}	

}
