package com.uniovi.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;

@Controller
public class AdminController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private SecurityService securityService;
	
	@RequestMapping(value = "/admin/login", method = RequestMethod.GET)
	public String adminLogin(Model model) {
		model.addAttribute("user", new User());
		return "admin/login";
	}
	
	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public String adminLogin(Model model, @ModelAttribute User usuario) {
		User user = usersService.getUserByEmail(usuario.getEmail());
		
		if(user != null) {
			if(user.getRole().equals("ROLE_ADMIN")) {
				
				model.addAttribute("errorInAdminLogin", false);
				securityService.autoLogin(usuario.getEmail(), usuario.getPassword());
				return "redirect:/admin/log";
				
			}
		}
		
		model.addAttribute("errorInAdminLogin", true);
		return "admin/login";
	}
	
	@RequestMapping("/admin/log")
	public String adminLog() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("User Admin: " + auth.getName()  +" logged in the application");
		return "redirect:/admin/list";
	}
	
	@RequestMapping("/admin/list")
	public String getAdminList(Model model, Pageable pageable, Principal principal,
			@RequestParam(defaultValue = "", required = false) String searchText) {

		// poner al usuario en linea
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//String email = auth.getName();
		
		//User currentUser = usersService.getUserByEmail(email);

		Page<User> users;
		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUsersByEmailAndName(pageable, searchText);
		} else {
			users = usersService.getUsers(pageable);
		}

		model.addAttribute("usersList", users);
		model.addAttribute("page", users);
		model.addAttribute("searchText", searchText);
		return "admin/list";
	}
}
