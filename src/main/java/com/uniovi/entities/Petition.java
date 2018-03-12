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
	@JoinColumn(name = "userDestino_id")
	private User userDestino;
	
	@ManyToOne
	@JoinColumn(name = "userOrigen_id")
	private User userOrigen;
	
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
		return "Petition [id=" + id + ", userDestino=" + userDestino + ", userOrigen=" + userOrigen + ", status="
				+ status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userDestino == null) ? 0 : userDestino.hashCode());
		result = prime * result + ((userOrigen == null) ? 0 : userOrigen.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Petition other = (Petition) obj;
		if (userDestino == null) {
			if (other.userDestino != null)
				return false;
		} else if (!userDestino.equals(other.userDestino))
			return false;
		if (userOrigen == null) {
			if (other.userOrigen != null)
				return false;
		} else if (!userOrigen.equals(other.userOrigen))
			return false;
		return true;
	}

	

	
	
	

}
