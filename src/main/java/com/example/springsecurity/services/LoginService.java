package com.example.springsecurity.services;

import org.springframework.security.core.Authentication;

public interface LoginService {

	Authentication authenticate(String authorization);

}
