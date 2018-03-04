package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.User;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.LoginFormValidator;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UserController {
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private SignUpFormValidator signUpFormValidator;
	
	@Autowired
	private LoginFormValidator loginFormValidator;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	/*
	 * El usuario accede a goToHomeAfterLogin por URL:
	 * Si el usuario está autentificado le redirije a home
	 * Si el usuario no está autentificado le redirije a login
	 */
	@RequestMapping(value = "/goToHomeAfterLogin", method = RequestMethod.GET)
	public String goToHomeAfterLogin(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.isAuthenticated()) {
			return "redirect:home";
		}
		else {
			return "redirect:login";
		}
	}
	
	@RequestMapping(value = "/goToHomeAfterLogin", method = RequestMethod.POST)
	public String login(@Validated User user, BindingResult result, Model model) {
		loginFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "login";
		}
		securityService.autoLogin(user.getEmail(), user.getPassword());
		return "redirect:user/list";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String setUser(@Validated User user, BindingResult result, Model model) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:user/list";
	}
	
	@RequestMapping("/user/list")
	public String getList(Model model, Pageable pageable){
		
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users=usersService.getUsers(pageable);
		
		model.addAttribute("usersList",users);
		model.addAttribute("page", users);
		return "user/list";
	}
	
	@RequestMapping(value="/user/{id}/sendPetition", method=RequestMethod.GET)
	public String setResendFalse(Model model, @PathVariable Long id){
		usersService.setSendPetition(true, id);
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/user/{id}/cancelPetition", method=RequestMethod.GET)
	public String setResendTrue(Model model, @PathVariable Long id){
		usersService.setSendPetition(false, id);
		return "redirect:/user/list";
	}
	
	@RequestMapping("/user/list/update")
	public String updateList(Model model, Pageable pageable, Principal principal){
		
		Page<User> users = usersService.getUsers(pageable);
		model.addAttribute("usersList", users.getContent() );
		return "user/list :: tableUsers";
	}
	
}
