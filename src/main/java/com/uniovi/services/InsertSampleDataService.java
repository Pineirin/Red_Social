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
		User user3 = new User("francisco12@live.com", "Francisco");
		user3.setPassword("123456");
		User user4 = new User("rodrigo13@hotmail.com", "Rodrigo");
		user4.setPassword("123456");
		User user5 = new User("pepe@live.com", "Pepe");
		user5.setPassword("123456");
		User user6 = new User("berto@hotmail.com", "Alberto");
		user6.setPassword("123456");
	
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
	}

}
