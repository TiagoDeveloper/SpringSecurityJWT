package com.example.springsecurity.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl {

	public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";
	public static final String TOKEN_ISSUER = "secure-api";
	public static final String TOKEN_AUDIENCE = "secure-app";
	
	
	
	public String gerarToken(Authentication authResult) {
		
		User user = ((User) authResult.getPrincipal());
	        
	    List<String> roles = user.getAuthorities()
	            .stream()
	            .map(GrantedAuthority::getAuthority)
	            .collect(Collectors.toList());
		
		byte[] signingKey = JWT_SECRET.getBytes();
        return Jwts.builder()
        		.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
        		.setHeaderParam("typ", TOKEN_TYPE)
        		.setIssuer(TOKEN_ISSUER)
        		.setAudience(TOKEN_AUDIENCE)
        		.setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong("3600000")))
                .claim("rol", roles)
                .compact();
	}
}
