package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.uniovi.entities.Petition;
import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long>{
	
	@Modifying 
	@Transactional
	@Query("UPDATE User SET sendPetition = ?1 WHERE id = ?2")
	void updateResend(Boolean resend, Long id); //modifica el reenvio de la nota en el repositorio
	
	@Modifying 
	@Transactional
	@Query("UPDATE User SET petitions = ?1 WHERE id = ?2")
	void updatePetitionsForUser(List<Petition> peticiones, Long id);
	
	User findByEmail(String email);
	
	Page<User> findAll(Pageable pageable); 
	
	@Query("SELECT r FROM User r WHERE (LOWER(r.email) LIKE LOWER(?1) OR LOWER(r.name) LIKE LOWER(?1))")
	Page<User> searchByEmailAndName(Pageable pageable, String seachtext); 
	
	
}
