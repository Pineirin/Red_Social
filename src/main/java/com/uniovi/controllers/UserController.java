package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

@Controller
public class UserController {
	
	@Autowired
	private UsersService usersService;
	
	@RequestMapping("/user/goToLogin")
	public String loginUser(){
		return "user/login";
	}
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	public String registerUser(@ModelAttribute User user){
		usersService.addUser(user);
		return "redirect:/user/list";
	}
	
	@RequestMapping("/user/list")
	public String getList(Model model){
		model.addAttribute("userList", usersService.getUsers() );//markList es lo que espera la vista
		return "user/list";
	}
	
	@RequestMapping("/user/goToRegister")
	public String goToRegister(){
		return "user/register";
	}
	
}
