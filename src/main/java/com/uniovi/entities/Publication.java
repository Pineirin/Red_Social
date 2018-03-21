package com.uniovi.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
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
	
	@Column(unique=true)
	private String title;
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private String date;
	
	public Publication() {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDate = new Date();
		this.date = formatter.format(currentDate);
	}
	
	public Publication(User user, String title, String description) {
		
		super();
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDate = new Date();
		this.date = formatter.format(currentDate); 
		
		this.user=user;
		this.title=title;
		this.description=description;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
}
