package com.uniovi.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_PublicationView2 extends PO_NavView {
	
	static public void fillForm(WebDriver driver, String titlep, String descriptionp) {
		
			WebElement title = driver.findElement(By.name("title"));
			title.click();
			title.clear();
			title.sendKeys(titlep);
			WebElement description = driver.findElement(By.name("description"));
			description.click();
			description.clear();
			description.sendKeys(descriptionp);
			
			driver.findElement(By.id("file")).sendKeys(System.getProperty("user.dir") + "Artorias y Sif.jpg");
			
			//Pulsar el boton de Publicar.
			By boton = By.className("btn");
			driver.findElement(boton).click();
		}
}
