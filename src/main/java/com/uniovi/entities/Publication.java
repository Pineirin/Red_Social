package com.uniovi.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Publication {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String titulo;
	private String texto;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private Date fecha;
	
	public Publication() {
		
	}
	
	public Publication(User user, String titulo,String texto, Date fecha) {
		
		super();
		this.user=user;
		this.titulo=titulo;
		this.texto=texto;
		this.fecha=fecha;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
