package com.uniovi.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String getAdminList(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		
		User currentUser = usersService.getUserByEmail(email);

		List<User> users = usersService.getUsers();

		model.addAttribute("usersList", users);
		model.addAttribute("currentUser", currentUser);
		return "admin/list";
	}
	
	@RequestMapping("/admin/delete/{id}")
	public String deleteUser(Model model, @PathVariable Long id) {
		
		usersService.deleteUser(id);
		
		return "redirect:/admin/list/update";
	}
	
	@RequestMapping("/admin/list/update")
	public String updateAdminList(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		
		User currentUser = usersService.getUserByEmail(email);

		List<User> users = usersService.getUsers();
		
		model.addAttribute("usersList", users);
		model.addAttribute("currentUser", currentUser);
		
		return "admin/list :: tableUsers";
	}
}
