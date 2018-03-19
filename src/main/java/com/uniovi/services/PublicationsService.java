package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Publication;
import com.uniovi.repositories.PublicationsRepository;

@Service
public class PublicationsService {
	
	@Autowired
	private PublicationsRepository publicationsRepository;
	
	public void savePublication(Publication publication) {
		publicationsRepository.save(publication);
	}
	
	public List<Publication> getPublications() {
		List<Publication> publications = new ArrayList<Publication>();
		publicationsRepository.findAll().forEach(publications::add);
		return publications;
	}

}
