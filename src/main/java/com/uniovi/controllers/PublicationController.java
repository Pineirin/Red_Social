package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@RequestMapping(value = "/publication/create", method = RequestMethod.POST)
	public String createPublication(Model model, Principal principal, @ModelAttribute Publication publication) {

		String email = principal.getName();
		User currentUser = usersService.getUserByEmail(email);

		publication.setUser(currentUser);

		publicationsService.savePublication(publication);

		return "redirect:/publication/list";
	}

	@RequestMapping("/publication/list")
	public String listPublications(Model model, Pageable pageable, String searchText) {

		Page<Publication> publications = publicationsService.getPublications(pageable);

		if (searchText != null && !searchText.isEmpty()) {
			publications = publicationsService.searchPublicationsByUserTitleDescription(pageable, searchText);
		} else {
			publications = publicationsService.getPublications(pageable);
		}

		model.addAttribute("publicationsList", publications);
		model.addAttribute("page", publications);
		model.addAttribute("searchText", searchText);

		return "publication/list";
	}
}
