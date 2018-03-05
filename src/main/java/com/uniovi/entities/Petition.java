package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"userOrigen_id", "userDestino_id"})
		})
public class Petition {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "userOrigen_id")
	private User userOrigen;
	
	@ManyToOne
	@JoinColumn(name = "userDestino_id")
	private User userDestino;
	
	private String status=PetitionStatus.EN_PROCESO;
	
	public Petition() {}
	
	public Petition(User userOrigen, User userDestino) {
		super();
		this.userOrigen=userOrigen;
		this.userDestino=userDestino;
	}
	
	public User getUserOrigen() {
		return userOrigen;
	}

	public void setUserOrigen(User userOrigen) {
		this.userOrigen = userOrigen;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUserDestino() {
		return userDestino;
	}

	public void setUserDestino(User userDestino) {
		this.userDestino = userDestino;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Petition [id=" + id + ", userOrigen=" + userOrigen + ", userDestino=" + userDestino + ", status="
				+ status + "]";
	}

	
	
	

}
