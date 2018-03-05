package com.uniovi.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(unique=true)
	private String email;
	
	private String name;
	
	private String password;
	@Transient //propiedad que no se almacena e la tabla.
	private String passwordConfirm;
	
	private Boolean sendPetition = false;
	
	//@OneToMany(mappedBy = "userDestino", cascade = CascadeType.ALL)
	//private Set<Petition> petitions;

	public User() {
		
	}
	
	public User(String email,String name) {
		super();
		this.email=email;
		this.name=name;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String nombre) {
		this.name = nombre;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordConfirm() {
		 return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		 this.passwordConfirm = passwordConfirm;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", nombre=" + name + ", email=" + email + ", password=" + password + "]";
	}

	public Boolean getSendPetition() {
		return sendPetition;
	}

	public void setSendPetition(Boolean sendPetition) {
		this.sendPetition = sendPetition;
	}
	
	/*public Set<Petition> getPetitions() {
		return petitions;
	}

	public void setPetitions(Set<Petition> petitions) {
		this.petitions = petitions;
	}*/
	
	
}
