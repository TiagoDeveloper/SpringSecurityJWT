package com.example.springsecurity.config;

import java.util.Arrays;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private BearerAuthenticationFilter bearerAuthenticationFilter;	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//a ordem influencia aqui
		http
		.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/login").permitAll()
			.anyRequest().authenticated()
			.and()
			.csrf().disable().cors();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
	    return new BCryptPasswordEncoder(11);
	}
	@Bean
	public FilterRegistrationBean<Filter> myFilterRegistration() {
        FilterRegistrationBean<Filter> frb = new FilterRegistrationBean<Filter>();
        frb.setFilter(bearerAuthenticationFilter);
        frb.setUrlPatterns(Arrays.asList("/api/*"));
        return frb;
    }
}
