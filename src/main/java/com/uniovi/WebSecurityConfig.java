package com.uniovi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Configuration
	@Order(1)
	public static class WebSecurityConfig1 extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.csrf().disable().antMatcher("/login/**")
	          .authorizeRequests()
	          //.antMatchers("/css/**", "/img/**", "/script/**", "/", "/signup").permitAll()
	          .anyRequest()
	          .hasRole("USER")

			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.defaultSuccessUrl("/log")
			.failureUrl("/login?error")

			.and()
			.logout()
			.logoutSuccessUrl("/logoutFromLogin")
			.permitAll();
		}

	}

	@Configuration
	@Order(2)
	public class WebSecurityConfig2 extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.csrf().disable().antMatcher("/admin/login/**")
	          .authorizeRequests()
	          .anyRequest()
	          .hasRole("ADMIN")

			.and()
			.formLogin()
			.loginPage("/admin/login")
			.permitAll()
			.defaultSuccessUrl("/admin/log")
			.failureUrl("/admin/login?error")

			.and()
			.logout()
			.logoutSuccessUrl("/logoutFromLogin")
			.permitAll();
		}
	}

}



