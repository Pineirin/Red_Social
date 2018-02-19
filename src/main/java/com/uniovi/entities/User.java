package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	private String nombre;
	private String email;
	private String password;
	
	public User() {
		
	}
	
	public User(Long id,String nombre,String email,String password) {
		super();
		this.id=id;
		this.nombre=nombre;
		this.email=email;
		this.password=password;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", nombre=" + nombre + ", email=" + email + ", password=" + password + "]";
	}
	
	
}
