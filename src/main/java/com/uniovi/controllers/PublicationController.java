package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.Petition;
import com.uniovi.entities.PetitionStatus;
import com.uniovi.entities.User;
import com.uniovi.services.PetitionsService;

@Controller
public class PublicationController {

	@Autowired
	private PublicationService publicationService;
	
	@RequestMapping("/publication/petitions")
	public String getPetitions(Model model){

		return "petition/list";
	}
}
