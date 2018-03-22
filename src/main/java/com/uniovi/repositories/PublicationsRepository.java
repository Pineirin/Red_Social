package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Publication;

public interface PublicationsRepository extends CrudRepository<Publication, Long> {

	Page<Publication> findAll(Pageable pageable);

	@Query("SELECT r FROM Publication r WHERE (LOWER(r.user.email) LIKE LOWER(?1) OR LOWER(r.description) LIKE LOWER(?1) OR LOWER (r.title) LIKE LOWER(?1))")
	Page<Publication> searchPublicationsByUserTitleDescription(Pageable pageable, String searchText);
	
	@Query("SELECT p FROM Publication p WHERE p.user.email = ?1")
	Page<Publication> searchUserPublications(Pageable pageable, String userEmail);
}
