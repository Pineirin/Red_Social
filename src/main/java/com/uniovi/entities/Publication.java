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
	
	private String title;
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private Date date;
	
	public Publication() {
		this.date = new Date();
	}
	
	public Publication(User user, String title, String description) {
		
		super();
		this.user=user;
		this.title=title;
		this.description=description;
		this.date=new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
}
