package com.example.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		User user = (User) authentication.getPrincipal();
		//TODO Buscar o usuário no banco
		if(passwordEncoder.matches(user.getPassword(), "$2a$10$psK231bAZUluPlWZn8DCtur2wAQNCbLlwzPE1nNuZfgQsipXfeoQ."))
			return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		else
			throw new BadCredentialsException("Senha inválida.");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
