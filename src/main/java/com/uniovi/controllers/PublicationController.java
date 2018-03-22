package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(value = "/publication/create", method = RequestMethod.POST)
	public String createPublication(Model model, Principal principal, @ModelAttribute Publication publication) {

		String email = principal.getName();
		User currentUser = usersService.getUserByEmail(email);

		publication.setUser(currentUser);

		publicationsService.savePublication(publication);

		return "redirect:/publication/myList";
	}

	@RequestMapping("/publications/list/{id}")
	public String listPublications(Model model, Pageable pageable, String searchText, @PathVariable Long id) {	
		
		User user = usersService.getUser(id);
	
		Page<Publication> publications = publicationsService.getUserPublications(pageable, user);

		model.addAttribute("publicationsList", publications);
		model.addAttribute("page", publications);
		model.addAttribute("searchText", "");

		return "publication/list";
	}
	
	@RequestMapping("/publications/list")
	public String listMyPublications(Model model, Pageable pageable, String searchText) {	
		
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail=auth.getName();
	
		User user = usersService.getUserByEmail(userEmail);
	
		Page<Publication> publications = publicationsService.getUserPublications(pageable, user);

		model.addAttribute("publicationsList", publications);
		model.addAttribute("page", publications);
		model.addAttribute("searchText", "");

		return "publication/list";
	}
}
