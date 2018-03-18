package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.repositories.PetitionsRepository;

@Service
public class PublicationService {

	@Autowired
	private PublicationRepository publicationRepository;
}
