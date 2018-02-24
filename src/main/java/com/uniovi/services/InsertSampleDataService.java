package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;


public class InsertSampleDataService {
	
	@Autowired
	private UsersService usersService;
	@PostConstruct
	public void init() {
	User user1 = new User("adripc@live.com", "Adrian");
	user1.setPassword("123456");
	User user2 = new User("Juan@hotmail.com", "Juan");
	user2.setPassword("123456");

	
	usersService.addUser(user1);
	usersService.addUser(user2);
	}

}
