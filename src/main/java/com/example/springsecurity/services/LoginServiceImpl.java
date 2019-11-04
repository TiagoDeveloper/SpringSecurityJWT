package com.example.springsecurity.services;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTServiceImpl jwtService;
	
	@Override
	public Authentication authenticate(String authorization) {
		
		if (authorization == null || !authorization.toLowerCase().startsWith("basic ")) {
			throw new AuthenticationServiceException("Authentication FAIL");
		}
		String[] tokens = new String[2];
		try {
			tokens = extractAndDecodeHeader(authorization);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert tokens.length == 2;

		String username = tokens[0];
		User user = new User(username,tokens[1], Collections.emptyList());
		Authentication auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user, tokens[1]));
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String token = this.jwtService.gerarToken(auth);
		System.out.println(token);
		
		return auth;
	}
	
	
	
	private String[] extractAndDecodeHeader(String header)throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}

		String token = new String(decoded, "UTF-8");

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}
}
