package com.uniovi.controllers;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.services.PublicationsService;
import com.uniovi.services.UsersService;

@Controller
public class PublicationController {
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private PublicationsService publicationsService;
	
	@RequestMapping("/publication/create")
	public String createPublication(Model model) {
		model.addAttribute("publication", new Publication());
		return "publication/create";
	}
	
	@RequestMapping(value="/publication/create",method=RequestMethod.POST)
	public String createPublication(Model model, Principal principal, @ModelAttribute Publication publication) {
		
		String email=principal.getName();
		User currentUser=usersService.getUserByEmail(email);
		
		Date fecha=publicationsService.getActualDate();
		
		publication.setUser(currentUser);
		publication.setFecha(fecha);
		
		publicationsService.savePublication(publication);
		
		return "publication/create";
	}
	
	@RequestMapping("/publication/list")
	public String listPublications(Model model) {
		
		List<Publication> publications=publicationsService.getPublications();
		
		model.addAttribute("publicationsList", publications);
		
		return "publication/list";
	}
}
