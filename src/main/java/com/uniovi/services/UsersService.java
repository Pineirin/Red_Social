package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	public void init() {
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}

	public Page<User> getUsers(Pageable pageable) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersRepository.findAll(pageable);
		return users;
	}

	public User getUser(Long id) {
		User user = usersRepository.findOne(id);
		return user;
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
		log.info("User: " + user.getEmail() + " registered (and logged) in the application");
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public void deleteUser(Long id) {
		usersRepository.delete(id);
	}

	public String getPasswordEncrypt(String password) {

		return bCryptPasswordEncoder.encode(password);
	}

	public boolean passwordsIguales(String password, String passwordEncriptada) {

		return bCryptPasswordEncoder.matches(password, passwordEncriptada);
	}

	public Page<User> searchUsersByEmailAndName(Pageable pageable, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText = "%" + searchText + "%";
		users = usersRepository.searchByEmailAndName(pageable, searchText);
		return users;
	}

	public long getIdOriginUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User userOrigen = usersRepository.findByEmail(email);

		return userOrigen.getId();
	}

	public List<User> searchUsersDestinosForUser(User userOrigen) {

		return usersRepository.searchUsersDestinosForUser(userOrigen);
	}

	public Page<User> searchFriendsForUser(Pageable pageable, User user) {
		return usersRepository.searchFriendsForUser(pageable, user);
	}

	public List<User> searchFriendsForUser(User user) {

		return usersRepository.searchFriendsForUser(user);
	}

	public Page<User> amigosEnSesion(Pageable pageable, List<User> amigos) {

		if (amigos.size() > 0) {
			return usersRepository.searchFriendsOnLine(pageable, amigos);
		}
		return new PageImpl<User>(new LinkedList<User>());

	}

	public void actualizarEnLineaDelUsuario(String email, boolean enLinea) {
		usersRepository.actualizarEnLineaDelUsuario(email, enLinea);
	}

	public boolean userEnLinea(String email) {
		User user = getUserByEmail(email);
		return user.getEnLinea();
	}

}
