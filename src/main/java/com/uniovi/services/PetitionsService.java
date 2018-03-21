package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

	public void addPetition(Petition peticion) {
		petitionsRepository.save(peticion);
	}

	public void deletePetition(Long id) {
		petitionsRepository.delete(id);
	}

	public void updateStatus(String status, Long id) {
		petitionsRepository.updateStatus(status, id);
	}

	// +++++++++++++++++
	public void sendPetition(User userOrigen, User userDestino) {

		Petition peticion = new Petition(userOrigen, userDestino);

		addPetition(peticion);// añadimos la petición al repositorio
	}

	public void cancelarPetition(User userOrigen, User userDestino) {

		Petition peticion = getPetition(userOrigen, userDestino);

		deletePetition(peticion.getId());
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
