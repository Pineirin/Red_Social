package com.uniovi.repositories;

import java.util.List;
import java.util.Set;

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
	void updateUserPetitions(Set<Petition> peticiones, Long id);
	
	@Query("UPDATE User SET friends = ?1")
	void updateUserFriends(Set<User> friends);
	
	User findByEmail(String email);//Email es lo que se utiliza para el login, es decir le nombre de usuario
	
	Page<User> findAll(Pageable pageable); 
	
	@Query("SELECT r FROM User r WHERE (LOWER(r.email) LIKE LOWER(?1) OR LOWER(r.name) LIKE LOWER(?1))")
	Page<User> searchByEmailAndName(Pageable pageable, String seachtext); 
	
	@Query("SELECT p.userDestino FROM Petition p where p.userOrigen = ?1")
	List<User> searchUsersDestinosForUser(User userOrigin); 
}
