package com.uniovi.services;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Publication;
import com.uniovi.repositories.PublicationsRepository;

@Service
public class PublicationsService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PublicationsRepository publicationsRepository;

	public void savePublication(Publication publication) {
		publicationsRepository.save(publication);
		log.info("User: " + publication.getUser().getEmail() + "created the publication: " + publication.getTitle());
	}

	public Page<Publication> getPublications(Pageable pageable) {
		Page<Publication> publications = new PageImpl<Publication>(new LinkedList<Publication>());
		publications = publicationsRepository.findAll(pageable);
		return publications;
	}

	public Page<Publication> searchPublicationsByUserTitleDescription(Pageable pageable, String searchText) {
		Page<Publication> publications = new PageImpl<Publication>(new LinkedList<Publication>());
		searchText = "%" + searchText + "%";
		publications = publicationsRepository.searchPublicationsByUserTitleDescription(pageable, searchText);
		return publications;
	}

}
