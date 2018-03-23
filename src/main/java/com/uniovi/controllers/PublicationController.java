package com.uniovi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String createPublication(Model model, Principal principal, @ModelAttribute Publication publication, @RequestParam("file") MultipartFile file) {	
		
		String email = principal.getName();
		User currentUser = usersService.getUserByEmail(email);
	
		publication.setUser(currentUser);
		
		
		InputStream is = null;
		try {
			is = file.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		publicationsService.savePublication(publication);
		
		try {
			Files.copy(is, Paths.get("src/main/resources/static/fotossubidas/" + publication.getId() + ".png"),StandardCopyOption.REPLACE_EXISTING );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return "redirect:/publication/list";
	}

	@RequestMapping("/publication/list/{id}")
	public String listPublications(Model model, Pageable pageable, String searchText, @PathVariable Long id) {	
		
		User user = usersService.getUser(id);
	
		Page<Publication> publications = publicationsService.getUserPublications(pageable, user);

		model.addAttribute("publicationsList", publications);
		model.addAttribute("page", publications);
		model.addAttribute("searchText", "");

		return "publication/list";
	}
	
	@RequestMapping("/publication/list")
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
