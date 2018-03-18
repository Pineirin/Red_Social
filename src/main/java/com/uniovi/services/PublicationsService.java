package com.uniovi.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Petition;
import com.uniovi.entities.Publication;
import com.uniovi.repositories.PublicationsRepository;

@Service
public class PublicationsService {
	
	@Autowired
	private PublicationsRepository publicationsRepository;
	
	public void savePublication(Publication publication) {
		publicationsRepository.save(publication);
	}
	
	public List<Publication> getPublications() {
		List<Publication> publications = new ArrayList<Publication>();
		publicationsRepository.findAll().forEach(publications::add);
		return publications;
	}
	
	public Date getActualDate() {
		
		Calendar c = Calendar.getInstance();
		String dia = String.valueOf(c.get(Calendar.DATE));
		String mes = String.valueOf(c.get(Calendar.MONTH)+1);
		String annio = String.valueOf(c.get(Calendar.YEAR));
		String hora = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String minutos = String.valueOf(c.get(Calendar.MINUTE));
		String segundos = String.valueOf(c.get(Calendar.SECOND));
		
		String fechaActual=annio+"/"+mes+"/"+dia+" "+hora+":"+minutos+":"+segundos;
		Date fecha=new Date(fechaActual);
		
		return fecha;
	}
}
