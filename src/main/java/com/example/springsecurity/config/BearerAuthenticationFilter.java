package com.example.springsecurity.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.springsecurity.services.JWTServiceImpl;

@Component
public class BearerAuthenticationFilter implements Filter {

	@Autowired
	private JWTServiceImpl jwtService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//TODO verifica o token
		System.out.println(jwtService);
		chain.doFilter(request, response);
		
	}

}
