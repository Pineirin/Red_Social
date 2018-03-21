package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Petition;
import com.uniovi.entities.User;
import com.uniovi.repositories.PetitionsRepository;

@Service
public class PetitionsService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PetitionsRepository petitionsRepository;

	public List<Petition> getPetitions() {
		List<Petition> peticiones = new ArrayList<Petition>();
		petitionsRepository.findAll().forEach(peticiones::add);
		return peticiones;
	}

	public Page<Petition> getPetition(Pageable pageable) {
		Page<Petition> peticiones = new PageImpl<Petition>(new LinkedList<Petition>());
		peticiones = petitionsRepository.findAll(pageable);
		return peticiones;
	}

	public Petition getPetition(Long id) {
		return petitionsRepository.findOne(id);
	}

	public Petition getPetition(User userOrigen, User userDestino) {
		return petitionsRepository.getPetition(userOrigen, userDestino);
	}

	public void addPetition(Petition petition) {
		petitionsRepository.save(petition);
		log.info("User: " + petition.getUserOrigen().getEmail() + " sent a petition to user: " + petition.getUserDestino().getEmail());
	}

	public void deletePetition(Long id) {
		petitionsRepository.delete(id);
	}

	public void updateStatus(String status, Long id) {
		petitionsRepository.updateStatus(status, id);
		Petition petition = petitionsRepository.findById(id);
		log.info("User: " + petition.getUserDestino().getEmail() + " accepted the petition of the user: " + petition.getUserOrigen().getEmail());
	}

	// +++++++++++++++++
	public void sendPetition(User userOrigen, User userDestino) {

		Petition peticion = new Petition(userOrigen, userDestino);

		addPetition(peticion);// añadimos la petición al repositorio
	}

	public void cancelarPetition(User userOrigen, User userDestino) {

		Petition petition = getPetition(userOrigen, userDestino);

		deletePetition(petition.getId());
		
		log.info("User: " + petition.getUserOrigen().getEmail() + " canceled the petition to user: " + petition.getUserDestino().getEmail());
	}

	public List<Petition> searchPetitionByDestinationUser(User userDestino) {
		return petitionsRepository.searchPetitionsForDestinationUser(userDestino);
	}
	// +++++++++++++++++++

	public List<Petition> searchPetitionByOriginUserAndDestinationUser(User userOrigin, User userDestination) {
		return petitionsRepository.searchPetitionByOriginUserAndDestinationUser(userOrigin, userDestination);
	}

	/*
	 * public Page<Petition> searchPetitionsForOriginUser(Pageable pageable, User
	 * userOrigen) {
	 * 
	 * return petitionsRepository.searchPetitionsForOriginUser(pageable,userOrigen);
	 * 
	 * }
	 * 
	 * public Page<User> searchUsuariosDestinosForUser(Pageable pageable, User
	 * user){
	 * 
	 * return petitionsRepository.searchUsuariosDestinosForUser(pageable,user); }
	 */

}
