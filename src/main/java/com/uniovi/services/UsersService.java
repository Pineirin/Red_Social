package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	
	//private List<User> usersList = new LinkedList<User>();
	@Autowired
	private UsersRepository usersRepository;
	
	public List<User> getUsers(){
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}
	
	public void addUser(User user){
		// Si en Id es null le asignamos el ultimo + 1 de la lista
		usersRepository.save(user);
	}
}
