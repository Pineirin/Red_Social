package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

@RestController
public class UserController {
	
	@Autowired
	private UsersService usersService;
	
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	public String loginUser(){
		return "User Login";
	}
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	public String registerUser(@ModelAttribute User user){
		usersService.addUser(user);
		return "Se ha registrado correctamente";
	}
	
	@RequestMapping("/user/list")
	public String getList(){
		return usersService.getUsers().toString();
	}
	
}
