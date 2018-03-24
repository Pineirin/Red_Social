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

public interface PetitionsRepository extends CrudRepository<Petition, Long> {

	@Modifying
	@Transactional
	@Query("UPDATE Petition SET status = ?1 WHERE id = ?2")
	void updateStatus(String status, Long id);

	@Query("SELECT p FROM Petition p WHERE p.userDestino = ?1 ORDER BY p.id ASC")
	List<Petition> searchPetitionsForDestinationUser(User user);

	Petition findById(long id);

	Page<Petition> findAll(Pageable pageable);

	@Query("SELECT p " + "FROM User u join u.petitions p " + "where u = ?1 and p.userDestino = ?2")
	Petition getPetition(User userOrigen, User userDestino);
	// @Query("SELECT p FROM Petition p WHERE p.userOrigen = ?1 and p.userDestino =
	// ?2 ORDER BY p.id ASC")

	@Query("SELECT p FROM Petition p WHERE p.userOrigen = ?1 and p.userDestino = ?2 ORDER BY p.id ASC")
	List<Petition> searchPetitionByOriginUserAndDestinationUser(User userOrigin, User userDestination);

	// @Query("SELECT p FROM Petition p WHERE p.userOrigen = ?1")
	// Page<Petition> searchPetitionsForOriginUser(Pageable pageable,User user);

	// @Query("SELECT p.userDestino FROM Petition p WHERE p.userOrigen = ?1")
	// Page<User> searchUsuariosDestinosForUser(Pageable pageable,User user);

    @Query("SELECT p FROM Petition p where (p.userOrigen = ?1 or p.userDestino = ?1) and p.status = 'TERMINADA'")
    Page<Petition> searchPetitionsForUser(Pageable pageable, User currentUser);
	
	@Query("SELECT p FROM Petition p where (p.userOrigen = ?1 or p.userDestino = ?1) and p.status = 'TERMINADA'")
    List<Petition> searchPetitionsForUser(User currentUser);

	@Query("SELECT p FROM Petition p where p.userDestino = ?1 and p.status = 'EN_PROCESO'")
	Page<Petition> searchSentPetitionsForUser(Pageable pageable, User currentUser);

    @Query("SELECT p FROM Petition p WHERE (p.userOrigen = ?1 and p.userDestino = ?2) or (p.userOrigen = ?2 and p.userDestino = ?1)")
	List<Petition> searchPetition(User user, User user2);

    @Query("SELECT p FROM Petition p WHERE p.userOrigen = ?1 and p.userDestino = ?2 and p.status = 'EN_PROCESO'")
	List<Petition> searchSentPetitionsUserOriginUserDestination(User userOrigin, User userDestination);
	
}
