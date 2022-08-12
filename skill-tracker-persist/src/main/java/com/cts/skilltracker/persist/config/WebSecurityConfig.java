package com.cts.skilltracker.persist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/v2/api-docs", "/configuration/**", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui.html", "/swagger-ui", "/webjars/**", "**/api-docs/**","/swagger-ui/**","/api/v1/user/createUser").permitAll();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers("/v2/api-docs", "/configuration/**", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui.html", "/swagger-ui", "/webjars/**", "**/api-docs/**","/swagger-ui/**","/api/v1/user/createUser");
	}
}