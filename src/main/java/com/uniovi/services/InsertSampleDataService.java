package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private RolesService rolesService;

	@PostConstruct
	public void init() {
		User user1 = new User("adripc@live.com", "Adrian");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[1]);
		
		User user7 = new User("a1@live.com", "Adrian");
		user7.setPassword("123456");
		user7.setRole(rolesService.getRoles()[0]);
		
		User user8 = new User("a2@live.com", "Adrian");
		user8.setPassword("123456");
		user8.setRole(rolesService.getRoles()[0]);
		
		User user9 = new User("a3@live.com", "Adrian");
		user9.setPassword("123456");
		user9.setRole(rolesService.getRoles()[0]);
		
		User user10 = new User("a4@live.com", "Adrian");
		user10.setPassword("123456");
		user10.setRole(rolesService.getRoles()[0]);
		
		User user11 = new User("a5@live.com", "Adrian");
		user11.setPassword("123456");
		user11.setRole(rolesService.getRoles()[0]);

		User user2 = new User("Juan@hotmail.com", "Juan");
		user2.setPassword("123456");
		user2.setRole(rolesService.getRoles()[0]);
		
		User user3 = new User("francisco12@live.com", "Francisco");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[0]);
		
		User user4 = new User("rodrigo13@hotmail.com", "Rodrigo");
		user4.setPassword("123456");
		user4.setRole(rolesService.getRoles()[0]);
		
		User user5 = new User("pepe@live.com", "Pepe");
		user5.setPassword("123456");
		user5.setRole(rolesService.getRoles()[0]);
		
		User user6 = new User("berto@hotmail.com", "Alberto");
		user6.setPassword("123456");
		user6.setRole(rolesService.getRoles()[0]);

		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);

		usersService.addUser(user7);
		usersService.addUser(user8);
		usersService.addUser(user9);
		usersService.addUser(user10);
		usersService.addUser(user11);
	}

}
