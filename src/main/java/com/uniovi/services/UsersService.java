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
		return usersRepository.findOne(id);
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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = usersRepository.findOne(id);
		if( user.getEmail().equals(email) ) {//¿el propietario de la nota es el mismo que el autenticado?
			usersRepository.updateResend(sendPetition, id);
		}
	}
	
	public List<User> searchUsersByEmailAndName (String searchText){  
		List<User> users = new ArrayList<User>();    
		searchText= "%"+searchText+"%";
	    users = usersRepository.searchByEmailAndName(searchText);      
	    return users; 
	} 
}
