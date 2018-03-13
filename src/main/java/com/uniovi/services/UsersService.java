package com.uniovi.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

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
		users=usersRepository.findAll(pageable);
		return users;
	}
	
	public User getUser(Long id) {
		User u = usersRepository.findOne(id);
		return u;
	}
	
	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
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
	
	public boolean passwordsIguales(String password,String passwordEncriptada) {
		
		return bCryptPasswordEncoder.matches(password, passwordEncriptada);
	}
	
	public void setSendPetition(boolean sendPetition,Long id){
		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();*/
		User user = usersRepository.findOne(id);
		if( user!=null) {//¿el propietario de la nota es el mismo que el autenticado?
			usersRepository.updateResend(sendPetition, id);
		}
	}
	
	public Page<User> searchUsersByEmailAndName (Pageable pageable, String searchText){  
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText= "%"+searchText+"%";
	    users = usersRepository.searchByEmailAndName(pageable,searchText);      
	    return users; 
	} 
	
	public long getIdOriginUser() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User userOrigen=usersRepository.findByEmail(email);
		
		return userOrigen.getId();
	}
	
	public List<User> searchUsersDestinosForUser(User userOrigen){
		
		return usersRepository.searchUsersDestinosForUser(userOrigen);
	}

	public Page<User> searchFriendsForUser(Pageable pageable, User currentUser){
		
		return usersRepository.searchFriendsForUser(pageable,currentUser);
	}
	
	public List<User> searchFriendsForUser(User currentUser){
		
		return usersRepository.searchFriendsForUser(currentUser);
	}
	
	public Page<User> amigosEnSesion(Pageable pageable, List<User> amigos){
		
		if(amigos.size()>0) {
			return usersRepository.searchFriendsOnLine(pageable, amigos);
		}
		return new PageImpl<User>(new LinkedList<User>());
		
	}
	
	public void actualizarEnLineaDelUsuario(String email, boolean enLinea) {
		
		usersRepository.actualizarEnLineaDelUsuario(email, enLinea);
	}
	
}
