package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long>{
	
	User findByEmail(String email);//Email es lo que se utiliza para el login, es decir le nombre de usuario
	
	Page<User> findAll(Pageable pageable); 
}
